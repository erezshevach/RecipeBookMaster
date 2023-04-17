package com.erezshevach.recipebookmaster.ui.model.response;

public class RecipeResponseModel {
    private String name;
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
