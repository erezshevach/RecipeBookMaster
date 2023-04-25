package com.erezshevach.recipebookmaster.shared.dto;

import com.erezshevach.recipebookmaster.io.entity.RecipeComponentEntity;
import com.erezshevach.recipebookmaster.io.entity.RecipeProcessEntity;
import com.erezshevach.recipebookmaster.ui.model.request.RecipeComponentRequestModel;
import com.erezshevach.recipebookmaster.ui.model.request.RecipeProcessRequestModel;
import com.erezshevach.recipebookmaster.ui.model.response.RecipeComponentResponseModel;
import com.erezshevach.recipebookmaster.ui.model.response.RecipeProcessResponseModel;

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


    //------------------- methods ----------------------------


    public String toString() {
        return sequence + ". " + description;
    }

    public String toStringDetailed() {
        StringBuilder s = new StringBuilder()
                .append(sequence)
                .append(". ")
                .append(description);
        for (RecipeComponentDto c : components) {
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

    public boolean similar(RecipeProcessRequestModel other) {
        List<RecipeComponentRequestModel> otherComponents = other.getComponents();
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

    public boolean similar(RecipeProcessResponseModel other) {
        List<RecipeComponentResponseModel> otherComponents = other.getComponents();
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


    //------------------- getters & setters ----------------------------


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
