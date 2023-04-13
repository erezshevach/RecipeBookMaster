package com.erezshevach.recipebookmaster.ui.controller;

import com.erezshevach.recipebookmaster.service.RecipeService;
import com.erezshevach.recipebookmaster.shared.dto.RecipeDto;
import com.erezshevach.recipebookmaster.ui.model.request.RecipeDetailsRequestModel;
import com.erezshevach.recipebookmaster.ui.model.response.RecipeResponseModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("recipes")
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @GetMapping(path="/{name}", produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public RecipeResponseModel getRecipe(@PathVariable String name) {
        RecipeResponseModel response = new RecipeResponseModel();

        RecipeDto recipeDto = recipeService.getRecipeByName(name);
        BeanUtils.copyProperties(recipeDto, response);

        return response;
    }

    @PostMapping(
            consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @RequestMapping(method = RequestMethod.POST)
    public RecipeResponseModel createRecipe(@RequestBody RecipeDetailsRequestModel recipeDetails) {
        RecipeResponseModel response = new RecipeResponseModel();

        RecipeDto recipeDtoIn = new RecipeDto();
        BeanUtils.copyProperties(recipeDetails, recipeDtoIn);

        RecipeDto recipeDtoOut = recipeService.createRecipe(recipeDtoIn);
        BeanUtils.copyProperties(recipeDtoOut, response);
        return response;
    }

    @PutMapping
    public String updateRecipe() {
        return "updated some recipe";
    }

    @DeleteMapping
    public String deleteRecipe() {
        return "deleted some recipe";
    }
}
