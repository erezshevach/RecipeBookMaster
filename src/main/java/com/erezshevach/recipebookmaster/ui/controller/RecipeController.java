package com.erezshevach.recipebookmaster.ui.controller;

import com.erezshevach.recipebookmaster.recipebookmaster.exceptions.RecipeException;
import com.erezshevach.recipebookmaster.service.RecipeService;
import com.erezshevach.recipebookmaster.shared.dto.RecipeDto;
import com.erezshevach.recipebookmaster.ui.model.request.RecipeRequestModel;
import com.erezshevach.recipebookmaster.ui.model.response.*;
import org.modelmapper.ModelMapper;
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
        RecipeDto recipeDto = recipeService.getRecipeByName(name);

        ModelMapper mapper = new ModelMapper();
        return mapper.map(recipeDto, RecipeResponseModel.class);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<RecipeResponseModel> getRecipes(@RequestParam(value = "pname", defaultValue = "") String partialName,
                                                @RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<RecipeDto> recipeDtos = recipeService.getRecipesByPartialName(partialName, page, limit);

        List<RecipeResponseModel> response = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        for (RecipeDto recipeDto : recipeDtos) {
            RecipeResponseModel res = mapper.map(recipeDto, RecipeResponseModel.class);
            response.add(res);
        }
        return response;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @RequestMapping(method = RequestMethod.POST)
    public RecipeResponseModel createRecipe(@RequestBody RecipeRequestModel recipeDetails) {
        if (recipeDetails.getName() == null ||
                recipeDetails.getProcesses() == null) {
            throw new RecipeException(recipeDetails.getName(), ErrorMessages.MISSING_REQUIRED_FIELD.getMessage());
        }

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
    public OperationStatusResponseModel deleteRecipe(@PathVariable String name) {
        OperationStatusResponseModel response = new OperationStatusResponseModel();
        response.setEntityName(name);
        response.setOperationName(OperationName.DELETE.name());
        recipeService.deleteRecipe(name);
        response.setOperationStatus(OperationStatus.SUCCESS.name());
        return response;
    }
}
