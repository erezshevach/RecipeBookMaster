package com.erezshevach.recipebookmaster.presentation.model.request;

import com.erezshevach.recipebookmaster.shared.dto.RecipeDto;
import com.erezshevach.recipebookmaster.shared.dto.RecipeProcessDto;
import com.erezshevach.recipebookmaster.presentation.model.response.RecipeProcessResponseModel;
import com.erezshevach.recipebookmaster.presentation.model.response.RecipeResponseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeRequestModel {

    private String recipePid;
    private String name;
    private List<RecipeProcessRequestModel> processes = new ArrayList<>();
    private Integer kCalPer100g;
    private boolean containsGluten;
    private boolean containsDairy;
    private boolean containsNuts;
    private boolean containsPeanuts;
    private boolean vegan;


    //------------------- methods ----------------------------


    public boolean similar(RecipeDto other) {
        if (Objects.equals(this.name, other.getName())) return false;
        if (this.containsGluten != other.isContainsGluten()) return false;
        if (this.containsDairy != other.isContainsDairy()) return false;
        if (this.containsNuts != other.isContainsNuts()) return false;
        if (this.containsPeanuts != other.isContainsPeanuts()) return false;
        if (this.vegan != other.isVegan()) return false;
        if (this.kCalPer100g != other.getkCalPer100g()) return false;

        List<RecipeProcessDto> otherProcesses = other.getProcesses();
        int otherProcessesSize = otherProcesses != null ? otherProcesses.size() : -1;
        int processesSize = processes != null ? processes.size() : -1;

        if (processesSize != otherProcessesSize) return false;
        if (processesSize > 0) {
            for (int i = 0; i < processesSize; i++) {
                if (!processes.get(i).similar(otherProcesses.get(i))) return false;
            }
        }
        return true;
    }

    public boolean similar(RecipeResponseModel other) {
        if (Objects.equals(this.name, other.getName())) return false;
        if (this.containsGluten != other.isContainsGluten()) return false;
        if (this.containsDairy != other.isContainsDairy()) return false;
        if (this.containsNuts != other.isContainsNuts()) return false;
        if (this.containsPeanuts != other.isContainsPeanuts()) return false;
        if (this.vegan != other.isVegan()) return false;
        if (this.kCalPer100g != other.getkCalPer100g()) return false;

        List<RecipeProcessResponseModel> otherProcesses = other.getProcesses();
        int otherProcessesSize = otherProcesses != null ? otherProcesses.size() : -1;
        int processesSize = processes != null ? processes.size() : -1;

        if (processesSize != otherProcessesSize) return false;
        if (processesSize > 0) {
            for (int i = 0; i < processesSize; i++) {
                if (!processes.get(i).similar(otherProcesses.get(i))) return false;
            }
        }
        return true;
    }

    public boolean similar(RecipeRequestModel other) {
        if (Objects.equals(this.name, other.getName())) return false;
        if (this.containsGluten != other.isContainsGluten()) return false;
        if (this.containsDairy != other.isContainsDairy()) return false;
        if (this.containsNuts != other.isContainsNuts()) return false;
        if (this.containsPeanuts != other.isContainsPeanuts()) return false;
        if (this.vegan != other.isVegan()) return false;
        if (this.kCalPer100g != other.getkCalPer100g()) return false;

        List<RecipeProcessRequestModel> otherProcesses = other.getProcesses();
        int otherProcessesSize = otherProcesses != null ? otherProcesses.size() : -1;
        int processesSize = processes != null ? processes.size() : -1;

        if (processesSize != otherProcessesSize) return false;
        if (processesSize > 0) {
            for (int i = 0; i < processesSize; i++) {
                if (!processes.get(i).similar(otherProcesses.get(i))) return false;
            }
        }
        return true;
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

    public boolean isContainsGluten() {
        return containsGluten;
    }

    public void setContainsGluten(boolean containsGluten) {
        this.containsGluten = containsGluten;
    }

    public List<RecipeProcessRequestModel> getProcesses() {
        return processes;
    }

    public void setProcesses(List<RecipeProcessRequestModel> processes) {
        this.processes = processes;
    }

    public Integer getkCalPer100g() {
        return kCalPer100g;
    }

    public void setkCalPer100g(Integer kCalPer100g) {
        this.kCalPer100g = kCalPer100g;
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
