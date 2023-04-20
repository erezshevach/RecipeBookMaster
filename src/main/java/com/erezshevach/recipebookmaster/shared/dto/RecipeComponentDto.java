package com.erezshevach.recipebookmaster.shared.dto;

import com.erezshevach.recipebookmaster.Uom;

import java.io.Serial;
import java.io.Serializable;

public class RecipeComponentDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 24L;

    private long id;
    private String componentPid;
    private double quantity;
    private Uom uom;
    private String ingredient;
    private String state;
    private RecipeDto ofRecipe;
    private RecipeProcessDto ofProcess;
    private Integer relatedProcessSequence;


    //-------------------getters & setters ----------------------------


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComponentPid() {
        return componentPid;
    }

    public void setComponentPid(String componentPid) {
        this.componentPid = componentPid;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Uom getUom() {
        return uom;
    }

    public void setUom(Uom uom) {
        this.uom = uom;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public RecipeDto getOfRecipe() {
        return ofRecipe;
    }

    public void setOfRecipe(RecipeDto ofRecipe) {
        this.ofRecipe = ofRecipe;
    }

    public RecipeProcessDto getOfProcess() {
        return ofProcess;
    }

    public void setOfProcess(RecipeProcessDto ofProcess) {
        this.ofProcess = ofProcess;
    }

    public Integer getRelatedProcessSequence() {
        return relatedProcessSequence;
    }

    public void setRelatedProcessSequence(Integer relatedProcessSequence) {
        this.relatedProcessSequence = relatedProcessSequence;
    }
}
