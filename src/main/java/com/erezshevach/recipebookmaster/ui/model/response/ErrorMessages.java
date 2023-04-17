package com.erezshevach.recipebookmaster.ui.model.response;

public enum ErrorMessages {
    RECORD_EXISTS("Record already exists"),
    NO_RECORD_FOUND("No record found"),
    MISSING_REQUIRED_FIELD("Missing a required field");

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
