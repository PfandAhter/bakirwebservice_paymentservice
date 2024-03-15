package com.bakirwebservice.paymentservice.api.request;


import lombok.Getter;

@Getter
public class GetBalanceRequest extends BaseRequest{
    private String username;
}
