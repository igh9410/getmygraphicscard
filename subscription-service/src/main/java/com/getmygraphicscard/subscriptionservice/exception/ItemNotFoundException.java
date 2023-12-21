package com.getmygraphicscard.subscriptionservice.exception;

public class ItemNotFoundException extends Exception {

    // Constructor with custom message
    public ItemNotFoundException(String message) {
        super(message);
    }

    // Constructor with custom message and cause
    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause
    public ItemNotFoundException(Throwable cause) {
        super(cause);
    }
}

