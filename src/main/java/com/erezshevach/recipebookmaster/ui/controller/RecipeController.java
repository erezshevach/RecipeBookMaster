package com.erezshevach.recipebookmaster.ui.controller;

import com.erezshevach.recipebookmaster.service.RecipeProcessService;
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

import java.util.List;

@RestController
@RequestMapping("recipes")
public class RecipeController {
    RecipeService recipeService;
    RecipeProcessService processService;

    @Autowired
    public RecipeController(RecipeService recipeService, RecipeProcessService processService) {
        this.recipeService = recipeService;
        this.processService = processService;
    }


    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @RequestMapping(method = RequestMethod.POST)
    public RecipeResponseModel createRecipe(@RequestBody @NotNull RecipeRequestModel recipeDetails) {

        ModelMapper mapper = new ModelMapper();
        RecipeDto recipeDtoIn = mapper.map(recipeDetails, RecipeDto.class);
        RecipeDto recipeDtoOut = recipeService.createRecipe(recipeDtoIn);
        return mapper.map(recipeDtoOut, RecipeResponseModel.class);
    }

/*    @GetMapping(
            path = "/{name}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public RecipeResponseModel getRecipeByName(@PathVariable String name) {
        RecipeDto recipeDto = recipeService.getRecipeByName(name);

        ModelMapper mapper = new ModelMapper();
        return mapper.map(recipeDto, RecipeResponseModel.class);
    }*/

    @GetMapping(
            path = "/{pid}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public RecipeResponseModel getRecipe(@PathVariable String pid) {
        RecipeDto recipeDto = recipeService.getRecipeByPid(pid);

        ModelMapper mapper = new ModelMapper();
        return mapper.map(recipeDto, RecipeResponseModel.class);
    }

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<RecipeResponseModel> getRecipes(@RequestParam(value = "pname", defaultValue = "") String partialName,
                                                @RequestParam(value = "page", defaultValue = "1") int page,
                                                @RequestParam(value = "limit", defaultValue = "25") int limit) {
        partialName = partialName.trim();
        List<RecipeDto> recipeDtos = recipeService.getRecipesByPartialName(partialName, page, limit);

        ModelMapper mapper = new ModelMapper();
        return mapper.map(recipeDtos, new TypeToken<List<RecipeResponseModel>>() {}.getType());
    }

    @PutMapping(
            path = "/{pid}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public RecipeResponseModel updateRecipe(@PathVariable String pid, @RequestBody @NotNull RecipeRequestModel recipeDetails) {

        ModelMapper mapper = new ModelMapper();
        RecipeDto recipeDtoIn = mapper.map(recipeDetails, RecipeDto.class);
        RecipeDto recipeDtoOut = recipeService.updateRecipeByPid(pid, recipeDtoIn);
        return mapper.map(recipeDtoOut, RecipeResponseModel.class);
    }

    @DeleteMapping(
            path = "/{pid}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public OperationStatusResponseModel deleteRecipe(@PathVariable @NotNull @NotEmpty String pid) {
        OperationStatusResponseModel response = new OperationStatusResponseModel();
        response.setEntityName(pid);
        response.setOperationName(OperationName.DELETE.name());
        recipeService.deleteRecipeByPid(pid);
        response.setOperationStatus(OperationStatus.SUCCESS.name());
        return response;
    }

    @DeleteMapping(
            path = "/process/{pid}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public OperationStatusResponseModel deleteProcess(@PathVariable @NotNull @NotEmpty String pid) {
        OperationStatusResponseModel response = new OperationStatusResponseModel();
        response.setEntityName(pid);
        response.setOperationName(OperationName.DELETE.name());
        processService.deleteProcessByPid(pid);
        response.setOperationStatus(OperationStatus.SUCCESS.name());
        return response;
    }
}
