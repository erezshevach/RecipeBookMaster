package com.erezshevach.recipebookmaster.exceptions;

import java.util.Date;

public class RecipeErrorMessage {
    private Date timeStamp;
    private String objectClass;
    private String identifier;
    private String errorMessage;


    public RecipeErrorMessage() {
    }

    public RecipeErrorMessage(Date timeStamp, String identifier, String objectClass, String errorMessage) {
        this.timeStamp = timeStamp;
        this.objectClass = objectClass;
        this.identifier = identifier;
        this.errorMessage = errorMessage;
    }


    //-------------------getters & setters ----------------------------


    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
