package com.bakirwebservice.paymentservice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OrderStatus {
    private String productCode;
    private int orderQuantity;
    private String status;

}
