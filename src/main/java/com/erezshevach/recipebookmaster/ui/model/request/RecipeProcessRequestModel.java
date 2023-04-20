package com.erezshevach.recipebookmaster.ui.model.request;


import java.util.ArrayList;
import java.util.List;

public class RecipeProcessRequestModel {

    private int sequence;
    private String description;
    private List<RecipeComponentRequestModel> components = new ArrayList<>();

    //-------------------getters & setters ----------------------------


    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RecipeComponentRequestModel> getComponents() {
        return components;
    }

    public void setComponents(List<RecipeComponentRequestModel> components) {
        this.components = components;
    }
}
