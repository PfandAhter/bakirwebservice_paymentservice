package com.bakirwebservice.paymentservice.api.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserOrderedProductRequest {

    private String username;

    private String productId;
}

