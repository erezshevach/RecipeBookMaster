package com.erezshevach.recipebookmaster.recipebookmaster.exceptions;

public class RecipeException extends RuntimeException{

    private String identifier;
    private String objectClass;

    public RecipeException(String identifier, String objectClass, String message) {
        super(message);
        this.identifier = identifier;
        this.objectClass = objectClass;
    }

    //-------------------getters & setters ----------------------------

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }
}
