import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import util.DbOperations;

@Entity
public class Recipe {

    @Id
    @GeneratedValue//(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToMany(mappedBy = "ofRecipe", cascade = CascadeType.ALL)
    private List<RecipeProcess> processes = new ArrayList<>();
    @OneToMany(mappedBy = "ofRecipe", cascade = CascadeType.ALL)
    private List<RecipeComponent> components = new ArrayList<>();
    //@ManyToMany
    //private List<RecipeTag> tags;
    private Integer kCalPer100g = 0;
    private boolean containsGluten = false;
    private boolean containsDairy = false;
    private boolean containsNuts = false;
    private boolean containsPeanuts = false;
    private boolean vegan = false;

    // ---------- constructors ----------
    public Recipe() {}

    public Recipe(String name) {
        this.name = name;
    }

    public static Recipe createRecipe(String name, List<String> input_processes, String[] ingredients, String[] states, double[] qtys, Uom[] uoms, int[] seqs) {
        Recipe recipe = new Recipe(name);

        List<RecipeProcess> processes = new ArrayList<>();
        int seq = 0;
        for (String s : input_processes){
            processes.add(new RecipeProcess(++seq, s, recipe));
        }
        recipe.setProcesses(processes);

        List<RecipeComponent> components = new ArrayList<>();
        for (int i = 0; i<ingredients.length; i++) {
            if (seqs[i] > processes.size()) {
                //alert
            }
            components.add(new RecipeComponent(ingredients[i], states[i], qtys[i], uoms[i], seqs[i], recipe));
        }
        recipe.setComponents(components);

        return recipe;
    }

    // ---------- methods ----------

    public String toString() {
        StringBuilder s = new StringBuilder()
                .append(id)
                .append(" ")
                .append(name)
                .append(":\n");
        for (RecipeComponent c : components) {
            s.append(c.toString()).append("\n");
        }
        for (RecipeProcess p : processes) {
            s.append(p.toString()).append("\n");
        }
        return s.toString();

    }

    public static boolean addRecipe(Recipe recipe, EntityManager pContext) {
        boolean success = false;
        if (isRecipeNameExists(recipe.getName(), pContext)) {
            System.out.println("recipe " + recipe.getName() + " already exsists");
        } else {
            DbOperations.insertObject(recipe, pContext);
            success = true;
        }
        return success;
    }

    public static Boolean isRecipeNameExists(String name, EntityManager pContext) throws IllegalArgumentException {
        return !pContext.createQuery("select name from Recipe where name = :n", String.class).setParameter("n", name).getResultList().isEmpty();
    }

    public static List<String> getRecipeNamesListByPartialName(String name, EntityManager pContext) throws IllegalArgumentException {
        String validNames = "%"+name+"%";
        return pContext.createQuery("select name from Recipe where name like :n", String.class).setParameter("n", validNames).getResultList();
    }

//    public static Recipe getRecipeByName(String name, EntityManager pContext) throws IllegalArgumentException {
//        pContext.createQuery("from Recipe where name = :n", Recipe.class).setParameter("n", name).uniqueResult();
//        return recipe;
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

    // ---------- getters/setters ----------
    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public List<RecipeProcess> getProcesses() {
        return processes;
    }

    public void setProcesses(List<RecipeProcess> processes) {
        this.processes = processes;
    }

    public List<RecipeComponent> getComponents() {
        return components;
    }

    public void setComponents(List<RecipeComponent> components) {
        this.components = components;
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