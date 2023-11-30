package com.erezshevach.recipebookmaster.presentation.model.response;

import com.erezshevach.recipebookmaster.shared.dto.RecipeComponentDto;
import com.erezshevach.recipebookmaster.shared.dto.RecipeProcessDto;
import com.erezshevach.recipebookmaster.presentation.model.request.RecipeComponentRequestModel;
import com.erezshevach.recipebookmaster.presentation.model.request.RecipeProcessRequestModel;

import java.util.List;

public class RecipeProcessResponseModel {
    private String processPid;
    private int sequence;
    private String description;
    private List<RecipeComponentResponseModel> components;


    //------------------- methods ----------------------------


    public String toString() {
        return sequence + ". " + description;
    }

    public String toStringDetailed() {
        StringBuilder s = new StringBuilder()
                .append(sequence)
                .append(". ")
                .append(description);
        for (RecipeComponentResponseModel c : components) {
            if (c != null) {
                s.append("\n").append(c);
            }
        }
        return s.toString();
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
                this.description.equals(other.getDescription())  &&
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
                this.description.equals(other.getDescription())  &&
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
                this.description.equals(other.getDescription())  &&
                componentsSimilarity;
    }


    //------------------- getters & setters ----------------------------


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

    public List<RecipeComponentResponseModel> getComponents() {
        return components;
    }

    public void setComponents(List<RecipeComponentResponseModel> components) {
        this.components = components;
    }
}
