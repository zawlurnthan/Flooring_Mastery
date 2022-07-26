package com.zthan.ui;

public class NotFutureDateException extends Exception {
    public NotFutureDateException(String message) {
        super(message);
    }

    public NotFutureDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
