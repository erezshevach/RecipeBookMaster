package com.erezshevach.recipebookmaster.ui.model.request;

public class RecipeDetailsRequestModel {

    private String name;
    private String[] processes_input;
    private String[] ingredients;
    private String[] states;
    private double[] quantities;
    private String[] units;
    private int[] sequences;


    //-------------------getters & setters ----------------------------

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String[] getProcesses_input() {
        return processes_input;
    }
    public void setProcesses_input(String[] processes) {
        this.processes_input = processes;
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
}
