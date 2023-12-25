package com.getmygraphicscard.productservice.exception;

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String message) {
        super(message);
    }

    // If you need to include the cause of the exception
    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
