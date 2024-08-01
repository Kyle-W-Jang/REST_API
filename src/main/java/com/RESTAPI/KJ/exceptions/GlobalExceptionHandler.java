package com.RESTAPI.KJ.exceptions;

import com.RESTAPI.KJ.models.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<?> invalidIdException(InvalidIdException ex) {
        ApiError apiError = new ApiError("Not Valid Order Id", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> orderNotFoundException(OrderNotFoundException ex) {
        ApiError apiError = new ApiError("Order Not Found in DB", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderAlreadyExistsException.class)
    public ResponseEntity<?> orderAlreadyExistsException(OrderAlreadyExistsException ex) {
        ApiError apiError = new ApiError("Order Already Exist in DB", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<?> invalidAmountException(InvalidAmountException ex) {
        ApiError apiError = new ApiError("Invalid Amount Format", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCurrencyCodeException.class)
    public ResponseEntity<?> invalidCurrencyCodeException(InvalidCurrencyCodeException ex) {
        ApiError apiError = new ApiError("Not Valid Currency Code", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DatabaseHealthCheckException.class)
    public ResponseEntity<?> databaseHealthCheckException(DatabaseHealthCheckException ex) {
        ApiError apiError = new ApiError("Data Base Down", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex) {
        ApiError apiError = new ApiError("Internal Server Error", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
