package com.BlogApplication.UserService.core.exceptions;

public class ErrorModel {
    private String fieldName;
    private Object RejectedValue;
    public ErrorModel(String fieldName,Object rejectedValue,String message) {
        this.fieldName = fieldName;
        this.RejectedValue = rejectedValue;
        this.messageError = message;
    }
    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    public Object getRejectedValue() {
        return RejectedValue;
    }
    public void setRejectedValue(Object rejectedValue) {
        RejectedValue = rejectedValue;
    }
    public String getMessageError() {
        return messageError;
    }
    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }
    private String messageError;

}
