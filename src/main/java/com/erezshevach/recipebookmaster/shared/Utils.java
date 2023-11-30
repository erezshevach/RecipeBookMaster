package com.erezshevach.recipebookmaster.shared;

import com.erezshevach.recipebookmaster.data.entity.RecipeComponentEntity;
import com.erezshevach.recipebookmaster.data.entity.RecipeEntity;
import com.erezshevach.recipebookmaster.data.entity.RecipeProcessEntity;
import org.modelmapper.ModelMapper;

import java.security.SecureRandom;
import java.util.Random;

public class Utils {
    private static final Random RANDOM = new SecureRandom();
    private static final ModelMapper mapper = new ModelMapper();


    public static String generateRecipePid(int length, String name) {
        String nameSample = name.replace(" ", "").substring(0,(Math.min(10, name.length()))).toLowerCase();
        return nameSample + "_" + generateRandomString(length);
    }

    public static String generateProcessPid(int length) {
        return "P" + generateRandomString(length);
    }

    public static String generateComponentPid(int length) {
        return "C" + generateRandomString(length);
    }

    private static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        String ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < length; i++) {
            sb.append((ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length()))));
        }
        return new String(sb);
    }

    public static boolean compareComponents(Object first, Object second) {
        return RecipeComponentEntity.compareComponents(mapper.map(first, RecipeComponentEntity.class), mapper.map(second, RecipeComponentEntity.class));
    }

    public static boolean compareProcesses(Object first, Object second) {
        return RecipeProcessEntity.compareProcesses(mapper.map(first, RecipeProcessEntity.class), mapper.map(second, RecipeProcessEntity.class));
    }

    public static boolean compareRecipes(Object first, Object second) {
        return RecipeEntity.compareRecipes(mapper.map(first, RecipeEntity.class), mapper.map(second, RecipeEntity.class));
    }

}
