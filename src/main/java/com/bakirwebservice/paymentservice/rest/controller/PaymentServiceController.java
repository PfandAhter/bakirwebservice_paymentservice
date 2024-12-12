package com.bakirwebservice.paymentservice.rest.controller;


import com.bakirwebservice.paymentservice.api.request.*;
import com.bakirwebservice.paymentservice.api.response.BaseResponse;
import com.bakirwebservice.paymentservice.api.response.GetItemsListInCartResponse;
import com.bakirwebservice.paymentservice.api.response.QueryTrackingNumberResponse;
import com.bakirwebservice.paymentservice.lib.constants.PropertyConstants;
import com.bakirwebservice.paymentservice.rest.controller.api.PaymentRestServiceApi;
import com.bakirwebservice.paymentservice.rest.service.ShoppingCartServiceImpl;
import com.bakirwebservice.paymentservice.rest.validator.ShoppingCartServiceValidator;
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

    private final ShoppingCartServiceValidator serviceValidator;

    @Override
    public ResponseEntity<BaseResponse> addToCart(AddToCartRequest addToCartRequest, HttpServletRequest request, BindingResult bindingResult) {
        serviceValidator.addToCartValidator(addToCartRequest);

        return ResponseEntity.ok(paymentService.addToCart(addToCartRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> deleteFromCart(DeleteFromCartRequest deleteFromCartRequest, HttpServletRequest request, BindingResult bindingResult) {
        serviceValidator.deleteFromCartValidator(deleteFromCartRequest);

        return ResponseEntity.ok(paymentService.deleteFromCart(deleteFromCartRequest));
    }

    @Override
    public ResponseEntity<GetItemsListInCartResponse> getShoppingCart(BaseRequest baseRequest, HttpServletRequest request){
        serviceValidator.getShoppingCartValidator(baseRequest);

        return ResponseEntity.ok(paymentService.getShoppingCart(baseRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> shoppingCartCheckout(ShoppingCartCheckoutRequest shoppingCartCheckoutRequest, HttpServletRequest request){
        serviceValidator.completePurchaseAndReturnTrackingNumberValidator(shoppingCartCheckoutRequest);
        paymentService.startShoppingCartCheckout(shoppingCartCheckoutRequest);


        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setErrorDescription("ISLEMINIZ BASARIYLA ALINDI.");
        return ResponseEntity.ok(baseResponse);
    }

    @Override
    public ResponseEntity<QueryTrackingNumberResponse> queryByTrackingNumber(String trackingNumber , BaseRequest baseRequest) {
        serviceValidator.queryTrackingNumberResponseValidator(trackingNumber, baseRequest);

        return ResponseEntity.ok(paymentService.queryTrackingNumberResponse(trackingNumber , baseRequest));
    }

    @Override
    public ResponseEntity<Boolean> isUserOrderedProduct(UserOrderedProductRequest userOrderedProductRequest, HttpServletRequest request) {
        return ResponseEntity.ok(paymentService.isUserOrderedProduct(userOrderedProductRequest));
    }

}
