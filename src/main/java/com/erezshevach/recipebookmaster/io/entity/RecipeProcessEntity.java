package com.erezshevach.recipebookmaster.io.entity;

import com.erezshevach.recipebookmaster.shared.dto.RecipeComponentDto;
import com.erezshevach.recipebookmaster.shared.dto.RecipeProcessDto;
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

    public RecipeProcessEntity(int sequence, String description) {
        this(sequence, description, null, null);
    }

    public RecipeProcessEntity(int sequence, @NotNull String description, RecipeEntity recipeEntity) {
        this(sequence, description, null, recipeEntity);
    }

    public RecipeProcessEntity(int sequence, @NotNull String description, List<RecipeComponentEntity> components, RecipeEntity recipeEntity) {
        if (sequence <= 0) throw new IllegalArgumentException("Process sequence cannot be 0 or negative.");
        if (description == null || description.isEmpty() || description.isBlank())
            throw new IllegalArgumentException("Process description is required.");

        this.sequence = sequence;
        this.description = description;
        this.ofRecipe = recipeEntity;

        if (components != null) {
            for (RecipeComponentEntity c : components) {
                c.setOfProcess(this);
                c.setOfRecipe(recipeEntity);
            }
            this.components = components;
        }
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

    public boolean similar(RecipeProcessEntity other) {
        List<RecipeComponentEntity> otherComponents = other.getComponents();
        int componentsSize = components != null ? components.size() : -1;
        int otherComponentsSize = otherComponents != null ? otherComponents.size() : -1;
        boolean componentsSimilarity = componentsSize == otherComponentsSize;
        if (componentsSimilarity && componentsSize > 0) {
            for (int i = 0; i < componentsSize; i++) {
                if (!components.get(i).similar(otherComponents.get(i))){
                    componentsSimilarity = false;
                    break;
                }
            }

        }
        return this.sequence == other.getSequence() &&
                this.description == other.getDescription() &&
                componentsSimilarity;
    }

    public boolean similar(RecipeProcessDto other) {
        List<RecipeComponentDto> otherComponents = other.getComponents();
        int componentsSize = components != null ? components.size() : -1;
        int otherComponentsSize = otherComponents != null ? otherComponents.size() : -1;
        boolean componentsSimilarity = componentsSize == otherComponentsSize;
        if (componentsSimilarity && componentsSize > 0) {
            for (int i = 0; i < componentsSize; i++) {
                if (!components.get(i).similar(otherComponents.get(i))){
                    componentsSimilarity = false;
                    break;
                }
            }

        }
        return this.sequence == other.getSequence() &&
                this.description == other.getDescription() &&
                componentsSimilarity;
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
        if (components != null) {
            for (RecipeComponentEntity c : components) {
                c.setOfProcess(this);
                c.setOfRecipe(this.ofRecipe);
            }
        }
        this.components = components;
    }

    public RecipeEntity getOfRecipe() {
        return ofRecipe;
    }

    public void setOfRecipe(RecipeEntity recipeEntity) {
        this.ofRecipe = recipeEntity;
        if (getComponents() != null) {
            for (RecipeComponentEntity c : getComponents()) {
                c.setOfRecipe(recipeEntity);
            }
        }
    }
}
