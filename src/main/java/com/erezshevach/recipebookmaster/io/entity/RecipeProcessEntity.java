package com.erezshevach.recipebookmaster.io.entity;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "recipe_processes")
public class RecipeProcessEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 24L;

    @Id
    @GeneratedValue
    private long id;
    private String processPid;
    @Column(nullable = false)
    private int sequence;
    @Column(nullable = false)
    private String description;
    @OneToMany(mappedBy = "ofProcess", cascade = CascadeType.ALL)
    private List<RecipeComponentEntity> components = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private RecipeEntity ofRecipe;

    // ---------- constructors ----------
    protected RecipeProcessEntity() {
    }

    public RecipeProcessEntity(int sequence, @NotNull String description, RecipeEntity recipeEntity) {
        this.sequence = sequence;
        this.description = description;
        this.ofRecipe = recipeEntity;
    }

    public RecipeProcessEntity(int sequence, @NotNull String description, List<RecipeComponentEntity> components, RecipeEntity recipeEntity) {
        this(sequence, description, recipeEntity);
        this.components = components;
    }

    // ---------- methods ----------
    public String toString() {
        return sequence + ". " + description;
    }

    public String toStringDetailed() {
        StringBuilder s = new StringBuilder()
                .append(sequence)
                .append(". ")
                .append(description);
        for (RecipeComponentEntity c : components) {
            if (c != null) {
                s.append("\n").append(c);
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
        for (RecipeComponentEntity c : getComponents()) {
            c.setOfRecipe(recipeEntity);
        }
    }
}
