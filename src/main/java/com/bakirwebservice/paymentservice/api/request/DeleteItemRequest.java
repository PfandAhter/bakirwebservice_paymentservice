package com.bakirwebservice.paymentservice.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DeleteItemRequest extends BaseRequest {
    private String productCode;
    private int quantity;
}
