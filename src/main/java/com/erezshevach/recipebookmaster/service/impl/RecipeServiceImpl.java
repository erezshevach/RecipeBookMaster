package com.erezshevach.recipebookmaster.service.impl;

import com.erezshevach.recipebookmaster.data.reposirory.RecipeProcessRepository;
import com.erezshevach.recipebookmaster.data.reposirory.RecipeRepository;
import com.erezshevach.recipebookmaster.data.entity.RecipeEntity;
import com.erezshevach.recipebookmaster.exceptions.RecipeException;
import com.erezshevach.recipebookmaster.service.RecipeProcessService;
import com.erezshevach.recipebookmaster.service.RecipeService;
import com.erezshevach.recipebookmaster.shared.Utils;
import com.erezshevach.recipebookmaster.shared.dto.*;
import com.erezshevach.recipebookmaster.exceptions.ErrorMessages;

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
    RecipeProcessService processService;
    ModelMapper mapper;
    private int pidLength = 10;


    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeProcessService processService, RecipeProcessRepository processRepository) {
        this.recipeRepository = recipeRepository;
        this.processRepository = processRepository;
        this.processService = processService;
        this.mapper = new ModelMapper();
    }

    /**
     * createRecipe
     * @param recipeDtoIn the new recipe as DTO
     * @return the created recipe as DTO
     * throws RecipeException if the new recipe fields are invalid
     * throws RecipeException if the new recipe name already exists in the DB
     */
    @Override
    public RecipeDto createRecipe(@NotNull RecipeDto recipeDtoIn) throws RecipeException {
        validateRequiredFields(recipeDtoIn);
        setGeneratedPublicIds(recipeDtoIn);

        RecipeEntity recipe = mapper.map(recipeDtoIn, RecipeEntity.class);
        if (recipeRepository.findByNameIgnoreCase(recipeDtoIn.getName()) != null)
            throw new RecipeException(recipeDtoIn.getName(), RecipeEntity.class.getName(), ErrorMessages.RECORD_EXISTS.getMessage());

        RecipeEntity storedRecipeEntity = recipeRepository.save(recipe);
        RecipeDto recipeDtoOut = mapper.map(storedRecipeEntity, RecipeDto.class);
        //setRelatedProcessSequenceForPresentation(recipeDtoOut);

        return recipeDtoOut;
    }

    /**
     * getRecipeByName
     * @param name
     * @return the corresponding recipe as DTO
     * throws RecipeException if no record found
     */
    @Override
    public RecipeDto getRecipeByName(String name) throws RecipeException {
        RecipeEntity recipeEntity = getRecipeEntityByName(name);
        RecipeDto recipeDto = mapper.map(recipeEntity, RecipeDto.class);
        //setRelatedProcessSequenceForPresentation(recipeDto);

        return recipeDto;
    }

    /**
     * getRecipeByPid
     * @param pid
     * @return the corresponding recipe as DTO
     * throws RecipeException if no record found
     */
    @Override
    public RecipeDto getRecipeByPid(String pid) throws RecipeException {
        RecipeEntity recipeEntity = getRecipeEntityByPid(pid);
        RecipeDto recipeDto = mapper.map(recipeEntity, RecipeDto.class);
        //setRelatedProcessSequenceForPresentation(recipeDto);

        return recipeDto;
    }

    //@Override
    public List<RecipeDto> getRecipesByPartialName(String partialName, int page, int limit) {
        if (page < 0 || limit < 1) throw new IllegalArgumentException("page/limit must be positive");
        partialName = partialName.trim();
        List<RecipeDto> result = new ArrayList<>();
        if (page > 0) page -= 1;
        Pageable pageable = PageRequest.of(page, limit);
        List<RecipeEntity> recipes = recipeRepository.findByNameContainsOrderByName(pageable, partialName);
        for (RecipeEntity recipe : recipes) {
            RecipeDto recipeDto = mapper.map(recipe, RecipeDto.class);
            //setRelatedProcessSequenceForPresentation(recipeDto);
            result.add(recipeDto);
        }
        return result;
    }

    /**
     * getRecipeHeadersByPartialName
     * @param partialName
     * @param page - the serial page number of the result records list
     * @param limit - max number of records in one page
     * @return a list of recipes DTO loaded with only header values. empty list if no matching records were found.
     * if partial name is empty or blank, all records will be retrieved
     */
    @Override
    public List<RecipeDto> getRecipeHeadersByPartialName(String partialName, int page, int limit) {
        if (page < 0 || limit < 1) throw new IllegalArgumentException("page/limit must be positive");
        partialName = partialName.trim();
        List<RecipeDto> result = new ArrayList<>();
        if (page > 0) page -= 1;
        Pageable pageable = PageRequest.of(page, limit);
        List<RecipeEntity> recipes = recipeRepository.findByNameContainsOrderByName(pageable, partialName);
        for (RecipeEntity recipe : recipes) {
            RecipeDto recipeDto = new RecipeDto();
            mapOnlyHeaderValues(recipeDto, recipe);
            result.add(recipeDto);
        }
        return result;
    }

    /**
     * updateRecipeByPid
     * the update reuses the same recipe record, but completely replaces the processes and components records (resulting in new records and new IDs for them)
     * @param pid
     * @param recipeUpdatesDto - a recipe DTO with the update request
     * @return a recipe DTO with the updated values
     * throws RecipeException if the updated recipe request fields are invalid
     * throws RecipeException if no record found with same pID
     * throws RecipeException if name was changed (other than case) and a record with a similar name already exists
     */
    @Override
    public RecipeDto updateRecipeByPid(String pid, RecipeDto recipeUpdatesDto) {
        validateRequiredFields(recipeUpdatesDto);
        setGeneratedPublicIds(recipeUpdatesDto);
        RecipeEntity recipeUpdates = mapper.map(recipeUpdatesDto, RecipeEntity.class);

        RecipeEntity existingRecipe = getRecipeEntityByPid(pid);

        // throwing RecipeException if name was changed (other than case) and a record with a similar name already exists
        String updatedName = recipeUpdates.getName();
        if (!updatedName.equals(existingRecipe.getName())) {
            if (!updatedName.equalsIgnoreCase(existingRecipe.getName())) {
                if (recipeRepository.findByNameIgnoreCase(updatedName) != null)
                    throw new RecipeException(updatedName, RecipeEntity.class.getName(), ErrorMessages.RECORD_EXISTS.getMessage());
            }
        }

        // updating the header values by copying
        existingRecipe.copyHeaderValuesFromTarget(recipeUpdates);

        // updating the processes list by replacing and deleting old ones
        processService.deleteProcessesByOfRecipe(existingRecipe);
        existingRecipe.setProcesses(recipeUpdates.getProcesses());

        // updating in DB
        RecipeEntity updatedRecipe = recipeRepository.save(existingRecipe);
        RecipeDto updatedRecipeDto = mapper.map(updatedRecipe, RecipeDto.class);
        //setRelatedProcessSequenceForPresentation(recipeDtoOut);

        return updatedRecipeDto;
    }

    /**
     * deleteRecipeByName
     * @param name
     * throws RecipeException if no matching record was found
     * @return recipe DTO loaded with the deleted recipe header values
     */
    @Override
    public RecipeDto deleteRecipeByName(String name) throws RecipeException {
        RecipeEntity recipeEntity = getRecipeEntityByName(name);
        RecipeDto deleted = new RecipeDto();
        mapOnlyHeaderValues(deleted, recipeEntity);
        recipeRepository.delete(recipeEntity);
        return deleted;
    }

    /**
     * deleteRecipeByPid
     * @param pid
     * throws RecipeException if no matching record was found
     * @return recipe DTO loaded with the deleted recipe header values
     */
    @Override
    public RecipeDto deleteRecipeByPid(String pid) throws RecipeException {
        RecipeEntity recipeEntity = getRecipeEntityByPid(pid);
        RecipeDto deleted = new RecipeDto();
        mapOnlyHeaderValues(deleted, recipeEntity);
        recipeRepository.delete(recipeEntity);
        return deleted;
    }

////////////////////////////////////////////////////////////////


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

    /**
     * validateRequiredFields - validates the required fields in a recipe DTO and trims the name
     * @param recipeDto
     * @return recipeDto
     * @throws RecipeException if any of the required fields is invalid
     */
    private RecipeDto validateRequiredFields(RecipeDto recipeDto) throws RecipeException {
        String name = recipeDto.getName();
        if (name == null || name.isEmpty() || name.isBlank())
            throw new RecipeException(name, RecipeEntity.class.getName(), ErrorMessages.MISSING_REQUIRED_FIELD.getMessage() + ": name");
        if (recipeDto.getProcesses() == null || recipeDto.getProcesses().isEmpty())
            throw new RecipeException(name, RecipeEntity.class.getName(), ErrorMessages.MISSING_REQUIRED_FIELD.getMessage() + ": processes");
        recipeDto.setName(name.trim());
        return recipeDto;
    }

    /**
     * getRecipeEntityByPid
     * @param pid
     * @return the recipe entity corresponding to the pID
     * throws RecipeException if no record found
     */
    private RecipeEntity getRecipeEntityByPid(String pid) throws RecipeException {
        pid = validatePid(pid);
        RecipeEntity recipeEntity = recipeRepository.findByRecipePid(pid);
        if (recipeEntity == null)
            throw new RecipeException(pid, RecipeEntity.class.getName(), ErrorMessages.NO_RECORD_FOUND.getMessage());

        return recipeEntity;
    }

    /**
     * getRecipeEntityByName
     * @param name
     * @return the recipe entity corresponding to the name
     * throws RecipeException if no record found
     */
    private RecipeEntity getRecipeEntityByName(String name) throws RecipeException {
        name = validateName(name);
        RecipeEntity recipeEntity = recipeRepository.findByNameIgnoreCase(name);
        if (recipeEntity == null)
            throw new RecipeException(name, RecipeEntity.class.getName(), ErrorMessages.NO_RECORD_FOUND.getMessage());
        return recipeEntity;
    }

    /**
     * setGeneratedPublicIds - sets the pIDs for the recipe, its processes and all components.
     * @param recipe as DTO
     */
    private void setGeneratedPublicIds(RecipeDto recipe) {
        recipe.setRecipePid(Utils.generateRecipePid(pidLength, recipe.getName()));
        if (recipe.getProcesses() != null) {
            for (RecipeProcessDto process : recipe.getProcesses()) {
                process.setProcessPid(Utils.generateProcessPid(pidLength));
                if (process.getComponents() != null) {
                    for (RecipeComponentDto component : process.getComponents()) {
                        component.setComponentPid(Utils.generateComponentPid(pidLength));
                    }
                }
            }
        }
    }

    /**
     * mapOnlyHeaderValues - mapping only the header values from a recipe entity to a DTO
     * @param dto - target recipe DTO
     * @param entity - source recipe entity
     */
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

    private void setStructuralAttributes_input(RecipeDto recipe) {
        for (RecipeProcessDto process : recipe.getProcesses()) {
            process.setOfRecipe(recipe);
            process.setProcessPid(Utils.generateProcessPid(10));
            for (RecipeComponentDto component : process.getComponents()) {
                component.setOfRecipe(recipe);
                component.setOfProcess(process);
                component.setComponentPid(Utils.generateComponentPid(10));
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

}
