package com.erezshevach.recipebookmaster.exceptions;

public enum ErrorMessages {
    RECORD_EXISTS("Record already exists"),
    NO_RECORD_FOUND("No record found"),
    MISSING_REQUIRED_FIELD("Missing a required field"),
    INVALID_INPUT("Invalid input");

    private String message;

    ErrorMessages(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
