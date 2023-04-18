package com.erezshevach.recipebookmaster.io.entity;

import com.erezshevach.recipebookmaster.Uom;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

@Entity(name = "recipe_components")
public class RecipeComponentEntity {

    @Id
    @GeneratedValue//(strategy = GenerationType.IDENTITY)
    private long id;
    //@ManyToOne
    //private Ingredient ingredient;
    private String ingredient;
    //@ManyToOne
    //private IngredientState state;
    private String state;
    private double quantity;
    private Uom uom;
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private RecipeEntity ofRecipe;
    @ManyToOne
    @JoinColumn(name = "recipeprocess_id")
    private RecipeProcessEntity ofProcess;

    // ---------- constructors ----------
    protected RecipeComponentEntity() {
    }

    public RecipeComponentEntity(@NotNull String ingredient, String state, double quantity, @NotNull Uom uom, RecipeEntity recipeEntity, RecipeProcessEntity process) {
        this.ingredient = ingredient;
        this.state = state;
        this.quantity = quantity;
        this.uom = uom;
        this.ofRecipe = recipeEntity;
        this.ofProcess = process;
    }

    // ---------- methods ----------
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(quantity).append(" ").append(uom).append(" ").append(ingredient);
        if (state != null) {
            s.append(" ").append(state);
        }
        return s.toString();
    }

    // ---------- getters/setters ----------
    public long getId() {
        return id;
    }
    private void setId(long id) {
        this.id = id;
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
    public RecipeEntity getOfRecipe() {
        return ofRecipe;
    }
    public void setOfRecipe(RecipeEntity recipeEntity) {
        this.ofRecipe = recipeEntity;
    }
    public RecipeProcessEntity getOfProcess() {
        return ofProcess;
    }
    public void setOfProcess(RecipeProcessEntity ofProcess) {
        this.ofProcess = ofProcess;
    }
}
