import jakarta.persistence.*;


@Entity
public class RecipeProcess {

    @Id
    @GeneratedValue//(strategy = GenerationType.IDENTITY)
    private long id;
    private int sequence;
    private String description;
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe ofRecipe;

    // ---------- constructors ----------
    public RecipeProcess() {
    }

    public RecipeProcess(int sequence, String description, Recipe recipe) {
        this.sequence = sequence;
        this.description = description;
        this.ofRecipe = recipe;
    }

    // ---------- getters/setters ----------
    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

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

    public Recipe getOfRecipe() {
        return ofRecipe;
    }

    public void setOfRecipe(Recipe recipe) {
        this.ofRecipe = recipe;
    }

    public String toString() {
        return sequence + ": " + description;
    }
}
