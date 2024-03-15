package com.bakirwebservice.paymentservice.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DeleteOrderRequest extends BaseRequest {
    private Long productCode;
    private int quantity;
}
