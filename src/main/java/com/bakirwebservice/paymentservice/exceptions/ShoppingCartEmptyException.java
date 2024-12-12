package com.bakirwebservice.paymentservice.exceptions;

import lombok.Getter;

public class ShoppingCartEmptyException extends Exception {
    @Getter
    private String message;

    public ShoppingCartEmptyException(){
        super();
        this.message = null;
    }

    public ShoppingCartEmptyException(String message){
        super();
        this.message = message;
    }
}
