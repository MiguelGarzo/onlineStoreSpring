package com.tiendaonline.tienda.exceptions;

public class NotExistsException extends RuntimeException {
    public NotExistsException(String message) {
        super(message);
    }
}