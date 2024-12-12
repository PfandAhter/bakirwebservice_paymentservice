package com.bakirwebservice.paymentservice.api.request.payment;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PaymentRequest {

    private String fromId;
    private String fromAccountName;
    private String toId;
    private String toAccountName;
    private Double amount;
    private String description;
}
