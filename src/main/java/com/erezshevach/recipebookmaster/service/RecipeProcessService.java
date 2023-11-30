package com.erezshevach.recipebookmaster.service;


import com.erezshevach.recipebookmaster.data.entity.RecipeEntity;

public interface RecipeProcessService {

    void deleteProcessByPid(String pid);

    void deleteProcessesByOfRecipe(RecipeEntity recipe);
}
