package com.bakirwebservice.paymentservice.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetProductDetailsRequest {
    private String productName;
    private String companyName;
    private String categoryName;
    private Long productCode;
    private Long amount;
    private String status;
    private String description;
}
