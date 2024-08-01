package com.RESTAPI.KJ.exceptions;

public class InvalidCurrencyCodeException extends RuntimeException{

    public InvalidCurrencyCodeException (String message){
        super(message);
    }
}
