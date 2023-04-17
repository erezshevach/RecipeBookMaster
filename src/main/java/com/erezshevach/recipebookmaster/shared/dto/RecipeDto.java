package com.erezshevach.recipebookmaster.shared.dto;

import com.erezshevach.recipebookmaster.io.entity.RecipeProcessEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class RecipeDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private String[] ingredients;
    private String[] states;
    private double[] quantities;
    private String[] units;
    private int[] sequences;
    private String[] processes_input;
    private List<RecipeProcessEntity> processes;
    private String[] processes_output;
    private String[] components_output;
    private Integer kCalPer100g;
    private boolean containsGluten;
    private boolean containsDairy;
    private boolean containsNuts;
    private boolean containsPeanuts;
    private boolean vegan;


    //-------------------getters & setters ----------------------------

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String[] getIngredients() {
        return ingredients;
    }
    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
    public String[] getStates() {
        return states;
    }
    public void setStates(String[] states) {
        this.states = states;
    }
    public double[] getQuantities() {
        return quantities;
    }
    public void setQuantities(double[] quantities) {
        this.quantities = quantities;
    }
    public String[] getUnits() {
        return units;
    }
    public void setUnits(String[] units) {
        this.units = units;
    }
    public int[] getSequences() {
        return sequences;
    }
    public void setSequences(int[] sequences) {
        this.sequences = sequences;
    }
    public String[] getProcesses_input() {
        return processes_input;
    }
    public void setProcesses_input(String[] processes) {
        this.processes_input = processes;
    }
    public List<RecipeProcessEntity> getProcesses() {
        return processes;
    }
    public void setProcesses(List<RecipeProcessEntity> processes) {
        this.processes = processes;
    }
    public String[] getProcesses_output() {
        return processes_output;
    }
    public void setProcesses_output(String[] processes_output) {
        this.processes_output = processes_output;
    }
    public String[] getComponents_output() {
        return components_output;
    }
    public void setComponents_output(String[] components_output) {
        this.components_output = components_output;
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
