package com.erezshevach.recipebookmaster.service;

import com.erezshevach.recipebookmaster.shared.dto.RecipeDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecipeService {

    @Transactional
    RecipeDto createRecipe(RecipeDto recipeDtoIn);

    RecipeDto getRecipeByName(String name);

    RecipeDto getRecipeByPid(String pid);

    List<RecipeDto> getRecipeHeadersByPartialName(String partialName, int page, int limit);

    @Transactional
    RecipeDto updateRecipeByPid(String pid, RecipeDto recipeDtoIn);

    @Transactional
    RecipeDto deleteRecipeByName(String name);

    @Transactional
    RecipeDto deleteRecipeByPid(String pid);
}
