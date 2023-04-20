package com.erezshevach.recipebookmaster.ui.model.request;

import com.erezshevach.recipebookmaster.Uom;

public class RecipeComponentRequestModel {

    private double quantity;
    private Uom uom;
    private String ingredient;
    private String state;

    //-------------------getters & setters ----------------------------


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


}
