package com.bakirwebservice.paymentservice.rest.service;

import com.bakirwebservice.paymentservice.api.client.StockServiceClient;
import com.bakirwebservice.paymentservice.api.client.TokenServiceClient;
import com.bakirwebservice.paymentservice.api.request.*;
import com.bakirwebservice.paymentservice.api.response.*;
import com.bakirwebservice.paymentservice.exceptions.ProcessFailedException;
import com.bakirwebservice.paymentservice.model.Status;
import com.bakirwebservice.paymentservice.model.dto.OrderStatus;
import com.bakirwebservice.paymentservice.model.dto.ShoppingCartDTO;
import com.bakirwebservice.paymentservice.model.entity.OrderList;
import com.bakirwebservice.paymentservice.model.entity.ShoppingCart;
import com.bakirwebservice.paymentservice.repository.OrderRepository;
import com.bakirwebservice.paymentservice.repository.ShoppingCartRepository;
import com.bakirwebservice.paymentservice.rest.service.interfaces.IShoppingCartService;
import com.bakirwebservice.paymentservice.rest.service.payment.PaymentFactory;
import com.bakirwebservice.paymentservice.rest.service.payment.strategy.PaymentStrategy;
import com.bakirwebservice.paymentservice.rest.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.bakirwebservice.paymentservice.lib.constants.ErrorCodeConstants.*;


@Service
@RequiredArgsConstructor
@Slf4j

public class ShoppingCartServiceImpl implements IShoppingCartService {

    private final OrderRepository orderRepository;

    private final MapperServiceImpl mapperService;

    private final ShoppingCartRepository shoppingCartRepository;

    private final TokenServiceClient tokenServiceClient;

    private final StockServiceClient stockServiceClient;

    private final OrderListServiceImpl orderListService;

    private final DirectExchange directExchange;

    private final AmqpTemplate rabbitTemplate;

    private final PaymentFactory paymentFactory;

//    private final HttpServletRequest httpServletRequest;

//    private final IHeaderService headerService;


    @Override
    public BaseResponse addToCart(AddToCartRequest request) {
        String localUsername = tokenServiceClient.extractedUsername(request);
        ShoppingCart localCart = shoppingCartRepository.findByCustomerNameAndProductCode(localUsername, request.getProductCode());

        if (localCart != null) {

            if (request.getOrderQuantity() + localCart.getOrderQuantity() <= 0) {
                shoppingCartRepository.delete(localCart);
            } else {
                localCart.setOrderQuantity(request.getOrderQuantity() + localCart.getOrderQuantity());
                shoppingCartRepository.save(localCart);
            }
        } else {
            shoppingCartRepository.save(ShoppingCart.builder()
                    .orderQuantity(request.getOrderQuantity())
                    .active(Status.ACTIVE.toString())
                    .customerName(localUsername)
                    .status(Status.WAITINGPAYMENT.toString())
                    .productCode(request.getProductCode()).build());
        }
        return new BaseResponse();
    }

    @Override
    public BaseResponse deleteFromCart(DeleteFromCartRequest request) {
        String localUsername = tokenServiceClient.extractedUsername(request);
        if (shoppingCartRepository.findByCustomerNameAndProductCode(localUsername, request.getProductCode()).getOrderQuantity() <= request.getQuantity()) {

            ShoppingCart localCart = shoppingCartRepository.findByCustomerNameAndProductCode(localUsername, request.getProductCode());

            shoppingCartRepository.delete(localCart);
        } else {
            OrderList localOrderList = orderRepository.findByCustomerNameAndActive(localUsername, Status.ACTIVE.toString());
            localOrderList.setOrderQuantity(localOrderList.getOrderQuantity() - request.getQuantity());
            orderRepository.save(localOrderList);
        }
        return new BaseResponse();
    }

    @Override
    public GetItemsListInCartResponse getShoppingCart(BaseRequest request) {
        String localUsername = tokenServiceClient.extractedUsername(request);
        List<ShoppingCart> localCartList = shoppingCartRepository.findShoppingCartsByCriteria(localUsername);

        List<ShoppingCartDTO> localCartListDTO = new ArrayList<>();

        for (int k = 0; k < localCartList.size(); k++) {
            localCartListDTO.add(k, mapperService.map(localCartList.get(k), ShoppingCartDTO.class));
        }
        long totalAmount = 0L;

        for (ShoppingCart shoppingCartItem : localCartList) {
            long localAmount;

            ProductGetResponse productGetResponse = stockServiceClient.getProductsDetail(shoppingCartItem.getProductCode());
            localAmount = shoppingCartItem.getOrderQuantity() * productGetResponse.getProductDTO().getPrice();
            totalAmount += localAmount;

            mapperService.map(productGetResponse, localCartListDTO.getClass());
            localCartListDTO.set(localCartList.indexOf(shoppingCartItem), mapperService.map(productGetResponse.getProductDTO(), ShoppingCartDTO.class)); //TODO: Check this line may be gets error
            localCartListDTO.get(localCartList.indexOf(shoppingCartItem)).setProductCode(shoppingCartItem.getProductCode());
            localCartListDTO.get(localCartList.indexOf(shoppingCartItem)).setOrderQuantity(shoppingCartItem.getOrderQuantity());
            localCartListDTO.get(localCartList.indexOf(shoppingCartItem)).setStatus(shoppingCartItem.getStatus());
            localCartListDTO.get(localCartList.indexOf(shoppingCartItem)).setAmount(localAmount);
        }

        GetItemsListInCartResponse getItemsListInCartResponse = new GetItemsListInCartResponse();
        getItemsListInCartResponse.setTotalAmount(totalAmount);
        getItemsListInCartResponse.setShoppingCartList(localCartListDTO);

        return getItemsListInCartResponse;
    }

    // PAYMENT STARTS HERE
    @Override
    public void startShoppingCartCheckout(ShoppingCartCheckoutRequest request){
        rabbitTemplate.convertAndSend(directExchange.getName(),"firstStepCartCheckout", request);
    }


    //STOCK CHECK STEP
    //Checking the stock here for does his/her account have enough stock
    @RabbitListener(queues = "firstStepCartCheckout")
    public void firstStepCartCheckout(ShoppingCartCheckoutRequest request){
        try {
            GetItemsListInCartResponse getItemsListIncartResponse = getShoppingCart(request);
            String username = tokenServiceClient.extractedUsername(request);
            List<ShoppingCart> localCartList = shoppingCartRepository.findShoppingCartsByCriteria(username);

            for (ShoppingCartDTO shoppingCartItem : getItemsListIncartResponse.getShoppingCartList()) {
                Boolean checkStock = stockServiceClient.checkStock(shoppingCartItem.getProductCode(), shoppingCartItem.getOrderQuantity());
                if (Boolean.FALSE.equals(checkStock)) {
                    throw new ProcessFailedException(STOCK_INSUFFICIENT);
                }
            }

            for (ShoppingCart shoppingCartItem : localCartList) {
                shoppingCartItem.setStatus(Status.WAITINGPAYMENT.toString());
                shoppingCartRepository.save(shoppingCartItem);
            }

            request.getParams().put("username", username);
            ShoppingCartCheckOutSteps shoppingCartCheckOutSteps = new ShoppingCartCheckOutSteps();
            mapperService.map(request, shoppingCartCheckOutSteps);
            shoppingCartCheckOutSteps.setShoppingCartList(localCartList);

            rabbitTemplate.convertAndSend(directExchange.getName(), "secondStepCartCheckout", shoppingCartCheckOutSteps);
        }catch (Exception exception){
            log.error("The user's: " + request.getParams().get("username") + " shopping cart checkout is not successful.");
            throw new ProcessFailedException(SHOPPING_CART_CHECKOUT_FAILED);
        }
    }

    //BANK ACCOUNT CHECK STEP
    //Checking the bank account here for does his/her account have enough money
    @RabbitListener(queues = "secondStepCartCheckout")
    public void secondStepCartCheckout(ShoppingCartCheckOutSteps request){
        try{
            PaymentStrategy paymentStrategy = paymentFactory.createPayment(
                    request.getPaymentType(),
                    request.getParams());
            Boolean checkPaymentCheckout = paymentStrategy.pay(request.getAmount());

            if (Boolean.FALSE.equals(checkPaymentCheckout)) {
                for (ShoppingCart shoppingCartItem : request.getShoppingCartList()) {
                    shoppingCartItem.setStatus(Status.WAITINGPAYMENT.toString());
                    shoppingCartRepository.save(shoppingCartItem);
                }
                log.info("The user's: " + request.getParams().get("username") + " payment is not successful.");
                throw new ProcessFailedException(PAYMENT_FAILED);
            }


            rabbitTemplate.convertAndSend(directExchange.getName(), "thirdStepCartCheckout", request);
        }catch (Exception exception){
            log.error("The user's: " + request.getParams().get("username") + " payment is not successful.");
            throw new ProcessFailedException(SHOPPING_CART_CHECKOUT_FAILED);
        }
    }


    //Finalize order and return tracking number to user and send email
    //Reduce product stock
    @RabbitListener(queues = "thirdStepCartCheckout")
    public void thirdStepCartCheckout (ShoppingCartCheckOutSteps request){
        try {
            GetItemsListInCartResponse getItemsListIncartResponse = getShoppingCart(request);

            try {
                for (ShoppingCartDTO shoppingCartItem : getItemsListIncartResponse.getShoppingCartList()) {
                    stockServiceClient.reduceStock(shoppingCartItem.getProductCode(), shoppingCartItem.getOrderQuantity());
                }
            } catch (Exception e) {
                PaymentStrategy paymentStrategy = paymentFactory.createPayment(
                        request.getPaymentType(),
                        request.getParams());
                Boolean checkPaymentCheckout = paymentStrategy.cancel(request.getAmount());

                if (Boolean.FALSE.equals(checkPaymentCheckout)) {
                    log.warn("The user's: " + request.getParams().get("username") + " payment return is not successful.");
                }
                throw new ProcessFailedException(STOCK_INSUFFICIENT);
            }

            for (ShoppingCartDTO shoppingCartItem : getItemsListIncartResponse.getShoppingCartList()) {
                orderListService.buyItemsInCartResponseTrackingNumber(
                        request.getParams().get("username"),
                        shoppingCartItem.getProductCode(),
                        shoppingCartItem.getOrderQuantity(),
                        Util.generateCode(),
                        getItemsListIncartResponse.getTotalAmount());
            }

            for (ShoppingCart shoppingCartItem : request.getShoppingCartList()) {
                shoppingCartItem.setStatus(Status.SUCCESSFULLY.toString());
                shoppingCartItem.setActive(Status.PASSIVE.toString());
                shoppingCartRepository.save(shoppingCartItem);
            }
            log.info("The user's: " + request.getParams().get("username") + " shopping cart checkout is successful.");

            //SEND EMAIL
            //SEND NOTIFICATION
        }catch (Exception exception){
            log.error("The user's: " + request.getParams().get("username") + " shopping cart checkout is not successful.");
            throw new ProcessFailedException(SHOPPING_CART_CHECKOUT_FAILED);
        }
    }

    public QueryTrackingNumberResponse queryTrackingNumberResponse(String trackingNumber, BaseRequest baseRequest) {
        String localUsername = tokenServiceClient.extractedUsername(baseRequest);
        return new QueryTrackingNumberResponse(mapperService.modelMapper(orderRepository.findByCustomerNameAndTrackingNumber(localUsername, trackingNumber), OrderStatus.class));
    }

    public Boolean isUserOrderedProduct(UserOrderedProductRequest request) {
        if (orderRepository.findOrderListByCustomerNameAndProductCode(request.getUsername(), request.getProductId()) != null) {
            return true;
        } else {
            return false;
        }
    }
}