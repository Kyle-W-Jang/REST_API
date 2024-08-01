package com.RESTAPI.KJ.exceptions;

public class InvalidIdException extends  RuntimeException{

    public InvalidIdException (String message){
        super(message);
    }
}
