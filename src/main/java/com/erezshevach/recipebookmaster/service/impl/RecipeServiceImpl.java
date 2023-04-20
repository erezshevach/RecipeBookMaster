package com.erezshevach.recipebookmaster.service.impl;

import com.erezshevach.recipebookmaster.RecipeRepository;
import com.erezshevach.recipebookmaster.io.entity.RecipeEntity;
import com.erezshevach.recipebookmaster.recipebookmaster.exceptions.RecipeException;
import com.erezshevach.recipebookmaster.service.RecipeService;
import com.erezshevach.recipebookmaster.shared.Utils;
import com.erezshevach.recipebookmaster.shared.dto.RecipeComponentDto;
import com.erezshevach.recipebookmaster.shared.dto.RecipeDto;
import com.erezshevach.recipebookmaster.shared.dto.RecipeProcessDto;
import com.erezshevach.recipebookmaster.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    RecipeRepository recipeRepository;
    Utils utils;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, Utils utils) {
        this.recipeRepository = recipeRepository;
        this.utils = utils;
    }

    @Override
    public RecipeDto createRecipe(RecipeDto recipeDtoIn) {
        setStructuralAttributes_input(recipeDtoIn);
        ModelMapper mapper = new ModelMapper();
        RecipeEntity recipe = mapper.map(recipeDtoIn, RecipeEntity.class);

        if (recipeRepository.findRecipeByName(recipeDtoIn.getName()) != null)
            throw new RecipeException(recipe.getName(), ErrorMessages.RECORD_EXISTS.getMessage());
        RecipeEntity storedRecipeEntity = recipeRepository.save(recipe);

        RecipeDto recipeDtoOut = mapper.map(storedRecipeEntity, RecipeDto.class);
        setStructuralAttributes_output(recipeDtoOut);

        return recipeDtoOut;
    }

    @Override
    public RecipeDto getRecipeByName(String name) {
        RecipeEntity recipeEntity = recipeRepository.findRecipeByName(name);
        if (recipeEntity == null)
            throw new RecipeException(name, ErrorMessages.NO_RECORD_FOUND.getMessage());
        ModelMapper mapper = new ModelMapper();
        RecipeDto recipeDto = mapper.map(recipeEntity, RecipeDto.class);
        setStructuralAttributes_output(recipeDto);

        return recipeDto;
    }

    @Override
    public List<RecipeDto> getRecipesByPartialName(String partialName, int page, int limit) {
        List<RecipeDto> result = new ArrayList<>();
        if (page > 0) page -= 1;
        Pageable pageable = PageRequest.of(page, limit);
        List<RecipeEntity> recipes = recipeRepository.findRecipesByNameContains(pageable, partialName);
        ModelMapper mapper = new ModelMapper();
        for (RecipeEntity recipe : recipes) {
            RecipeDto recipeDto = mapper.map(recipe, RecipeDto.class);
            setStructuralAttributes_output(recipeDto);
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

    private void setStructuralAttributes_input(RecipeDto recipe) {
        for (RecipeProcessDto process : recipe.getProcesses()) {
            process.setOfRecipe(recipe);
            process.setProcessPid(utils.generateProcessId(10));
            for (RecipeComponentDto component : process.getComponents()) {
                component.setOfRecipe(recipe);
                component.setOfProcess(process);
                component.setComponentPid(utils.generateComponentId(10));
            }
        }
    }

    private void setStructuralAttributes_output(RecipeDto recipe) {
        for (RecipeProcessDto process : recipe.getProcesses()) {
            for (RecipeComponentDto component : process.getComponents()) {
                component.setRelatedProcessSequence(process.getSequence());
            }
        }
    }

//    private void transformInputToProcessesAndComponents(RecipeDto recipeDto) {
//        String[] processes_input = recipeDto.getProcesses_input();
//        String[] ingredients = recipeDto.getIngredients();
//        double[] quantities = recipeDto.getQuantities();
//        String[] units = recipeDto.getUnits();
//        String[] states = recipeDto.getStates();
//        int[] sequences = recipeDto.getSequences();
//
//        RecipeProcessEntity[] processes = new RecipeProcessEntity[processes_input.length];
//        for (int i = 0; i < processes_input.length; i++) {
//            String description = processes_input[i] != null ? processes_input[i] : "";
//            processes[i] = new RecipeProcessEntity(i + 1, description, null);
//        }
//
//        for (int i = 0; i < ingredients.length; i++) {
//            if (sequences[i] > processes.length) {
//                throw new RecipeException(recipeDto.getName(), ErrorMessages.INVALID_INPUT.getMessage() + ", component sequence for " + ingredients[i] + " does not have a matching process sequence.");
//            }
//            RecipeProcessEntity relatedProcess = processes[sequences[i] - 1];
//            RecipeComponentEntity component = new RecipeComponentEntity(ingredients[i], states[i], quantities[i], Uom.getByLabel(units[i]), null, relatedProcess);
//            List<RecipeComponentEntity> components = relatedProcess.getComponents();
//            components.add(component);
//            relatedProcess.setComponents(components);
//        }
//        recipeDto.setProcesses(Arrays.asList(processes));
//    }
//    private void transformProcessesAndComponentsToOutput(RecipeDto recipeDto){
//        List<String> processes_output = new ArrayList<>();
//        List<String> components_output = new ArrayList<>();
//
//        for (RecipeProcessEntity p : recipeDto.getProcesses()) {
//            if (p != null) {
//                processes_output.add(p.toString());
//                for(RecipeComponentEntity c : p.getComponents()) {
//                    components_output.add("(" + p.getSequence() + ") " + c.toString());
//                }
//            }
//        }
//        recipeDto.setProcesses_output(processes_output.toArray(new String[0]));
//        recipeDto.setComponents_output(components_output.toArray(new String[0]));
//    }
}
