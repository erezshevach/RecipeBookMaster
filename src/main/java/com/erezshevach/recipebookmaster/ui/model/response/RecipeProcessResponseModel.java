package com.erezshevach.recipebookmaster.ui.model.response;

import java.util.List;

public class RecipeProcessResponseModel {
    private String processPid;
    private int sequence;
    private String description;
    private List<RecipeComponentResponseModel> components;


    //-------------------getters & setters ----------------------------


    public String getProcessPid() {
        return processPid;
    }

    public void setProcessPid(String processPid) {
        this.processPid = processPid;
    }

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

    public List<RecipeComponentResponseModel> getComponents() {
        return components;
    }

    public void setComponents(List<RecipeComponentResponseModel> components) {
        this.components = components;
    }
}
