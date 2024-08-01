package com.bakirwebservice.paymentservice.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductDTO {

    private String productName;
    private String companyName;
    private String categoryId;
    private Long price;
    private int stock;
    private String description;


}
