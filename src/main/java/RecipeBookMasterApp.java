import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import util.DbOperations;

import java.util.List;


public class RecipeBookMasterApp {

    public static void main(String[] args) {

        String[] ings = new String[]{"flour", "butter", "sugar"};
        String[] states = new String[]{"cold", "cold cubes", null};
        double[] qtys = new double[]{300, 200, 100};
        Uom[] uoms = new Uom[]{Uom.G, Uom.G, Uom.G};
        int[] seqs = new int[]{1,1,1};
        Recipe r1 = Recipe.createRecipe("short crust", List.of("mix"), ings, states, qtys, uoms, seqs);
        Recipe r2 = Recipe.createRecipe("short crust coco", List.of("mix"), ings, states, qtys, uoms, seqs);
        Recipe r3 = Recipe.createRecipe("cake", List.of("bake"), ings, states, qtys, uoms, seqs);
        Recipe r4 = Recipe.createRecipe("cookies", List.of("combine"), ings, states, qtys, uoms, seqs);

        EntityManagerFactory factory = DbOperations.getEntityManagerFactory();
        EntityManager pContext = factory.createEntityManager();
        Recipe.addRecipe(r1, pContext);
        Recipe.addRecipe(r2, pContext);
        Recipe.addRecipe(r3, pContext);
        Recipe.addRecipe(r4, pContext);
        pContext.close();
        factory.close();
    }
}
