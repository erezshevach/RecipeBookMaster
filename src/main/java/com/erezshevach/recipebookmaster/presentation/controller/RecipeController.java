package com.erezshevach.recipebookmaster.presentation.controller;

import com.erezshevach.recipebookmaster.exceptions.RecipeException;
import com.erezshevach.recipebookmaster.data.entity.RecipeEntity;
import com.erezshevach.recipebookmaster.service.RecipeProcessService;
import com.erezshevach.recipebookmaster.service.RecipeService;
import com.erezshevach.recipebookmaster.shared.dto.RecipeDto;
import com.erezshevach.recipebookmaster.presentation.model.request.RecipeRequestModel;
import com.erezshevach.recipebookmaster.presentation.model.response.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/recipes")
@CrossOrigin(origins = "http://localhost:3000")
public class RecipeController {
    RecipeService recipeService;
    RecipeProcessService processService;
    ModelMapper mapper;


    /**
     * constructor, autowired with the required services
     */
    @Autowired
    public RecipeController(RecipeService recipeService, RecipeProcessService processService) {
        this.recipeService = recipeService;
        this.processService = processService;
        mapper = new ModelMapper();
    }


    /**
     * POST / createRecipe / creates new recipe
     * @param recipeDetails as a RecipeRequestModel
     * @return RecipeResponseModel corresponding to the created recipe
     * throws RecipeException if the new recipe fields are invalid
     * throws RecipeException if the new recipe name already exists in the DB
     */
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @RequestMapping(method = RequestMethod.POST)
    public RecipeResponseModel createRecipe(@RequestBody @NotNull RecipeRequestModel recipeDetails)
        throws RecipeException {

        RecipeDto recipeDtoIn = mapper.map(recipeDetails, RecipeDto.class);
        RecipeDto recipeDtoOut = recipeService.createRecipe(recipeDtoIn);
        return mapper.map(recipeDtoOut, RecipeResponseModel.class);
    }

    /**
     * GET / getRecipeByName / gets a recipe by name
     * @param name prom the path variable
     * @returns a recipe as RecipeResponseModel
     * throws RecipeException if no record found
     *//*
   @GetMapping(
            path = "/{name}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public RecipeResponseModel getRecipeByName(@PathVariable String name) {
        RecipeDto recipeDto = recipeService.getRecipeByName(name);

        return mapper.map(recipeDto, RecipeResponseModel.class);
    }*/

    /**
     * GET / getRecipe / gets a recipe by publicID
     * @param pid prom the path variable
     * @return a recipe as RecipeResponseModel
     * throws RecipeException if no record found
     */
    @GetMapping(
            path = "/{pid}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public RecipeResponseModel getRecipe(@PathVariable String pid)
        throws RecipeException {
        RecipeDto recipeDto = recipeService.getRecipeByPid(pid);
        return mapper.map(recipeDto, RecipeResponseModel.class);
    }

    /**
     * GET / getRecipes / getting list of recipes by partial name
     * @param partialName from query params. defaulted to empty string, which will return all recipes.
     * @param page from query params. determines the result page number. defaulted to 1
     * @param limit from query params. determines the number of records in a single result page. defaulted to 25
     * @return a list of recipes as RecipeResponseModel
     *//*
    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<RecipeResponseModel> getRecipes(@RequestParam(value = "pname", defaultValue = "") String partialName,
                                                @RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "limit", defaultValue = "25") int limit) {
        partialName = partialName.trim();
        List<RecipeDto> recipeDtos = recipeService.getRecipesByPartialName(partialName, page, limit);

        return mapper.map(recipeDtos, new TypeToken<List<RecipeResponseModel>>() {}.getType());
    }*/

    /**
     * GET / getRecipeHeaders / getting list of recipe-headers by partial name
     * @param partialName from query params. defaulted to empty string, which will return all recipes.
     * @param page from query params. determines the result page serial number. defaulted to 1
     * @param limit from query params. determines the number of records in a single result page. defaulted to 25
     * @return a list of recipe-headers as RecipeResponseModel. empty list if no matching records were found.
     */
    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<RecipeResponseModel> getRecipeHeaders(@RequestParam(value = "pname", defaultValue = "") String partialName, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "25") int limit) {
        partialName = partialName.trim();
        List<RecipeDto> recipeDtos = recipeService.getRecipeHeadersByPartialName(partialName, page, limit);

        return mapper.map(recipeDtos, new TypeToken<List<RecipeResponseModel>>() {}.getType());
    }

    /**
     * PUT / updateRecipe / updating a recipe by publicID
     * the entire
     * @param pid
     * @param recipeDetails
     * @return
     * throws RecipeException if the updated recipe request fields are invalid
     * throws RecipeException if no record found with same pID
     * throws RecipeException if name was changed (other than case) and a record with a similar name already exists
     */
    @PutMapping(
            path = "/{pid}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public RecipeResponseModel updateRecipe(@PathVariable String pid, @RequestBody @NotNull RecipeRequestModel recipeDetails)
        throws RecipeException {

        RecipeDto recipeDtoIn = mapper.map(recipeDetails, RecipeDto.class);
        RecipeDto recipeDtoOut = recipeService.updateRecipeByPid(pid, recipeDtoIn);
        return mapper.map(recipeDtoOut, RecipeResponseModel.class);
    }

    /**
     * DELETE / deleteRecipe / deletes a recipe from the DB based on publicID
     * @param pid prom the path variable
     * @return the deleted recipe header as RecipeResponseModel
     * if record not found, throws RecipeException
     */
    @DeleteMapping(
            path = "/{pid}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public RecipeResponseModel deleteRecipe(@PathVariable @NotNull @NotEmpty String pid) {
        return mapper.map(recipeService.deleteRecipeByPid(pid), RecipeResponseModel.class);
//        OperationStatusResponseModel response = new OperationStatusResponseModel();
//        response.setEntityClass(RecipeEntity.class.getName());
//        response.setEntityIdentifier(pid);
//        response.setOperationName(OperationName.DELETE);
////        try {
//            recipeService.deleteRecipeByPid(pid);
//            response.setOperationStatus(OperationStatus.SUCCESS);
////        } catch (RecipeException e) {
////            response.setOperationStatus(OperationStatus.FAILURE);
////            response.setStatusMsg("Failed deleting " + pid + ": " + e.getMessage());
////        }
//        return response;
    }

    /**
     * DELETE / deleteProcess / deletes a process from the DB based on publicID
     * @param pid prom the path variable
     * @return the status as OperationStatusResponseModel
     * if record not found, throws RecipeException
     */
    @DeleteMapping(
            path = "/process/{pid}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public OperationStatusResponseModel deleteProcess(@PathVariable @NotNull @NotEmpty String pid) {
        OperationStatusResponseModel response = new OperationStatusResponseModel();
        response.setEntityClass(RecipeEntity.class.getName());
        response.setEntityIdentifier(pid);
        response.setOperationName(OperationName.DELETE);
        processService.deleteProcessByPid(pid);
        response.setOperationStatus(OperationStatus.SUCCESS);
        return response;
    }
}
