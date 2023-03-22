import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static Recipe createRecipe(String name, String[] input_processes, String[] ingredients, String[] states, double[] qtys, Uom[] uoms, int[] seqs) {
        Recipe recipe = new Recipe(name)
                .updateProcessesAndComponents(input_processes, ingredients, states, qtys, uoms, seqs);

        return recipe;
    }

    // ---------- methods ----------

    public String toString() {
        StringBuilder s = new StringBuilder()
                .append(id)
                .append(" ")
                .append(name);
        for (RecipeProcess p : processes) {
            if (p != null) {
                s.append("\n").append(p.toString());
            }
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

    /**
     * returns the first recipe with the matching name, or null, if none such recipe exists.
     */
    public static Recipe getRecipeByName(String name, EntityManager pContext) throws IllegalArgumentException {
        Recipe recipe = null;
        List<Recipe> results = pContext.createQuery("from Recipe where name = :n", Recipe.class).setParameter("n", name).getResultList();
        if (!results.isEmpty()) { recipe = results.get(0); }
        return recipe;
    }

    private Recipe updateProcessesAndComponents(String[] input_processes, String[] ingredients, String[] states, double[] qtys, Uom[] uoms, int[] seqs) {
        RecipeProcess[] processes = new RecipeProcess[input_processes.length];
        for (int i = 0; i < input_processes.length; i++){
            String description = input_processes[i] != null ? input_processes[i] : "";
            processes[i] = new RecipeProcess(i + 1, description , this);
        }

        for (int i = 0; i < ingredients.length; i++){
            if (seqs[i] > processes.length) {
                //alert
            }
            RecipeProcess relatedProcess = processes[seqs[i] - 1];
            RecipeComponent component = new RecipeComponent(ingredients[i], states[i], qtys[i], uoms[i], this, relatedProcess);
            List<RecipeComponent> components = relatedProcess.getComponents();
            components.add(component);
            relatedProcess.setComponents(components);
        }
        this.processes = Arrays.asList(processes);
        return this;
    }

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
