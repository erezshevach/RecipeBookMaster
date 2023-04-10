package com.erezshevach.recipebookmaster.service.impl;

import com.erezshevach.recipebookmaster.RecipeRepository;
import com.erezshevach.recipebookmaster.io.entity.RecipeEntity;
import com.erezshevach.recipebookmaster.io.entity.RecipeComponentEntity;
import com.erezshevach.recipebookmaster.io.entity.RecipeProcessEntity;
import com.erezshevach.recipebookmaster.Uom;
import com.erezshevach.recipebookmaster.service.RecipeService;
import com.erezshevach.recipebookmaster.shared.dto.RecipeDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    RecipeRepository recipeRepository;

    public RecipeDto createRecipe(RecipeDto recipeDtoIn) {
        RecipeDto recipeDtoOut = new RecipeDto();

            RecipeEntity recipeEntity = new RecipeEntity(recipeDtoIn.getName());
            recipeEntity = updateProcessesAndComponents(recipeEntity, recipeDtoIn.getProcesses(), recipeDtoIn.getIngredients(), recipeDtoIn.getStates(), recipeDtoIn.getQuantities(), recipeDtoIn.getUnits(), recipeDtoIn.getSequences());

            RecipeEntity storedRecipeEntity =  recipeRepository.save(recipeEntity);
            BeanUtils.copyProperties(storedRecipeEntity, recipeDtoOut);

        return recipeDtoOut;
    }
    private static RecipeEntity updateProcessesAndComponents(RecipeEntity recipeEntity, String[] input_processes, String[] ingredients, String[] states, double[] qtys, String[] uoms, int[] seqs) {
        RecipeProcessEntity[] processes = new RecipeProcessEntity[input_processes.length];
        for (int i = 0; i < input_processes.length; i++){
            String description = input_processes[i] != null ? input_processes[i] : "";
            processes[i] = new RecipeProcessEntity(i + 1, description , recipeEntity);
        }

        for (int i = 0; i < ingredients.length; i++){
            if (seqs[i] > processes.length) {
                //alert
            }
            RecipeProcessEntity relatedProcess = processes[seqs[i] - 1];
            RecipeComponentEntity component = new RecipeComponentEntity(ingredients[i], states[i], qtys[i], Uom.getByLabel(uoms[i]), recipeEntity, relatedProcess);
            List<RecipeComponentEntity> components = relatedProcess.getComponents();
            components.add(component);
            relatedProcess.setComponents(components);
        }
        recipeEntity.setProcesses(Arrays.asList(processes));
        return recipeEntity;
    }
}
