package com.bakirwebservice.paymentservice.rest.controller.api;

import com.bakirwebservice.paymentservice.api.request.*;
import com.bakirwebservice.paymentservice.api.response.BaseResponse;
import com.bakirwebservice.paymentservice.api.response.GetBalanceResponse;
import com.bakirwebservice.paymentservice.api.response.GetProductDetailsResponse;
import com.bakirwebservice.paymentservice.lib.constants.PropertyConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PaymentRestServiceApi {

    @PostMapping(path = PropertyConstants.REQUEST_BALANCE_ADD, consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse> addBalance (@Valid @RequestBody AddBalanceRequest addBalanceRequest, HttpServletRequest request, BindingResult bindingResult);

    @PostMapping(path = PropertyConstants.REQUEST_BALANCE_CREATE, consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse> createBalance (@Valid @RequestBody CreateBalanceRequest createBalanceRequest, HttpServletRequest request, BindingResult bindingResult);

    @PostMapping(path = PropertyConstants.REQUEST_BALANCE_GET, consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GetBalanceResponse> getBalance (@Valid @RequestBody BaseRequest baseRequest, HttpServletRequest request, BindingResult bindingResult);

    @PostMapping(path = PropertyConstants.REQUEST_ORDERLIST_CREATE_ORDER , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest , HttpServletRequest request, BindingResult bindingResult);

    @PostMapping(path = PropertyConstants.REQUEST_ORDERLIST_DELETE_BY_PRODUCT_CODE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse> deleteOrder (@Valid @RequestBody DeleteOrderRequest deleteOrderRequest, HttpServletRequest request, BindingResult bindingResult);

    @PostMapping(path = PropertyConstants.REQUEST_ORDERLIST_GET_LIST , consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GetProductDetailsResponse>> getOrderList (@Valid @RequestBody BaseRequest baseRequest , HttpServletRequest request ,BindingResult bindingResult);



}
