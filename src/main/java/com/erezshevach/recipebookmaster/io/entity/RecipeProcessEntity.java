package com.erezshevach.recipebookmaster.io.entity;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


@Entity(name = "recipe_processes")
public class RecipeProcessEntity {

    @Id
    @GeneratedValue
    private long id;
    private int sequence;
    private String description;
    @OneToMany(mappedBy = "ofProcess", cascade = CascadeType.ALL)
    private List<RecipeComponentEntity> components = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private RecipeEntity ofRecipe;

    // ---------- constructors ----------
    protected RecipeProcessEntity() {
    }
    public RecipeProcessEntity(int sequence, @NotNull String description, @NotNull RecipeEntity recipeEntity) {
        this.sequence = sequence;
        this.description = description;
        this.ofRecipe = recipeEntity;
    }
    public RecipeProcessEntity(int sequence, @NotNull String description, List<RecipeComponentEntity> components, @NotNull RecipeEntity recipeEntity) {
        this(sequence, description, recipeEntity);
        this.components = components;
    }

    // ---------- methods ----------
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(sequence)
                .append(": ")
                .append(description);
        for (RecipeComponentEntity c : components) {
            if (c != null) {
                s.append("\n").append(c.toString());
            }
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

    public List<RecipeComponentEntity> getComponents() {
        return components;
    }

    public void setComponents(List<RecipeComponentEntity> components) {
        this.components = components;
    }

    public RecipeEntity getOfRecipe() {
        return ofRecipe;
    }

    public void setOfRecipe(RecipeEntity recipeEntity) {
        this.ofRecipe = recipeEntity;
    }
}
