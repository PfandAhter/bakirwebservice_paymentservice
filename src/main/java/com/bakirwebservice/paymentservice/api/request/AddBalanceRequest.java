package com.bakirwebservice.paymentservice.api.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBalanceRequest extends BaseRequest{

    private Long amount;
}
