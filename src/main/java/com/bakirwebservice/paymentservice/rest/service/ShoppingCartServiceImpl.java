package com.bakirwebservice.paymentservice.rest.service;

import com.bakirwebservice.paymentservice.api.client.StockServiceClient;
import com.bakirwebservice.paymentservice.api.client.TokenServiceClient;
import com.bakirwebservice.paymentservice.api.client.UserServiceClient;
import com.bakirwebservice.paymentservice.api.request.*;
import com.bakirwebservice.paymentservice.api.response.*;
import com.bakirwebservice.paymentservice.exceptions.CartListEmptyException;
import com.bakirwebservice.paymentservice.exceptions.InsufficientBalanceException;
import com.bakirwebservice.paymentservice.model.dto.OrderStatus;
import com.bakirwebservice.paymentservice.model.dto.ShoppingCartDTO;
import com.bakirwebservice.paymentservice.model.entity.OrderList;
import com.bakirwebservice.paymentservice.model.entity.ShoppingCart;
import com.bakirwebservice.paymentservice.repository.OrderRepository;
import com.bakirwebservice.paymentservice.repository.ShoppingCartRepository;
import com.bakirwebservice.paymentservice.rest.service.interfaces.IHeaderService;
import com.bakirwebservice.paymentservice.rest.service.interfaces.IShoppingCartService;
import com.bakirwebservice.paymentservice.rest.util.Util;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j

public class ShoppingCartServiceImpl implements IShoppingCartService {

    private final OrderRepository orderRepository;

    private final MapperServiceImpl mapperService;

    private final ShoppingCartRepository shoppingCartRepository;

    private final TokenServiceClient tokenServiceClient;

    private final StockServiceClient stockServiceClient;

    private final UserServiceClient userServiceClient;

    private final OrderListServiceImpl orderListService;

    private final HttpServletRequest httpServletRequest;

    private final IHeaderService headerService;


    @Override
    public BaseResponse deleteItemOnCart(DeleteItemRequest request) {
        String localUsername = tokenServiceClient.extractedUsername(request);
        if (shoppingCartRepository.findByCustomerNameAndProductCode(localUsername, request.getProductCode()).getOrderQuantity() <= request.getQuantity()) {

            ShoppingCart localCart = shoppingCartRepository.findByCustomerNameAndProductCode(localUsername, request.getProductCode());

            shoppingCartRepository.delete(localCart);
        } else {
            OrderList localOrderList = orderRepository.findByCustomerNameAndActive(localUsername, 1);
            localOrderList.setOrderQuantity(localOrderList.getOrderQuantity() - request.getQuantity());
            orderRepository.save(localOrderList);
        }
        return new BaseResponse();
    }

    @Override
    public BaseResponse addItemOnCart(AddItemRequest request) {
        String localUsername = tokenServiceClient.extractedUsername(request);
        ShoppingCart localCart = shoppingCartRepository.findByCustomerNameAndProductCode(localUsername, request.getProductCode());

        if (localCart != null) {

            if(request.getOrderQuantity() + localCart.getOrderQuantity() <= 0){
                shoppingCartRepository.delete(localCart);
            }else{
                localCart.setOrderQuantity(request.getOrderQuantity() + localCart.getOrderQuantity());
                shoppingCartRepository.save(localCart);
            }
        } else {
            shoppingCartRepository.save(ShoppingCart.builder()
                    .orderQuantity(request.getOrderQuantity())
                    .active(1)
                    .customerName(localUsername)
                    .status("WAITING PAYMENT")
                    .productCode(request.getProductCode()).build());
        }
        return new BaseResponse();
    }

    @Override
    public GetItemsListInCartResponse getItemListInCart(BaseRequest request) throws CartListEmptyException{
        String localUsername = tokenServiceClient.extractedUsername(request);

//        List<ShoppingCartDTO> localCartList = Arrays.asList(mapperService.map(shoppingCartRepository.findShoppingCartsByCriteria(localUsername), ShoppingCartDTO.class));
        List<ShoppingCart> localCartList = shoppingCartRepository.findShoppingCartsByCriteria(localUsername);


//        for(ShoppingCartDTO shoppingCartDTO : localCartList){
//            ProductGetResponse productGetResponse = stockServiceClient.getProductsDetail(shoppingCartDTO.getProductCode());
//
//        }
        if(!localCartList.isEmpty()){

            List<ShoppingCartDTO> localCartListDTO = new ArrayList<>();

            for (int k = 0; k < localCartList.size(); k++) {
                localCartListDTO.add(k, mapperService.map(localCartList.get(k), ShoppingCartDTO.class));
            }
            Long totalAmount = 0L;

            for (int i = 0; i < localCartList.size(); i++) {
                Long localAmount;

                ProductGetResponse productGetResponse = stockServiceClient.getProductsDetail(localCartListDTO.get(i).getProductCode());
                localAmount = localCartListDTO.get(i).getOrderQuantity() * productGetResponse.getProductDTO().getPrice();
                totalAmount += localAmount;

                mapperService.map(productGetResponse, localCartListDTO.getClass());

                localCartListDTO.set(i, mapperService.map(productGetResponse.getProductDTO(), ShoppingCartDTO.class));
                localCartListDTO.get(i).setProductCode(localCartList.get(i).getProductCode());
                localCartListDTO.get(i).setOrderQuantity(localCartList.get(i).getOrderQuantity());
                localCartListDTO.get(i).setStatus(localCartList.get(i).getStatus());
                localCartListDTO.get(i).setAmount(localAmount);

            }

            GetItemsListInCartResponse getItemsListInCartResponse = new GetItemsListInCartResponse();
            getItemsListInCartResponse.setTotalAmount(totalAmount);
            getItemsListInCartResponse.setShoppingCartList(localCartListDTO);

            return getItemsListInCartResponse;
        }

        throw new CartListEmptyException("CART LIST IS EMPTY");
    }


    @Override
    public BuyOrdersOnCartTrackingNumberResponse buyOrdersOnCartsAndReturnTrackingNumber(BaseRequest request) throws CartListEmptyException ,InsufficientBalanceException {
        String localUsername = tokenServiceClient.extractedUsername(request);
        GetBalanceResponse localUserBalance = userServiceClient.getBalance(request);


        GetItemsListInCartResponse getItemsListIncartResponse = getItemListInCart(request);
        String trackingNumber = Util.generateCode();

        if (getItemsListIncartResponse.getTotalAmount() <= localUserBalance.getBalance()) {

            List<ShoppingCart> localCartList = shoppingCartRepository.findShoppingCartsByCriteria(localUsername);

            for (int i = 0; i < getItemsListIncartResponse.getShoppingCartList().size(); i++) {
                ReduceStockRequest reduceStockRequest = new ReduceStockRequest();
                reduceStockRequest.setToken(request.getToken());
                reduceStockRequest.setTime(request.getTime());
                reduceStockRequest.setOrderQuantity(getItemsListIncartResponse.getShoppingCartList().get(i).getOrderQuantity());
                reduceStockRequest.setProductId(getItemsListIncartResponse.getShoppingCartList().get(i).getProductCode());
                stockServiceClient.reduceStock(reduceStockRequest);
                orderListService.buyItemsInCartResponseTrackingNumber(localUsername,getItemsListIncartResponse.getShoppingCartList().get(i).getProductCode() , getItemsListIncartResponse.getShoppingCartList().get(i).getOrderQuantity(), trackingNumber, getItemsListIncartResponse.getTotalAmount());
                localCartList.get(i).setStatus("PAYMENT SUCCESSFULL");
                localCartList.get(i).setActive(2);
                shoppingCartRepository.save(localCartList.get(i));

            }
            AddBalanceRequest addBalanceRequest = new AddBalanceRequest();
            addBalanceRequest.setAmount(localUserBalance.getBalance() - getItemsListIncartResponse.getTotalAmount());
            addBalanceRequest.setToken(request.getToken());
            addBalanceRequest.setTime(request.getTime());

            userServiceClient.addBalance(addBalanceRequest);

            List<OrderList> localOrderLists = orderRepository.findOrderListsByCustomerNameAndActive(localUsername, 1);

            for (int i = 0; i < localOrderLists.size(); i++) {
                localOrderLists.get(i).setActive(2);
                localOrderLists.get(i).setStatus("PREPARING");
                orderRepository.save(localOrderLists.get(i));
            }
            return new BuyOrdersOnCartTrackingNumberResponse(getItemsListIncartResponse.getTotalAmount(), trackingNumber);
        }else{
            throw new InsufficientBalanceException("BALANCE INSUFFICIENT");
        }
    }

    public QueryTrackingNumberResponse queryTrackingNumberResponse (String trackingNumber , BaseRequest baseRequest){
        String localUsername = tokenServiceClient.extractedUsername(baseRequest);
        return new QueryTrackingNumberResponse(mapperService.modelMapper(orderRepository.findByCustomerNameAndTrackingNumber(localUsername,trackingNumber), OrderStatus.class));
    }

    public Boolean isUserOrderedProduct (UserOrderedProductRequest request){
        if(orderRepository.findOrderListByCustomerNameAndProductCode(request.getUsername(),request.getProductId()) != null){
            return true;
        }else{
            return false;
        }
    }
}