package com.GetMyGraphicsCard.subscriptionservice.exception;

public class DuplicateSubscriptionException extends RuntimeException {
    public DuplicateSubscriptionException() {
    }

    public DuplicateSubscriptionException(String message) {
        super(message);
    }

    public DuplicateSubscriptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateSubscriptionException(Throwable cause) {
        super(cause);
    }

    public DuplicateSubscriptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
