package com.bakirwebservice.paymentservice.rest.controller;


import com.bakirwebservice.paymentservice.api.request.*;
import com.bakirwebservice.paymentservice.api.response.BaseResponse;
import com.bakirwebservice.paymentservice.api.response.BuyOrdersOnCartTrackingNumberResponse;
import com.bakirwebservice.paymentservice.api.response.GetBalanceResponse;
import com.bakirwebservice.paymentservice.api.response.GetProductDetailsResponse;
import com.bakirwebservice.paymentservice.exceptions.BalanceNotEnoughException;
import com.bakirwebservice.paymentservice.lib.constants.PropertyConstants;
import com.bakirwebservice.paymentservice.rest.controller.api.PaymentRestServiceApi;
import com.bakirwebservice.paymentservice.rest.service.PaymentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping(path = PropertyConstants.REQUEST_PAYMENTSERVICE) //  /paymentservice
@RequiredArgsConstructor

public class PaymentServiceController implements PaymentRestServiceApi {

    private final PaymentServiceImpl paymentService;

    @Override
    public ResponseEntity<BaseResponse> addBalance(AddBalanceRequest addBalanceRequest, HttpServletRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(paymentService.addBalance(addBalanceRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> createBalance(CreateBalanceRequest createBalanceRequest, HttpServletRequest request, BindingResult bindingResult){
        return ResponseEntity.ok(paymentService.createBalance(createBalanceRequest));
    }

    @Override
    public ResponseEntity<GetBalanceResponse> getBalance(BaseRequest baseRequest, HttpServletRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(paymentService.getBalance(baseRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> createOrder(CreateOrderRequest createOrderRequest, HttpServletRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(paymentService.createOrder(createOrderRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> deleteOrder(DeleteOrderRequest deleteOrderRequest, HttpServletRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(paymentService.deleteOrder(deleteOrderRequest));
    }

    @Override
    public ResponseEntity<List<GetProductDetailsResponse>> getOrderList(BaseRequest baseRequest, HttpServletRequest request, BindingResult bindingResult) {
        return ResponseEntity.ok(paymentService.getOrderList(baseRequest));
    }

    @PostMapping(path = "/buyordersoncart")
    public ResponseEntity<BuyOrdersOnCartTrackingNumberResponse> buyOrdersOnCartTrackingNumberResponse (@Valid @RequestBody BaseRequest baseRequest) throws BalanceNotEnoughException {
        return ResponseEntity.ok(paymentService.buyOrdersOnCartsAndReturnTrackingNumber(baseRequest));
    }
}
