import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.DbOperations;

import java.util.List;


public class RecipeBookMasterApp {

    public static void main(String[] args) {

        String[] ings = new String[]{"flour", "butter", "sugar"};
        String[] states = new String[]{"cold", "cold cubes", null};
        double[] qtys = new double[]{300, 200, 100};
        Uom[] uoms = new Uom[]{Uom.G, Uom.G, Uom.G};
        int[] seqs = new int[]{1,1,1};
        Recipe r1 = Recipe.createRecipe("ganach", List.of("mix"), ings, states, qtys, uoms, seqs);
//        Recipe r2 = Recipe.createRecipe("short crust coco", List.of("mix"), ings, states, qtys, uoms, seqs);
//        Recipe r3 = Recipe.createRecipe("cake", List.of("mix"), ings, states, qtys, uoms, seqs);
//        Recipe r4 = Recipe.createRecipe("cookies", List.of("mix"), ings, states, qtys, uoms, seqs);
//        //System.out.println(r1.toString());
//
        SessionFactory sf = DbOperations.getSessionFactory(List.of(Recipe.class, RecipeProcess.class, RecipeComponent.class));
        try (Session session = sf.openSession()) {
            Recipe.addRecipe(r1, session);
        } catch (HibernateException ex) {
            System.err.println("Failed opening session." + ex);
        }



    }
}
