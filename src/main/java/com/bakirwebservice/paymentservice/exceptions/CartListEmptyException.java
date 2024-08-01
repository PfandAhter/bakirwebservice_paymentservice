package com.bakirwebservice.paymentservice.exceptions;

import lombok.Getter;

public class CartListEmptyException extends Exception {
    @Getter
    private String message;

    public CartListEmptyException(){
        super();
        this.message = null;
    }

    public CartListEmptyException(String message){
        super();
        this.message = message;
    }
}
