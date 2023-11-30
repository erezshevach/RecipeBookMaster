package com.erezshevach.recipebookmaster.data.entity;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "recipes")
public class RecipeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 24L;

    @Id
    @GeneratedValue
    private Long id;
    private String recipePid;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "ofRecipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    public static boolean compareRecipes(RecipeEntity first, RecipeEntity second) {
        List<RecipeProcessEntity> processes_first = first.getProcesses();
        List<RecipeProcessEntity> processes_second = second.getProcesses();
        int nProcesses_first = processes_first != null ? processes_first.size() : -1;
        int nProcesses_second = processes_second != null ? processes_second.size() : -1;
        boolean matchingProcesses = nProcesses_first == nProcesses_second;
        if (matchingProcesses && nProcesses_first > 0) {
            for (int i = 0; i < nProcesses_first; i++) {
                if (!RecipeProcessEntity.compareProcesses(processes_first.get(i), processes_second.get(i))){
                    matchingProcesses = false;
                    break;
                }
            }

        }
        boolean matchingName = Objects.equals(first.getName(), second.getName());
        return matchingName && matchingProcesses;
    }

    /**
     * copyHeaderValuesFromTarget - copies only header attributes values (not processes and components), excluding IDs
     * @param target recipe entity to copy from
     */
    public void copyHeaderValuesFromTarget(RecipeEntity target) {
        if (target != null) {
            this.setName(target.getName());
            this.setContainsDairy(target.isContainsDairy());
            this.setContainsGluten(target.isContainsGluten());
            this.setContainsNuts(target.isContainsNuts());
            this.setContainsPeanuts(target.isContainsPeanuts());
            this.setVegan(target.isVegan());
            this.setkCalPer100g(target.getkCalPer100g());
        }
    }

    //-------------------getters & setters ----------------------------


    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

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
