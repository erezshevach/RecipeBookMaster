package com.erezshevach.recipebookmaster.ui.model.response;

import com.erezshevach.recipebookmaster.Uom;

public class RecipeComponentResponseModel {

    private String componentPid;
    private double quantity;
    private Uom uom;
    private String ingredient;
    private String state;
    private Integer relatedProcessSequence;


    //-------------------getters & setters ----------------------------


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

    public Integer getRelatedProcessSequence() {
        return relatedProcessSequence;
    }

    public void setRelatedProcessSequence(Integer relatedProcessSequence) {
        this.relatedProcessSequence = relatedProcessSequence;
    }
}
