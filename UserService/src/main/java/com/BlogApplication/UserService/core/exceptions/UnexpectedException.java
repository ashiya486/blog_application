package com.BlogApplication.UserService.core.exceptions;

public class UnexpectedException extends RuntimeException {
    public UnexpectedException(String message) {
        super(message);
    }
}
