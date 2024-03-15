package com.bakirwebservice.paymentservice.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetProductDetailsResponse {
    private String productName;
    private String companyName;
    private String categoryName;
    private String productCode;
    private Long price;
    private int orderQuantity;
    private String status;
    private Long totalPriceOnCart;
    private String description;
}
