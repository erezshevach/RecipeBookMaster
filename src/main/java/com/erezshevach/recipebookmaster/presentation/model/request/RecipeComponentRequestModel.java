package com.erezshevach.recipebookmaster.presentation.model.request;

import com.erezshevach.recipebookmaster.shared.Uom;
import com.erezshevach.recipebookmaster.shared.dto.RecipeComponentDto;
import com.erezshevach.recipebookmaster.presentation.model.response.RecipeComponentResponseModel;

public class RecipeComponentRequestModel {

    private double quantity;
    private Uom uom;
    private String ingredient;
    private String state;


    //------------------- methods ----------------------------


    public boolean similar(RecipeComponentDto other) {
        return this.quantity == other.getQuantity() &&
                this.uom == other.getUom() &&
                this.ingredient.equals(other.getIngredient())  &&
                this.state.equals(other.getState());
    }

    public boolean similar(RecipeComponentResponseModel other) {
        return this.quantity == other.getQuantity() &&
                this.uom == other.getUom() &&
                this.ingredient.equals(other.getIngredient())  &&
                this.state.equals(other.getState());
    }

    public boolean similar(RecipeComponentRequestModel other) {
        return this.quantity == other.getQuantity() &&
                this.uom == other.getUom() &&
                this.ingredient.equals(other.getIngredient())  &&
                this.state.equals(other.getState());
    }


    //------------------- getters & setters ----------------------------


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
