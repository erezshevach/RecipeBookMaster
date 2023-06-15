package com.erezshevach.recipebookmaster.service.impl;

import com.erezshevach.recipebookmaster.RecipeProcessRepository;
import com.erezshevach.recipebookmaster.RecipeRepository;
import com.erezshevach.recipebookmaster.io.entity.RecipeEntity;
import com.erezshevach.recipebookmaster.recipebookmaster.exceptions.RecipeException;
import com.erezshevach.recipebookmaster.service.RecipeProcessService;
import com.erezshevach.recipebookmaster.service.RecipeService;
import com.erezshevach.recipebookmaster.shared.Utils;
import com.erezshevach.recipebookmaster.shared.dto.*;
import com.erezshevach.recipebookmaster.ui.model.response.ErrorMessages;

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
    RecipeProcessRepository processRepository;
    Utils utils;
    RecipeProcessService processService;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, Utils utils, RecipeProcessService processService, RecipeProcessRepository processRepository) {
        this.recipeRepository = recipeRepository;
        this.processRepository = processRepository;
        this.utils = utils;
        this.processService = processService;
    }

    private int pidLength = 10;


    @Override
    public RecipeDto createRecipe(@NotNull RecipeDto recipeDtoIn) {
        validateRequiredFields(recipeDtoIn);
        setGeneratedPublicIds(recipeDtoIn);
        ModelMapper mapper = new ModelMapper();
        RecipeEntity recipe = mapper.map(recipeDtoIn, RecipeEntity.class);

        if (recipeRepository.findByNameIgnoreCase(recipeDtoIn.getName()) != null)
            throw new RecipeException(recipeDtoIn.getName(), RecipeEntity.class.getName(), ErrorMessages.RECORD_EXISTS.getMessage());
        RecipeEntity storedRecipeEntity = recipeRepository.save(recipe);

        RecipeDto recipeDtoOut = mapper.map(storedRecipeEntity, RecipeDto.class);
        setRelatedProcessSequenceForPresentation(recipeDtoOut);

        return recipeDtoOut;
    }

    @Override
    public RecipeDto getRecipeByName(String name) {
        RecipeEntity recipeEntity = getRecipeEntityByName(name);
        ModelMapper mapper = new ModelMapper();
        RecipeDto recipeDto = mapper.map(recipeEntity, RecipeDto.class);
        setRelatedProcessSequenceForPresentation(recipeDto);

        return recipeDto;
    }

    @Override
    public RecipeDto getRecipeByPid(String pid) {
        RecipeEntity recipeEntity = getRecipeEntityByPid(pid);

        ModelMapper mapper = new ModelMapper();
        RecipeDto recipeDto = mapper.map(recipeEntity, RecipeDto.class);
        setRelatedProcessSequenceForPresentation(recipeDto);

        return recipeDto;
    }

//    @Override
//    public List<RecipeDto> getRecipesByPartialName(String partialName, int page, int limit) {
//        if (page < 0 || limit < 1) throw new IllegalArgumentException("page/limit must be positive");
//        partialName = partialName.trim();
//        List<RecipeDto> result = new ArrayList<>();
//        if (page > 0) page -= 1;
//        Pageable pageable = PageRequest.of(page, limit);
//        List<RecipeEntity> recipes = recipeRepository.findByNameContainsOrderByName(pageable, partialName);
//        ModelMapper mapper = new ModelMapper();
//        for (RecipeEntity recipe : recipes) {
//            RecipeDto recipeDto = mapper.map(recipe, RecipeDto.class);
//            setRelatedProcessSequenceForPresentation(recipeDto);
//            result.add(recipeDto);
//        }
//        return result;
//    }

    @Override
    public List<RecipeDto> getRecipeHeadersByPartialName(String partialName, int page, int limit) {
        if (page < 0 || limit < 1) throw new IllegalArgumentException("page/limit must be positive");
        partialName = partialName.trim();
        List<RecipeDto> result = new ArrayList<>();
        if (page > 0) page -= 1;
        Pageable pageable = PageRequest.of(page, limit);
        List<RecipeEntity> recipes = recipeRepository.findByNameContainsOrderByName(pageable, partialName);
        ModelMapper mapper = new ModelMapper();
        for (RecipeEntity recipe : recipes) {
            RecipeDto recipeDto = new RecipeDto();
            mapOnlyHeaderValues(recipeDto, recipe);
            result.add(recipeDto);
        }
        return result;
    }

    @Override
    public RecipeDto updateRecipeByPid(String pid, RecipeDto recipeDtoIn) {
        validateRequiredFields(recipeDtoIn);
        RecipeEntity existingRecipe = getRecipeEntityByPid(pid);
        setGeneratedPublicIds(recipeDtoIn);
        ModelMapper mapper = new ModelMapper();
        RecipeEntity updatedRecipe = mapper.map(recipeDtoIn, RecipeEntity.class);

        String updatedName = recipeDtoIn.getName();
        if (!updatedName.equals(existingRecipe.getName())) {
            if (!updatedName.equalsIgnoreCase(existingRecipe.getName())) {
                if (recipeRepository.findByNameIgnoreCase(updatedName) != null)
                    throw new RecipeException(updatedName, RecipeEntity.class.getName(), ErrorMessages.RECORD_EXISTS.getMessage());
            }
            existingRecipe.setName(updatedName);
        }

        processService.deleteProcessesByOfRecipe(existingRecipe);
        existingRecipe.setProcesses(updatedRecipe.getProcesses());

        RecipeEntity storedRecipe = recipeRepository.save(existingRecipe);
        RecipeDto recipeDtoOut = mapper.map(storedRecipe, RecipeDto.class);
        setRelatedProcessSequenceForPresentation(recipeDtoOut);

        return recipeDtoOut;
    }

    @Override
    public void deleteRecipeByName(String name) {
        RecipeEntity recipeEntity = getRecipeEntityByName(name);
        recipeRepository.delete(recipeEntity);
    }

    @Override
    public void deleteRecipeByPid(String pid) {
        RecipeEntity recipeEntity = getRecipeEntityByPid(pid);
        recipeRepository.delete(recipeEntity);
    }




    public int getPidLength() {
        return pidLength;
    }

    public void setPidLength(int pidLength) {
        this.pidLength = pidLength;
    }

    private String validateName(String name) {
        if (name == null || name.isEmpty() || name.isBlank())
            throw new IllegalArgumentException("valid name must be provided");
        return name.trim();
    }

    private String validatePid(String pid) {
        if (pid == null || pid.isEmpty() || pid.isBlank())
            throw new IllegalArgumentException("valid pID must be provided");
        return pid.trim();
    }

    private RecipeDto validateRequiredFields(RecipeDto recipeDto) {
        String name = recipeDto.getName();
        if (name == null || name.isEmpty() || name.isBlank())
            throw new RecipeException(name, RecipeEntity.class.getName(), ErrorMessages.MISSING_REQUIRED_FIELD.getMessage() + ": name");
        if (recipeDto.getProcesses() == null || recipeDto.getProcesses().isEmpty())
            throw new RecipeException(name, RecipeEntity.class.getName(), ErrorMessages.MISSING_REQUIRED_FIELD.getMessage() + ": processes");
        recipeDto.setName(name.trim());
        return recipeDto;
    }

    private RecipeEntity getRecipeEntityByPid(String pid) {
        pid = validatePid(pid);
        RecipeEntity recipeEntity = recipeRepository.findByRecipePid(pid);
        if (recipeEntity == null)
            throw new RecipeException(pid, RecipeEntity.class.getName(), ErrorMessages.NO_RECORD_FOUND.getMessage());

        return recipeEntity;
    }

    private RecipeEntity getRecipeEntityByName(String name) {
        name = validateName(name);
        RecipeEntity recipeEntity = recipeRepository.findByNameIgnoreCase(name);
        if (recipeEntity == null)
            throw new RecipeException(name, RecipeEntity.class.getName(), ErrorMessages.NO_RECORD_FOUND.getMessage());
        return recipeEntity;
    }

    private void setGeneratedPublicIds(RecipeDto recipe) {
        recipe.setRecipePid(utils.generateRecipePid(pidLength, recipe.getName()));
        if (recipe.getProcesses() != null) {
            for (RecipeProcessDto process : recipe.getProcesses()) {
                process.setProcessPid(utils.generateProcessPid(pidLength));
                if (process.getComponents() != null) {
                    for (RecipeComponentDto component : process.getComponents()) {
                        component.setComponentPid(utils.generateComponentPid(pidLength));
                    }
                }
            }
        }
    }

    private void setStructuralAttributes_input(RecipeDto recipe) {
        for (RecipeProcessDto process : recipe.getProcesses()) {
            process.setOfRecipe(recipe);
            process.setProcessPid(utils.generateProcessPid(10));
            for (RecipeComponentDto component : process.getComponents()) {
                component.setOfRecipe(recipe);
                component.setOfProcess(process);
                component.setComponentPid(utils.generateComponentPid(10));
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

    private void mapOnlyHeaderValues(RecipeDto dto, RecipeEntity entity) {
        dto.setRecipePid(entity.getRecipePid());
        dto.setName(entity.getName());
        dto.setkCalPer100g(entity.getkCalPer100g());
        dto.setContainsGluten(entity.isContainsGluten());
        dto.setContainsDairy(entity.isContainsDairy());
        dto.setContainsNuts(entity.isContainsNuts());
        dto.setContainsPeanuts(entity.isContainsPeanuts());
        dto.setVegan(entity.isVegan());
    }
}
