package com.erezshevach.recipebookmaster.io.entity;

import com.erezshevach.recipebookmaster.shared.dto.RecipeComponentDto;
import com.erezshevach.recipebookmaster.shared.dto.RecipeDto;
import com.erezshevach.recipebookmaster.shared.dto.RecipeProcessDto;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "recipes")
public class RecipeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 24L;

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "ofRecipe", cascade = CascadeType.ALL)
    private List<RecipeProcessEntity> processes = new ArrayList<>();
    //@ManyToMany
    //private List<RecipeTag> tags;
    private Integer kCalPer100g = 0;
    private boolean containsGluten = false;
    private boolean containsDairy = false;
    private boolean containsNuts = false;
    private boolean containsPeanuts = false;
    private boolean vegan = false;


    // ---------- constructors ----------


    protected RecipeEntity() {
    }

    public RecipeEntity(@NotNull String name) {
        if (name == null || name.isEmpty() || name.isBlank()) throw new IllegalArgumentException("Recipe name is required");

        this.name = name;
    }

    public RecipeEntity(@NotNull String name, List<RecipeProcessEntity> processes) {
        this(name);

        if (processes != null) {
            for (RecipeProcessEntity p : processes) {
                p.setOfRecipe(this);
            }
            this.processes = processes;
        }
    }


    // ---------- methods ----------


    public String toString() {
        StringBuilder s = new StringBuilder()
                .append(id)
                .append(" ")
                .append(name);
        for (RecipeProcessEntity p : processes) {
            if (p != null) {
                s.append("\n").append(p.toStringDetailed());
            }
        }
        return s.toString();
    }

    public boolean similar(RecipeEntity other) {
        List<RecipeProcessEntity> otherProcesses = other.getProcesses();
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
        return this.name == other.getName() && processesSimilarity;
    }

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
        return this.name == other.getName() && processesSimilarity;
    }


    //-------------------getters & setters ----------------------------


    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RecipeProcessEntity> getProcesses() {
        return processes;
    }

    public void setProcesses(List<RecipeProcessEntity> processes) {
        if (processes != null) {
            for (RecipeProcessEntity p : processes) {
                p.setOfRecipe(this);
            }
        }
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
