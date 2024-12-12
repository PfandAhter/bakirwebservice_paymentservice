package com.bakirwebservice.paymentservice.rest.validator;


import com.bakirwebservice.paymentservice.api.client.TokenServiceClient;
import com.bakirwebservice.paymentservice.api.client.UserServiceClient;
import com.bakirwebservice.paymentservice.api.request.AddToCartRequest;
import com.bakirwebservice.paymentservice.api.request.BaseRequest;
import com.bakirwebservice.paymentservice.api.request.DeleteFromCartRequest;
import com.bakirwebservice.paymentservice.api.request.ShoppingCartCheckoutRequest;
import com.bakirwebservice.paymentservice.api.response.GetBalanceResponse;
import com.bakirwebservice.paymentservice.api.response.GetItemsListInCartResponse;
import com.bakirwebservice.paymentservice.exceptions.NotFoundException;
import com.bakirwebservice.paymentservice.model.PaymentMethod;
import com.bakirwebservice.paymentservice.model.Status;
import com.bakirwebservice.paymentservice.model.entity.OrderList;
import com.bakirwebservice.paymentservice.model.entity.ShoppingCart;
import com.bakirwebservice.paymentservice.repository.OrderRepository;
import com.bakirwebservice.paymentservice.repository.ShoppingCartRepository;
import com.bakirwebservice.paymentservice.rest.service.ShoppingCartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

import static com.bakirwebservice.paymentservice.lib.constants.ErrorCodeConstants.*;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceValidator {

    private final TokenServiceClient tokenServiceClient;

    private final ShoppingCartRepository shoppingCartRepository;

    private final UserServiceClient userServiceClient;

    private final ShoppingCartServiceImpl shoppingCartService;

    private final OrderRepository orderRepository;

    public void deleteFromCartValidator (DeleteFromCartRequest deleteFromCartRequest){
        String localUsername = tokenServiceClient.extractedUsername(deleteFromCartRequest);

        if (localUsername.isEmpty()){
            throw new NotFoundException(USER_NOT_FOUND);
        }
        ShoppingCart shoppingCart = shoppingCartRepository.findByCustomerNameAndProductCode(localUsername, deleteFromCartRequest.getProductCode());

        if(shoppingCart == null){
            throw new NotFoundException(SHOPPING_CART_EMPTY);
        }
    }

    public void addToCartValidator(AddToCartRequest addToCartRequest){
        String localUsername = tokenServiceClient.extractedUsername(addToCartRequest);

        if(localUsername.isEmpty()){
            throw new NotFoundException(USER_NOT_FOUND);
        }

    }

    public void getShoppingCartValidator(BaseRequest baseRequest){
        String localUsername = tokenServiceClient.extractedUsername(baseRequest);

        if (localUsername.isEmpty() || localUsername.isBlank()){
            throw new NotFoundException(USER_NOT_FOUND);
        }
        List<ShoppingCart> shoppingCart = shoppingCartRepository.findShoppingCartsByCriteria(localUsername);

        if (shoppingCart.isEmpty()){
            throw new NotFoundException(SHOPPING_CART_EMPTY);
        }
    }

    public void completePurchaseAndReturnTrackingNumberValidator(ShoppingCartCheckoutRequest shoppingCartCheckoutRequest){
        String localUsername = tokenServiceClient.extractedUsername(shoppingCartCheckoutRequest);

        if(localUsername.isEmpty()){
            throw new NotFoundException(USER_NOT_FOUND);
        }
        if (shoppingCartCheckoutRequest.getPaymentType() == null){
            throw new NotFoundException("Payment method is not found");
        }

        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findShoppingCartsByCriteria(localUsername);

        if(shoppingCartList.isEmpty()){
            throw new NotFoundException(SHOPPING_CART_EMPTY);
        }

        if(PaymentMethod.CASH.toString().equals(shoppingCartCheckoutRequest.getPaymentType())){
            GetBalanceResponse userBalance = userServiceClient.getBalance(shoppingCartCheckoutRequest);
            if (userBalance.getBalance() <= 0) {
                throw new NotFoundException(INSUFFICIENT_FUNDS);
            }
            GetItemsListInCartResponse shoppingCartResponse = shoppingCartService.getShoppingCart(shoppingCartCheckoutRequest);

            if(userBalance.getBalance() < shoppingCartResponse.getTotalAmount()){
                throw new NotFoundException(INSUFFICIENT_FUNDS);
            }
        }
        List<OrderList> orderList = orderRepository.findOrderListsByCustomerNameAndActive(localUsername, Status.ACTIVE.toString());

        if(orderList.isEmpty()){
            throw new NotFoundException(ORDER_NOT_FOUND);
        }
    }

    public void queryTrackingNumberResponseValidator(String trackingNumber, BaseRequest baseRequest){
        String localUsername = tokenServiceClient.extractedUsername(baseRequest);

        if(localUsername.isEmpty()){
            throw new NotFoundException(USER_NOT_FOUND);
        }

        List<OrderList> orderList = orderRepository.findByCustomerNameAndTrackingNumber(localUsername,trackingNumber);
        if(orderList.isEmpty()){
            throw new NotFoundException(ORDER_NOT_FOUND);
        }
    }
}