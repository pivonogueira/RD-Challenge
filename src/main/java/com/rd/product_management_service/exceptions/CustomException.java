package com.rd.product_management_service.exceptions;

public class CustomException extends RuntimeException {
    private final String errorCode;

    public CustomException(String errorCode, String errorDescription) {
        super(errorDescription);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}