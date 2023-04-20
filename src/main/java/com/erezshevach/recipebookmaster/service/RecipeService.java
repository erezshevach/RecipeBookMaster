package com.erezshevach.recipebookmaster.service;

import com.erezshevach.recipebookmaster.shared.dto.RecipeDto;

import java.util.List;

public interface RecipeService {

    RecipeDto createRecipe(RecipeDto recipeDtoIn);

    RecipeDto getRecipeByName(String name);

    List<RecipeDto> getRecipesByPartialName(String partialName, int page, int limit);

    void deleteRecipe(String name);
}
