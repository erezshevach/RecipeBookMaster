package com.erezshevach.recipebookmaster.recipebookmaster.exceptions;

public class RecipeException extends RuntimeException{

    private String recipeName;

    public RecipeException(String recipeName, String message) {
        super(message);
        this.recipeName = recipeName;
    }

    //-------------------getters & setters ----------------------------

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
