package com.erezshevach.recipebookmaster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipeBookMasterApplication {

    private static final Logger log = LoggerFactory.getLogger(RecipeBookMasterApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(RecipeBookMasterApplication.class, args);

//        String[] ings = new String[]{"flour", "butter", "sugar", "chocolate", "eggs", "milk"};
//        String[] states = new String[]{"cold", "cold cubes", null, "melted", null, null};
//        double[] qtys = new double[]{300, 200, 100, 150, 50, 80};
//        Uom[] uoms = new Uom[]{Uom.G, Uom.G, Uom.G, Uom.G, Uom.G, Uom.G};
//        int[] seqs = new int[]{1,1,1,2,4,4};
//        String[] processes = new String[]{"mix", "incorporate", "cool down", "fold in", "bake", "cool on a rack"};
//        RecipeEntity r1 = RecipeEntity.createRecipe("chocolate buns", processes, ings, states, qtys, uoms, seqs);
//        System.out.println(r1);

    }



}
