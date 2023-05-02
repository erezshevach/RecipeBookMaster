package com.erezshevach.recipebookmaster.ui.model.request;

import com.erezshevach.recipebookmaster.shared.dto.RecipeDto;
import com.erezshevach.recipebookmaster.shared.dto.RecipeProcessDto;
import com.erezshevach.recipebookmaster.ui.model.response.RecipeProcessResponseModel;
import com.erezshevach.recipebookmaster.ui.model.response.RecipeResponseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeRequestModel {


    private String recipePid;
    private String name;
    private List<RecipeProcessRequestModel> processes = new ArrayList<>();

//    private String[] processes_input;
//    private String[] ingredients;
//    private String[] states;
//    private double[] quantities;
//    private String[] units;
//    private int[] sequences;


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

    public List<RecipeProcessRequestModel> getProcesses() {
        return processes;
    }

    public void setProcesses(List<RecipeProcessRequestModel> processes) {
        this.processes = processes;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String[] getProcesses_input() {
//        return processes_input;
//    }
//
//    public void setProcesses_input(String[] processes) {
//        this.processes_input = processes;
//    }
//
//    public String[] getIngredients() {
//        return ingredients;
//    }
//
//    public void setIngredients(String[] ingredients) {
//        this.ingredients = ingredients;
//    }
//
//    public String[] getStates() {
//        return states;
//    }
//
//    public void setStates(String[] states) {
//        this.states = states;
//    }
//
//    public double[] getQuantities() {
//        return quantities;
//    }
//
//    public void setQuantities(double[] quantities) {
//        this.quantities = quantities;
//    }
//
//    public String[] getUnits() {
//        return units;
//    }
//
//    public void setUnits(String[] units) {
//        this.units = units;
//    }
//
//    public int[] getSequences() {
//        return sequences;
//    }
//
//    public void setSequences(int[] sequences) {
//        this.sequences = sequences;
//    }
}
