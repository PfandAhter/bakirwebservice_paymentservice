package com.bakirwebservice.paymentservice.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ReduceStockRequest extends BaseRequest{

    private String productId;

    private int orderQuantity;
}
