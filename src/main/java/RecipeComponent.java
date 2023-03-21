import jakarta.persistence.*;

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
    private int processSeq;
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe ofRecipe;

    // ---------- constructors ----------
    public RecipeComponent() {
    }

    public RecipeComponent(String ingredient, String state, double quantity, Uom uom, int processSeq, Recipe recipe) {
        this.ingredient = ingredient;
        this.state = state;
        this.quantity = quantity;
        this.uom = uom;
        this.processSeq = processSeq;
        this.ofRecipe = recipe;
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

    public int getProcessSeq() {
        return processSeq;
    }

    public void setProcessSeq(int processSeq) {
        this.processSeq = processSeq;
    }

    public Recipe getOfRecipe() {
        return ofRecipe;
    }

    public void setOfRecipe(Recipe recipe) {
        this.ofRecipe = recipe;
    }

    public String toString() {
        return quantity + " " + uom + " " + ingredient + " " + state + "(" + processSeq + ")";
    }
}
