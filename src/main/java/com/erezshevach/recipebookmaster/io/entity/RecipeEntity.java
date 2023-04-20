package com.erezshevach.recipebookmaster.io.entity;

import jakarta.persistence.*;

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

    public RecipeEntity(String name) {
        this.name = name;
    }

    public RecipeEntity(String name, List<RecipeProcessEntity> processes) {
        this.name = name;

        for (RecipeProcessEntity p : processes) {
            p.setOfRecipe(this);
        }

        this.processes = processes;

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

//    public static boolean addRecipe(RecipeEntity recipeEntity, EntityManager pContext) {
//        boolean success = false;
//        if (isRecipeNameExists(recipeEntity.getName(), pContext)) {
//            System.out.println("recipe " + recipeEntity.getName() + " already exsists");
//        } else {
//            DbOperations.insertObject(recipeEntity, pContext);
//            success = true;
//        }
//        return success;
//    }
//
//    public static Boolean isRecipeNameExists(String name, EntityManager pContext) throws IllegalArgumentException {
//        return !pContext.createQuery("select name from RecipeEntity where name = :n", String.class).setParameter("n", name).getResultList().isEmpty();
//    }
//
//    public static List<String> getRecipeNamesListByPartialName(String name, EntityManager pContext) throws IllegalArgumentException {
//        String validNames = "%" + name + "%";
//        return pContext.createQuery("select name from RecipeEntity where name like :n", String.class).setParameter("n", validNames).getResultList();
//    }
//
//    /**
//     * returns the first recipe with the matching name, or null, if none such recipe exists.
//     */
//    public static RecipeEntity getRecipeByName(String name, EntityManager pContext) throws IllegalArgumentException {
//        RecipeEntity recipeEntity = null;
//        List<RecipeEntity> results = pContext.createQuery("from RecipeEntity where name = :n", RecipeEntity.class).setParameter("n", name).getResultList();
//        if (!results.isEmpty()) {
//            recipeEntity = results.get(0);
//        }
//        return recipeEntity;
//    }


    //public static void getRecipeNamesListByAttribute() {}

    //public static void getRecipeNamesListByIngredient() {}

    //public static void filterRecipesListByAttribute() {}

    //    public void calculateContainsDairy(Predicate<Ingredient> getter) {
//        boolean res = false;
//        res = processes.stream()
//                .flatMap(process->process.getProcessComponents().stream())
//                .map(RecipeComponent::getIngredient)
//                .filter(e->e.getClass().equals(Ingredient.class))
//                .map(i->(Ingredient)i)
//                .anyMatch(getter);
//        this.containsDairy = res;
//    }

    //-------------------getters & setters ----------------------------
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
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
