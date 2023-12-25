package com.getmygraphicscard.productservice.exception;

public class InvalidPriceRangeException extends RuntimeException {

    public InvalidPriceRangeException(String message) {
        super(message);
    }

    // If you need to include the cause of the exception
    public InvalidPriceRangeException(String message, Throwable cause) {
        super(message, cause);
    }
}
