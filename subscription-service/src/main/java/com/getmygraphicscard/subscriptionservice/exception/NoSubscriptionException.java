package com.getmygraphicscard.subscriptionservice.exception;

public class NoSubscriptionException extends RuntimeException {
    public NoSubscriptionException() {
    }

    public NoSubscriptionException(String message) {
        super(message);
    }

    public NoSubscriptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSubscriptionException(Throwable cause) {
        super(cause);
    }

    public NoSubscriptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
