package com.bakirwebservice.paymentservice.exceptions;

import lombok.Getter;

public class InsufficientBalanceException extends Exception{
    @Getter
    private String message;

    public InsufficientBalanceException(){
        super();
        this.message = null;
    }

    public InsufficientBalanceException(String message){
        super();
        this.message = message;
    }
}
