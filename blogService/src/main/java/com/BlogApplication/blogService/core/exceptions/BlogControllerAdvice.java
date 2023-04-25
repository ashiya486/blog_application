package com.BlogApplication.blogService.core.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class BlogControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFound(NotFoundException ex, WebRequest request){
        return new ResponseEntity<Object>(new ErrorDetails(ex.getMessage(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object>badRequest(BadRequestException ex,WebRequest request){
        return new ResponseEntity<Object>(new ErrorDetails(ex.getMessage(),LocalDateTime.now()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RestTemplateException.class)
    public ResponseEntity<Object>restTempExcept(RestTemplateException ex,WebRequest request){
        return new ResponseEntity<Object>(new ErrorDetails(ex.getMessage(),LocalDateTime.now()),ex.getStatusCode());
    }
    @ExceptionHandler(UnexpectedException.class)
    public ResponseEntity<Object>restTempExcept(UnexpectedException ex,WebRequest request){
        return new ResponseEntity<Object>(new ErrorDetails("something unexpected occured "+ex.getMessage(),LocalDateTime.now()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<ErrorModel> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(
                        err ->new ErrorModel(err.getField(),err.getRejectedValue(),err.getDefaultMessage()))
                .distinct()
                .collect(Collectors.toList());
        return new ResponseEntity<Object>(errorMessages,HttpStatus.BAD_REQUEST);
    }
}
