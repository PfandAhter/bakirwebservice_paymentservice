package com.bakirwebservice.paymentservice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ShoppingCartDTO {

    private String productName;
    private String companyName;
    private String categoryId;
    private String productCode;
    private Long price;
    private int orderQuantity;
    private Long amount;
    private String status;
    private String description;
}
