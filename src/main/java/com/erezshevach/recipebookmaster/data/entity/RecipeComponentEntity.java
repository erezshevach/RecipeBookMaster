package com.erezshevach.recipebookmaster.data.entity;

import com.erezshevach.recipebookmaster.shared.Uom;
import com.erezshevach.recipebookmaster.shared.dto.RecipeComponentDto;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "recipe_components")
public class RecipeComponentEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 24L;

    @Id
    @GeneratedValue
    private long id;
    private String componentPid;
    @Column(nullable = false)
    private double quantity;
    @Column(nullable = false)
    private Uom uom;
    @Column(nullable = false)
    private String ingredient;
    private String state;
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private RecipeEntity ofRecipe;
    @ManyToOne
    @JoinColumn(name = "recipe_process_id")
    private RecipeProcessEntity ofProcess;


    // ---------- constructors ----------


    protected RecipeComponentEntity() {
    }

    public RecipeComponentEntity(double quantity, Uom uom, String ingredient, String state) {
        this(quantity, uom, ingredient, state, null, null);
    }

    public RecipeComponentEntity(double quantity, @NotNull Uom uom, @NotNull String ingredient, String state, RecipeProcessEntity process, RecipeEntity recipeEntity) {
        if (quantity <= 0) throw new IllegalArgumentException("Component's quantity cannot be 0 or negative.");
        if (uom == null) throw new IllegalArgumentException("Component's UOM is required.");
        if (ingredient == null || ingredient.isEmpty() || ingredient.isBlank()) throw new IllegalArgumentException("Component's ingredient is required.");

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

    public static boolean compareComponents(RecipeComponentEntity first, RecipeComponentEntity second) {
        boolean matchingQuantities = first.getQuantity() == second.getQuantity();
        boolean matchingUom = first.getUom() == second.getUom();
        boolean matchingIngredients = Objects.equals(first.getIngredient(), second.getIngredient());
        boolean matchingStates = Objects.equals(first.getState(), second.getState());
        return matchingQuantities && matchingUom && matchingIngredients && matchingStates;
    }


    // ---------- getters/setters ----------


    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public String getComponentPid() {
        return componentPid;
    }

    public void setComponentPid(String processPid) {
        this.componentPid = processPid;
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
