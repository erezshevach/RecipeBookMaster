package com.erezshevach.recipebookmaster.ui.model.response;

import com.erezshevach.recipebookmaster.shared.dto.RecipeDto;
import com.erezshevach.recipebookmaster.shared.dto.RecipeProcessDto;
import com.erezshevach.recipebookmaster.ui.model.request.RecipeProcessRequestModel;
import com.erezshevach.recipebookmaster.ui.model.request.RecipeRequestModel;

import java.util.List;
import java.util.Objects;

public class RecipeResponseModel {
    private String recipePid;
    private String name;
    private List<RecipeProcessResponseModel> processes;
    private Integer kCalPer100g;
    private boolean containsGluten;
    private boolean containsDairy;
    private boolean containsNuts;
    private boolean containsPeanuts;
    private boolean vegan;


    //------------------- methods ----------------------------


    public boolean similar(RecipeDto other) {
        List<RecipeProcessDto> otherProcesses = other.getProcesses();
        int processesSize = processes != null ? processes.size() : -1;
        int otherProcessesSize = otherProcesses != null ? otherProcesses.size() : -1;
        boolean processesSimilarity = processesSize == otherProcessesSize;
        if (processesSimilarity && processesSize > 0) {
            for (int i = 0; i < processesSize; i++) {
                if (!processes.get(i).similar(otherProcesses.get(i))){
                    processesSimilarity = false;
                    break;
                }
            }

        }
        return Objects.equals(this.name, other.getName()) && processesSimilarity;
    }

    public boolean similar(RecipeResponseModel other) {
        List<RecipeProcessResponseModel> otherProcesses = other.getProcesses();
        int processesSize = processes != null ? processes.size() : -1;
        int otherProcessesSize = otherProcesses != null ? otherProcesses.size() : -1;
        boolean processesSimilarity = processesSize == otherProcessesSize;
        if (processesSimilarity && processesSize > 0) {
            for (int i = 0; i < processesSize; i++) {
                if (!processes.get(i).similar(otherProcesses.get(i))){
                    processesSimilarity = false;
                    break;
                }
            }

        }
        return Objects.equals(this.name, other.getName()) && processesSimilarity;
    }

    public boolean similar(RecipeRequestModel other) {
        List<RecipeProcessRequestModel> otherProcesses = other.getProcesses();
        int processesSize = processes != null ? processes.size() : -1;
        int otherProcessesSize = otherProcesses != null ? otherProcesses.size() : -1;
        boolean processesSimilarity = processesSize == otherProcessesSize;
        if (processesSimilarity && processesSize > 0) {
            for (int i = 0; i < processesSize; i++) {
                if (!processes.get(i).similar(otherProcesses.get(i))){
                    processesSimilarity = false;
                    break;
                }
            }

        }
        return Objects.equals(this.name, other.getName()) && processesSimilarity;
    }


    //------------------- getters & setters ----------------------------


    public String getRecipePid() {
        return recipePid;
    }

    public void setRecipePid(String recipePid) {
        this.recipePid = recipePid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RecipeProcessResponseModel> getProcesses() {
        return processes;
    }

    public void setProcesses(List<RecipeProcessResponseModel> processes) {
        this.processes = processes;
    }

    public Integer getkCalPer100g() {
        return kCalPer100g;
    }

    public void setkCalPer100g(Integer kCalPer100g) {
        this.kCalPer100g = kCalPer100g;
    }

    public boolean isContainsGluten() {
        return containsGluten;
    }

    public void setContainsGluten(boolean containsGluten) {
        this.containsGluten = containsGluten;
    }

    public boolean isContainsDairy() {
        return containsDairy;
    }

    public void setContainsDairy(boolean containsDairy) {
        this.containsDairy = containsDairy;
    }

    public boolean isContainsNuts() {
        return containsNuts;
    }

    public void setContainsNuts(boolean containsNuts) {
        this.containsNuts = containsNuts;
    }

    public boolean isContainsPeanuts() {
        return containsPeanuts;
    }

    public void setContainsPeanuts(boolean containsPeanuts) {
        this.containsPeanuts = containsPeanuts;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }
}
