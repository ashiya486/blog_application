package com.BlogApplication.UserService.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class RestTemplateException extends HttpClientErrorException {


    public RestTemplateException(HttpStatus status, String message) {
        super(status, message);

    }
}
