package com.rd.product_management_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CustomError {

    private String errorCode;
    private String errorDescription;

    public Error toError() {
        Error error = new Error();
        error.setCode(this.errorCode);
        error.setDescription(this.errorDescription);
        return error;
    }
}