package com.erezshevach.recipebookmaster.shared.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class RecipeProcessDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 24L;

    private Long id;
    private String processPid;
    private int sequence;
    private String description;
    private List<RecipeComponentDto> components;
    private RecipeDto ofRecipe;


    //-------------------getters & setters ----------------------------


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<RecipeComponentDto> getComponents() {
        return components;
    }

    public void setComponents(List<RecipeComponentDto> components) {
        this.components = components;
    }

    public RecipeDto getOfRecipe() {
        return ofRecipe;
    }

    public void setOfRecipe(RecipeDto ofRecipe) {
        this.ofRecipe = ofRecipe;
    }
}
