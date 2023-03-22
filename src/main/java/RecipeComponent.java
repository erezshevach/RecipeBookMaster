import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

@Entity
public class RecipeComponent {

    @Id
    @GeneratedValue//(strategy = GenerationType.IDENTITY)
    private long id;
    //@ManyToOne
    //private Ingredient ingredient;
    private String ingredient;
    //@ManyToOne
    //private IngredientState state;
    private String state;
    private double quantity;
    private Uom uom;
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe ofRecipe;
    @ManyToOne
    @JoinColumn(name = "recipeprocess_id")
    private RecipeProcess ofProcess;

    // ---------- constructors ----------
    public RecipeComponent() {
    }

    public RecipeComponent(@NotNull String ingredient, String state, double quantity, @NotNull Uom uom, @NotNull Recipe recipe, @NotNull RecipeProcess process) {
        this.ingredient = ingredient;
        this.state = state;
        this.quantity = quantity;
        this.uom = uom;
        this.ofRecipe = recipe;
        this.ofProcess = process;
    }

    // ---------- methods ----------
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(quantity).append(" ").append(uom).append(" ").append(ingredient);
        if (state != null) {
            s.append(" ").append(state);
        }
        return s.toString();
    }

    // ---------- getters/setters ----------
    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Uom getUom() {
        return uom;
    }

    public void setUom(Uom uom) {
        this.uom = uom;
    }

    public Recipe getOfRecipe() {
        return ofRecipe;
    }

    public void setOfRecipe(Recipe recipe) {
        this.ofRecipe = recipe;
    }

    public RecipeProcess getOfProcess() {
        return ofProcess;
    }

    public void setOfProcess(RecipeProcess ofProcess) {
        this.ofProcess = ofProcess;
    }
}
