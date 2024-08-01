package com.RESTAPI.KJ.exceptions;

public class DatabaseHealthCheckException extends RuntimeException {

    public DatabaseHealthCheckException(String message) {
        super(message);
    }

    public DatabaseHealthCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}
