package com.bakirwebservice.paymentservice.rest.service.interfaces;

import com.bakirwebservice.paymentservice.api.request.*;
import com.bakirwebservice.paymentservice.api.response.BaseResponse;
import com.bakirwebservice.paymentservice.api.response.GetBalanceResponse;
import com.bakirwebservice.paymentservice.api.response.GetProductDetailsResponse;

import java.util.List;

public interface IPaymentService {

    BaseResponse addBalance(AddBalanceRequest request);

    BaseResponse createBalance(CreateBalanceRequest request);

    GetBalanceResponse getBalance (BaseRequest request);

    BaseResponse deleteOrder(DeleteOrderRequest request);

    BaseResponse createOrder(CreateOrderRequest request);

    List<GetProductDetailsResponse> getOrderList(BaseRequest request);

}
