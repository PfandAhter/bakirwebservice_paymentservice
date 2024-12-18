package com.bakirwebservice.paymentservice.exceptions;

import lombok.Getter;

public class NotFoundException extends RuntimeException{

    @Getter
    private String message;

    public NotFoundException(){
        super();
        this.message = null;
    }

    public NotFoundException (String message){
        super();
        this.message = message;
    }

}
