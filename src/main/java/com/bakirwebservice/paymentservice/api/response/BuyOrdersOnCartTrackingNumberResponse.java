package com.bakirwebservice.paymentservice.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class BuyOrdersOnCartTrackingNumberResponse extends BaseResponse{
    private Long cost;
    private Long trackingNumber;
}
