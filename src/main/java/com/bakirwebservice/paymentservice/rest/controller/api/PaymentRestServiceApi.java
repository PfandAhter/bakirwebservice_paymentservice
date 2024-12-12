package com.bakirwebservice.paymentservice.rest.controller.api;

import com.bakirwebservice.paymentservice.api.request.*;
import com.bakirwebservice.paymentservice.api.response.BaseResponse;
import com.bakirwebservice.paymentservice.api.response.GetItemsListInCartResponse;
import com.bakirwebservice.paymentservice.api.response.QueryTrackingNumberResponse;
import com.bakirwebservice.paymentservice.exceptions.ShoppingCartEmptyException;
import com.bakirwebservice.paymentservice.exceptions.InsufficientBalanceException;
import com.bakirwebservice.paymentservice.lib.constants.PropertyConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface PaymentRestServiceApi {

    @PostMapping(path = PropertyConstants.REQUEST_SECURE_REST_CONTROLLER_PAYMENT_SERVICE_ORDER_LIST_ADD, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse> addToCart(@Valid @RequestBody AddToCartRequest addToCartRequest, HttpServletRequest request, BindingResult bindingResult);

    @PostMapping(path = PropertyConstants.REQUEST_SECURE_REST_CONTROLLER_PAYMENT_SERVICE_ORDER_LIST_DELETE_ORDER_BY_PRODUCT_CODE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse> deleteFromCart(@Valid @RequestBody DeleteFromCartRequest deleteFromCartRequest, HttpServletRequest request, BindingResult bindingResult);

    @PostMapping(path = PropertyConstants.REQUEST_SECURE_REST_CONTROLLER_PAYMENT_SERVICE_ORDER_LIST_GET)
    ResponseEntity<GetItemsListInCartResponse> getShoppingCart(@RequestBody BaseRequest baseRequest , HttpServletRequest request )throws ShoppingCartEmptyException;

    @PostMapping(path = PropertyConstants.REQUEST_SECURE_REST_CONTROLLER_PAYMENT_SERVICE_SHOPPING_CART_CHECKOUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse> shoppingCartCheckout(@RequestBody ShoppingCartCheckoutRequest shoppingCartCheckoutRequest, HttpServletRequest request) throws ShoppingCartEmptyException, InsufficientBalanceException;

    @PostMapping(path = PropertyConstants.REQUEST_SECURE_REST_CONTROLLER_PAYMENT_SERVICE_QUERY_BY_TRACKING_NUMBER)
    ResponseEntity<QueryTrackingNumberResponse> queryByTrackingNumber(@RequestParam("trackingnumber") String trackingNumber , @RequestBody BaseRequest baseRequest);

    @PostMapping(path = PropertyConstants.REQUEST_SECURE_REST_CONTROLLER_PAYMENT_SERVICE_ORDER_LIST_IS_BOUGHT , produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Boolean> isUserOrderedProduct (@RequestBody UserOrderedProductRequest userOrderedProductRequest,HttpServletRequest request);

}
