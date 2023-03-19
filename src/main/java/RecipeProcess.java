import jakarta.persistence.*;


@Entity
public class RecipeProcess {

    @Id
    @GeneratedValue
    private long id;
    private int sequence;
    private String description;
    @ManyToOne
    private Recipe recipe;

    // ---------- constructors ----------
    public RecipeProcess() {
    }

    public RecipeProcess(int sequence, String description, Recipe recipe) {
        this.sequence = sequence;
        this.description = description;
        this.recipe = recipe;
    }

    // ---------- getters/setters ----------
    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String toString() {
        return sequence + ": " + description;
    }
}
