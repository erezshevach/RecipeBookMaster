package com.erezshevach.recipebookmaster.ui.controller;

import com.erezshevach.recipebookmaster.recipebookmaster.exceptions.RecipeException;
import com.erezshevach.recipebookmaster.service.RecipeService;
import com.erezshevach.recipebookmaster.shared.dto.RecipeDto;
import com.erezshevach.recipebookmaster.ui.model.request.RecipeRequestModel;
import com.erezshevach.recipebookmaster.ui.model.response.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("recipes")
public class RecipeController {
    RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping(path = "/{name}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public RecipeResponseModel getRecipe(@PathVariable String name) {
        name = validateName(name);
        RecipeDto recipeDto = recipeService.getRecipeByName(name);

        ModelMapper mapper = new ModelMapper();
        return mapper.map(recipeDto, RecipeResponseModel.class);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<RecipeResponseModel> getRecipes(@RequestParam(value = "pname", defaultValue = "") String partialName,
                                                @RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "limit", defaultValue = "25") int limit) {
        partialName = partialName.trim();
        List<RecipeDto> recipeDtos = recipeService.getRecipesByPartialName(partialName, page, limit);

        ModelMapper mapper = new ModelMapper();
        List<RecipeResponseModel> response = mapper.map(recipeDtos, new TypeToken<List<RecipeResponseModel>>() {}.getType());

        return response;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @RequestMapping(method = RequestMethod.POST)
    public RecipeResponseModel createRecipe(@RequestBody @NotNull RecipeRequestModel recipeDetails) {
        String name = recipeDetails.getName();
        if (recipeDetails.getProcesses() == null || recipeDetails.getProcesses().isEmpty())
            throw new RecipeException(name, ErrorMessages.MISSING_REQUIRED_FIELD.getMessage() + ": processes");
        if (name == null || name.isEmpty() || name.isBlank())
            throw new RecipeException(name, ErrorMessages.MISSING_REQUIRED_FIELD.getMessage() + ": name");


        ModelMapper mapper = new ModelMapper();
        RecipeDto recipeDtoIn = mapper.map(recipeDetails, RecipeDto.class);
        RecipeDto recipeDtoOut = recipeService.createRecipe(recipeDtoIn);
        return mapper.map(recipeDtoOut, RecipeResponseModel.class);
    }

    @PutMapping
    public String updateRecipe() {
        return "updated some recipe";
    }

    @DeleteMapping(path = "/{name}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusResponseModel deleteRecipe(@PathVariable @NotNull @NotEmpty String name) {
        name = validateName(name);
        OperationStatusResponseModel response = new OperationStatusResponseModel();
        response.setEntityName(name);
        response.setOperationName(OperationName.DELETE.name());
        recipeService.deleteRecipe(name);
        response.setOperationStatus(OperationStatus.SUCCESS.name());
        return response;
    }

    private String validateName(String name) {
        if (name == null || name.isEmpty() || name.isBlank())
            throw new IllegalArgumentException("valid name must be provided");
        return name.trim();
    }
}
