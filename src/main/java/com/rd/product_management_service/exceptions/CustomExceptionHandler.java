package com.rd.product_management_service.exceptions;

import com.rd.product_management_service.model.CustomError;
import com.rd.product_management_service.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Error> handleCustomException(CustomException ex) {
        CustomError customError = new CustomError(ex.getErrorCode(), ex.getMessage());
        Error error = customError.toError();
        HttpStatus status = HttpStatus.valueOf(Integer.parseInt(ex.getErrorCode()));
        return new ResponseEntity<>(error, status);
    }
}