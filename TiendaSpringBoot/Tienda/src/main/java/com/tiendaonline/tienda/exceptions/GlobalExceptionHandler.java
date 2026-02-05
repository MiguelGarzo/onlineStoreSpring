package com.tiendaonline.tienda.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserExists(UserAlreadyExistsException ex) {
        ApiError error = new ApiError(409, "USER_EXISTS", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiError> handleInvalidInput(InvalidInputException ex) {
        ApiError error = new ApiError(400, "INVALID_INPUT", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        ex.printStackTrace();
        ApiError error = new ApiError(500, "SERVER_ERROR", "Ocurrió un error inesperado");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotExistsException.class)
    public ResponseEntity<ApiError> handleNotExists(NotExistsException ex) {
        ex.printStackTrace();
        ApiError error = new ApiError(404, "NOT FOUND", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoStockException.class)
    public ResponseEntity<ApiError> handleNoStock(NoStockException ex) {
        ex.printStackTrace();
        ApiError error = new ApiError(409, "NOT STOCK", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoStockException.class)
    public ResponseEntity<ApiError> handleEmpty(EmptyException ex) {
        ex.printStackTrace();
        ApiError error = new ApiError(400, "EMPTY", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
