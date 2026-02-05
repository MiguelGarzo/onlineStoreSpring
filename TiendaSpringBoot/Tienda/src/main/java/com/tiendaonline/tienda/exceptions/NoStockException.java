package com.tiendaonline.tienda.exceptions;

public class NoStockException extends RuntimeException {
    public NoStockException(String message) {
        super(message);
    }
}
