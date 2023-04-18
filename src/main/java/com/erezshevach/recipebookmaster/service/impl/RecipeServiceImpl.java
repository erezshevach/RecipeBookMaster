package com.erezshevach.recipebookmaster.service.impl;

import com.erezshevach.recipebookmaster.RecipeRepository;
import com.erezshevach.recipebookmaster.io.entity.RecipeEntity;
import com.erezshevach.recipebookmaster.io.entity.RecipeComponentEntity;
import com.erezshevach.recipebookmaster.io.entity.RecipeProcessEntity;
import com.erezshevach.recipebookmaster.Uom;
import com.erezshevach.recipebookmaster.recipebookmaster.exceptions.RecipeException;
import com.erezshevach.recipebookmaster.service.RecipeService;
import com.erezshevach.recipebookmaster.shared.dto.RecipeDto;
import com.erezshevach.recipebookmaster.ui.model.response.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public RecipeDto createRecipe(RecipeDto recipeDtoIn) {
        transformInputToProcessesAndComponents(recipeDtoIn);
        RecipeEntity recipeEntity = new RecipeEntity(recipeDtoIn.getName(), recipeDtoIn.getProcesses());

        RecipeEntity storedRecipeEntity = recipeRepository.save(recipeEntity);
        RecipeDto recipeDtoOut = new RecipeDto();
        BeanUtils.copyProperties(storedRecipeEntity, recipeDtoOut);
        transformProcessesAndComponentsToOutput(recipeDtoOut);

        return recipeDtoOut;
    }
    @Override
    public RecipeDto getRecipeByName(String name) {
        RecipeDto recipeDto = new RecipeDto();
        RecipeEntity recipeEntity = recipeRepository.findRecipeByName(name);
        if (recipeEntity == null) throw new RecipeException(name, ErrorMessages.NO_RECORD_FOUND.getMessage());
        BeanUtils.copyProperties(recipeEntity, recipeDto);
        transformProcessesAndComponentsToOutput(recipeDto);
        return recipeDto;
    }

    @Override
    public List<RecipeDto> getRecipesByPartialName(String partialName, int page, int limit) {
        List<RecipeDto> result = new ArrayList<>();
        if (page > 0) page -=1;
        Pageable pageable = PageRequest.of(page, limit);
        List<RecipeEntity> recipes = recipeRepository.findRecipesByNameContains(pageable, partialName);
        for (RecipeEntity recipe : recipes) {
            RecipeDto recipeDto = new RecipeDto();
            BeanUtils.copyProperties(recipe, recipeDto);
            transformProcessesAndComponentsToOutput(recipeDto);
            result.add(recipeDto);
        }
        return result;
    }

    @Override
    public void deleteRecipe(String name) {
        RecipeEntity recipe = recipeRepository.findRecipeByName(name);
        if (recipe == null)
            throw new RecipeException(name, ErrorMessages.NO_RECORD_FOUND.getMessage());
        recipeRepository.delete(recipe);
    }

    private void transformInputToProcessesAndComponents(RecipeDto recipeDto) {
        String[] processes_input = recipeDto.getProcesses_input();
        String[] ingredients = recipeDto.getIngredients();
        double[] quantities = recipeDto.getQuantities();
        String[] units = recipeDto.getUnits();
        String[] states = recipeDto.getStates();
        int[] sequences = recipeDto.getSequences();

        RecipeProcessEntity[] processes = new RecipeProcessEntity[processes_input.length];
        for (int i = 0; i < processes_input.length; i++) {
            String description = processes_input[i] != null ? processes_input[i] : "";
            processes[i] = new RecipeProcessEntity(i + 1, description, null);
        }

        for (int i = 0; i < ingredients.length; i++) {
            if (sequences[i] > processes.length) {
                throw new RecipeException(recipeDto.getName(), ErrorMessages.INVALID_INPUT.getMessage() + ", component sequence for " + ingredients[i] + " does not have a matching process sequence.");
            }
            RecipeProcessEntity relatedProcess = processes[sequences[i] - 1];
            RecipeComponentEntity component = new RecipeComponentEntity(ingredients[i], states[i], quantities[i], Uom.getByLabel(units[i]), null, relatedProcess);
            List<RecipeComponentEntity> components = relatedProcess.getComponents();
            components.add(component);
            relatedProcess.setComponents(components);
        }
        recipeDto.setProcesses(Arrays.asList(processes));
    }
    private void transformProcessesAndComponentsToOutput(RecipeDto recipeDto){
        List<String> processes_output = new ArrayList<>();
        List<String> components_output = new ArrayList<>();

        for (RecipeProcessEntity p : recipeDto.getProcesses()) {
            if (p != null) {
                processes_output.add(p.toString());
                for(RecipeComponentEntity c : p.getComponents()) {
                    components_output.add("(" + p.getSequence() + ") " + c.toString());
                }
            }
        }
        recipeDto.setProcesses_output(processes_output.toArray(new String[0]));
        recipeDto.setComponents_output(components_output.toArray(new String[0]));
    }
}
