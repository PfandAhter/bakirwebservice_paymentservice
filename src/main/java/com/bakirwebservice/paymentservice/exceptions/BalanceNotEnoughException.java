package com.bakirwebservice.paymentservice.exceptions;

import lombok.Getter;

public class BalanceNotEnoughException extends Exception {
    @Getter
    private String message;

    public BalanceNotEnoughException(){
        super();
        this.message = null;
    }

    public BalanceNotEnoughException(String message){
        super();
        this.message = message;
    }
}
