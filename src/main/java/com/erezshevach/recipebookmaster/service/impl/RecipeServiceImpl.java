package com.erezshevach.recipebookmaster.service.impl;

import com.erezshevach.recipebookmaster.RecipeRepository;
import com.erezshevach.recipebookmaster.io.entity.RecipeEntity;
import com.erezshevach.recipebookmaster.recipebookmaster.exceptions.RecipeException;
import com.erezshevach.recipebookmaster.service.RecipeService;
import com.erezshevach.recipebookmaster.shared.Utils;
import com.erezshevach.recipebookmaster.shared.dto.*;
import com.erezshevach.recipebookmaster.ui.model.response.ErrorMessages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    public RecipeDto createRecipe(@NotNull RecipeDto recipeDtoIn) {
        String name = recipeDtoIn.getName();
        if (recipeDtoIn.getProcesses() == null || recipeDtoIn.getProcesses().isEmpty())
            throw new RecipeException(name, ErrorMessages.MISSING_REQUIRED_FIELD.getMessage() + ": processes");
        if (name == null || name.isEmpty() || name.isBlank())
            throw new RecipeException(name, ErrorMessages.MISSING_REQUIRED_FIELD.getMessage() + ": name");
        setGeneratedPublicIds(recipeDtoIn);
        ModelMapper mapper = new ModelMapper();
        RecipeEntity recipe = mapper.map(recipeDtoIn, RecipeEntity.class);

        if (recipeRepository.findRecipeByName(name) != null)
            throw new RecipeException(name, ErrorMessages.RECORD_EXISTS.getMessage());
        RecipeEntity storedRecipeEntity = recipeRepository.save(recipe);

        RecipeDto recipeDtoOut = mapper.map(storedRecipeEntity, RecipeDto.class);
        setRelatedProcessSequenceForPresentation(recipeDtoOut);

        return recipeDtoOut;
    }

    @Override
    public RecipeDto getRecipeByName(String name) {
        name = validateName(name);
        RecipeEntity recipeEntity = recipeRepository.findRecipeByName(name);
        if (recipeEntity == null)
            throw new RecipeException(name, ErrorMessages.NO_RECORD_FOUND.getMessage());
        ModelMapper mapper = new ModelMapper();
        RecipeDto recipeDto = mapper.map(recipeEntity, RecipeDto.class);
        setRelatedProcessSequenceForPresentation(recipeDto);

        return recipeDto;
    }

    @Override
    public List<RecipeDto> getRecipesByPartialName(String partialName, int page, int limit) {
        if (page < 0 || limit < 1) throw new IllegalArgumentException("page/limit must be positive");
        partialName = partialName.trim();
        List<RecipeDto> result = new ArrayList<>();
        if (page > 0) page -= 1;
        Pageable pageable = PageRequest.of(page, limit);
        List<RecipeEntity> recipes = recipeRepository.findRecipesByNameContains(pageable, partialName);
        ModelMapper mapper = new ModelMapper();
        for (RecipeEntity recipe : recipes) {
            RecipeDto recipeDto = mapper.map(recipe, RecipeDto.class);
            setRelatedProcessSequenceForPresentation(recipeDto);
            result.add(recipeDto);
        }
        return result;
    }

    @Override
    public void deleteRecipe(String name) {
        name = validateName(name);
        RecipeEntity recipe = recipeRepository.findRecipeByName(name);
        if (recipe == null)
            throw new RecipeException(name, ErrorMessages.NO_RECORD_FOUND.getMessage());
        recipeRepository.delete(recipe);
    }

    private void setGeneratedPublicIds(RecipeDto recipe) {
        if (recipe.getProcesses() != null) {
            for (RecipeProcessDto process : recipe.getProcesses()) {
                process.setProcessPid(utils.generateProcessId(10));
                if (process.getComponents() != null) {
                    for (RecipeComponentDto component : process.getComponents()) {
                        component.setComponentPid(utils.generateComponentId(10));
                    }
                }
            }
        }
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

    private void setRelatedProcessSequenceForPresentation(RecipeDto recipe) {
        for (RecipeProcessDto process : recipe.getProcesses()) {
            for (RecipeComponentDto component : process.getComponents()) {
                component.setRelatedProcessSequence(process.getSequence());
            }
        }
    }

    private String validateName(String name) {
        if (name == null || name.isEmpty() || name.isBlank())
            throw new IllegalArgumentException("valid name must be provided");
        return name.trim();
    }
}
