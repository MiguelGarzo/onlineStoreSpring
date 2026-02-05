package com.tiendaonline.tienda.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {

    private int status;
    private String code;
    private String message;

    public ApiError(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
