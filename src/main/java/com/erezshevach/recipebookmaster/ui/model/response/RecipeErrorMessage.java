package com.erezshevach.recipebookmaster.ui.model.response;

import java.util.Date;

public class RecipeErrorMessage {
    private Date timeStamp;
    private String recipeName;
    private String errorMessage;


    public RecipeErrorMessage() {
    }
    public RecipeErrorMessage(Date timeStamp, String recipeName, String errorMessage) {
        this.timeStamp = timeStamp;
        this.recipeName = recipeName;
        this.errorMessage = errorMessage;
    }


    //-------------------getters & setters ----------------------------
    public Date getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
    public String getRecipeName() {
        return recipeName;
    }
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
