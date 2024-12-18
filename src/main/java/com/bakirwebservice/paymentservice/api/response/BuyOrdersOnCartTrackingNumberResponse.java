package com.bakirwebservice.paymentservice.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class BuyOrdersOnCartTrackingNumberResponse extends BaseResponse{
    private Long cost;
    private String trackingNumber;
}
