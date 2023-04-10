package com.erezshevach.recipebookmaster.shared.dto;

import java.io.Serial;
import java.io.Serializable;

public class RecipeDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private String[] processes;
    private String[] ingredients;
    private String[] states;
    private double[] quantities;
    private String[] units;
    private int[] sequences;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getProcesses() {
        return processes;
    }

    public void setProcesses(String[] processes) {
        this.processes = processes;
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
