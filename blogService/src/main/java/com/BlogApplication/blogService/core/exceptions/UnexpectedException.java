package com.BlogApplication.blogService.core.exceptions;

public class UnexpectedException extends RuntimeException {
    public UnexpectedException(String message) {
        super(message);
    }
}
