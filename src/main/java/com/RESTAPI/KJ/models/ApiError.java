package com.RESTAPI.KJ.models;

public class ApiError {
    private String errorMessage;
    private String details;

    public ApiError(String errorMessage, String details) {
        this.errorMessage = errorMessage;
        this.details = details;
    }

    // Getters and setters
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
