import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import util.DbOperations;

import java.util.List;


public class RecipeBookMasterApp {

    public static void main(String[] args) {

        String[] ings = new String[]{"flour", "butter", "sugar", "chocolate", "eggs", "milk"};
        String[] states = new String[]{"cold", "cold cubes", null, "melted", null, null};
        double[] qtys = new double[]{300, 200, 100, 150, 50, 80};
        Uom[] uoms = new Uom[]{Uom.G, Uom.G, Uom.G, Uom.G, Uom.G, Uom.G};
        int[] seqs = new int[]{1,1,1,2,4,4};
        String[] processes = new String[]{"mix", "incorporate", "cool down", "fold in", "bake", "cool on a rack"};
        Recipe r1 = Recipe.createRecipe("chocolate cake", processes, ings, states, qtys, uoms, seqs);
//        System.out.println(r1);
//        Recipe r2 = Recipe.createRecipe("brownies", new String[]{"mix"}, ings, states, qtys, uoms, seqs);
//        Recipe r3 = Recipe.createRecipe("fudge", new String[]{"bake"}, ings, states, qtys, uoms, seqs);
//        Recipe r4 = Recipe.createRecipe("chocolate mouse", new String[]{"combine"}, ings, states, qtys, uoms, seqs);

        EntityManagerFactory factory = DbOperations.getEntityManagerFactory();
        EntityManager pContext = factory.createEntityManager();
        Recipe.addRecipe(r1, pContext);
//        Recipe.addRecipe(r2, pContext);
//        Recipe.addRecipe(r3, pContext);
//        Recipe.addRecipe(r4, pContext);
        pContext.close();
        factory.close();
    }
}
