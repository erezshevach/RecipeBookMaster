package com.erezshevach.recipebookmaster.service;

import com.erezshevach.recipebookmaster.shared.dto.RecipeDto;

public interface RecipeService {

    RecipeDto createRecipe(RecipeDto recipeDtoIn);
    RecipeDto getRecipeByName(String name);
    void deleteRecipe(String name);
}
