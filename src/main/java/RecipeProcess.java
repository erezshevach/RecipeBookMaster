import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


@Entity
public class RecipeProcess {

    @Id
    @GeneratedValue
    private long id;
    private int sequence;
    private String description;
    @OneToMany(mappedBy = "ofProcess", cascade = CascadeType.ALL)
    private List<RecipeComponent> components = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe ofRecipe;

    // ---------- constructors ----------
    public RecipeProcess() {
    }

    public RecipeProcess(int sequence, @NotNull String description, @NotNull Recipe recipe) {
        this.sequence = sequence;
        this.description = description;
        this.ofRecipe = recipe;
    }
    public RecipeProcess(int sequence, @NotNull String description, List<RecipeComponent> components, @NotNull Recipe recipe) {
        this(sequence, description, recipe);
        this.components = components;
    }

    // ---------- methods ----------
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(sequence)
                .append(": ")
                .append(description);
        for (RecipeComponent c : components) {
            if (c != null) {
                s.append("\n").append(c.toString());
            }
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

    public List<RecipeComponent> getComponents() {
        return components;
    }

    public void setComponents(List<RecipeComponent> components) {
        this.components = components;
    }

    public Recipe getOfRecipe() {
        return ofRecipe;
    }

    public void setOfRecipe(Recipe recipe) {
        this.ofRecipe = recipe;
    }
}
