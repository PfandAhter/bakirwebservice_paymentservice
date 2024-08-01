package com.bakirwebservice.paymentservice.rest.controller;


import com.bakirwebservice.paymentservice.api.request.AddItemRequest;
import com.bakirwebservice.paymentservice.api.request.DeleteItemRequest;
import com.bakirwebservice.paymentservice.api.request.BaseRequest;
import com.bakirwebservice.paymentservice.api.request.UserOrderedProductRequest;
import com.bakirwebservice.paymentservice.api.response.BaseResponse;
import com.bakirwebservice.paymentservice.api.response.BuyOrdersOnCartTrackingNumberResponse;
import com.bakirwebservice.paymentservice.api.response.GetItemsListInCartResponse;
import com.bakirwebservice.paymentservice.api.response.QueryTrackingNumberResponse;
import com.bakirwebservice.paymentservice.exceptions.CartListEmptyException;
import com.bakirwebservice.paymentservice.exceptions.InsufficientBalanceException;
import com.bakirwebservice.paymentservice.lib.constants.PropertyConstants;
import com.bakirwebservice.paymentservice.rest.controller.api.PaymentRestServiceApi;
import com.bakirwebservice.paymentservice.rest.service.ShoppingCartServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping(path = PropertyConstants.REQUEST_SECURE_SERVICE_PAYMENT_CONTROLLER) //  /payment
@RequiredArgsConstructor

public class PaymentServiceController implements PaymentRestServiceApi {

    private final ShoppingCartServiceImpl paymentService;

    @Override
    public ResponseEntity<BaseResponse> createOrder(AddItemRequest addItemRequest, HttpServletRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(paymentService.addItemOnCart(addItemRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> deleteOrder(DeleteItemRequest deleteItemRequest, HttpServletRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(paymentService.deleteItemOnCart(deleteItemRequest));
    }

    @Override
    public ResponseEntity<GetItemsListInCartResponse> getShoppingCartItems(BaseRequest baseRequest, HttpServletRequest request)throws CartListEmptyException {
        return ResponseEntity.ok(paymentService.getItemListInCart(baseRequest));
    }

    @Override
    public ResponseEntity<BuyOrdersOnCartTrackingNumberResponse> buyItemsInCart(BaseRequest baseRequest, HttpServletRequest request) throws CartListEmptyException , InsufficientBalanceException {
        return ResponseEntity.ok(paymentService.buyOrdersOnCartsAndReturnTrackingNumber(baseRequest));
    }

    @Override
    public ResponseEntity<QueryTrackingNumberResponse> queryByTrackingNumber(String trackingNumber , BaseRequest baseRequest) {
        return ResponseEntity.ok(paymentService.queryTrackingNumberResponse(trackingNumber , baseRequest));
    }

    @Override
    public ResponseEntity<Boolean> isUserOrderedProduct(UserOrderedProductRequest userOrderedProductRequest, HttpServletRequest request) {
        return ResponseEntity.ok(paymentService.isUserOrderedProduct(userOrderedProductRequest));
    }

}
