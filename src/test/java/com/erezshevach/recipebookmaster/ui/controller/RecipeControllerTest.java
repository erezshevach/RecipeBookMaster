package com.erezshevach.recipebookmaster.ui.controller;

import com.erezshevach.recipebookmaster.Uom;
import com.erezshevach.recipebookmaster.io.entity.RecipeComponentEntity;
import com.erezshevach.recipebookmaster.io.entity.RecipeEntity;
import com.erezshevach.recipebookmaster.io.entity.RecipeProcessEntity;
import com.erezshevach.recipebookmaster.service.RecipeService;
import com.erezshevach.recipebookmaster.shared.dto.RecipeDto;
import com.erezshevach.recipebookmaster.ui.model.request.RecipeRequestModel;
import com.erezshevach.recipebookmaster.ui.model.response.OperationName;
import com.erezshevach.recipebookmaster.ui.model.response.OperationStatus;
import com.erezshevach.recipebookmaster.ui.model.response.OperationStatusResponseModel;
import com.erezshevach.recipebookmaster.ui.model.response.RecipeResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class RecipeControllerTest {

    @InjectMocks
    RecipeController controller;
    @Mock
    RecipeService service;

    ModelMapper mapper = new ModelMapper();
    RecipeDto recipeDto;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeDto = buildRecipeDto();
    }

    @Test
    @DisplayName("get recipe")
    void getRecipe() {
        when(service.getRecipeByPid(anyString())).thenReturn(recipeDto);

        RecipeResponseModel response = controller.getRecipe("some name");

        assertAll(
                ()-> assertNotNull(response, "response model should not be null"),
                ()-> assertTrue(recipeDto.similar(response), "response details should be similar to dto details")
        );
    }

    @Test
    @DisplayName("get recipes")
    void getRecipes() {
        int page = 1;
        int limit = 5;
        List<RecipeDto> recipeDtos = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            recipeDtos.add(buildRecipeDto());
        }
        when(service.getRecipeHeadersByPartialName(anyString(), anyInt(), anyInt())).thenReturn(recipeDtos);

        List<RecipeResponseModel> response = controller.getRecipeHeaders("some name", page, limit);

        assertAll(
                ()-> assertNotNull(response, "response model list should not be null"),
                ()-> assertEquals(recipeDtos.size(), response.size(), "response model list size should be as expected"),
                ()-> assertTrue(recipeDtos.get(0).similar(response.get(0)), "response details should be similar to dto details (0)"),
                ()-> assertTrue(recipeDtos.get(1).similar(response.get(1)), "response details should be similar to dto details (1)"),
                ()-> assertTrue(recipeDtos.get(2).similar(response.get(2)), "response details should be similar to dto details (2)")
        );
    }

    @Test
    @DisplayName("create recipe")
    void createRecipe() {
        RecipeRequestModel request = buildRecipeRequestModel();
        recipeDto = mapper.map(request, RecipeDto.class);
        when(service.createRecipe(any(RecipeDto.class))).thenReturn(recipeDto);

        RecipeResponseModel response = controller.createRecipe(request);

        assertAll(
                ()-> assertNotNull(response, "response model should not be null"),
                ()-> assertTrue(request.similar(response), "response details should be similar to request details")
        );
    }

    @Test
    @DisplayName("delete recipe")
    void deleteRecipe() {
        String name = "some name";
        OperationStatusResponseModel response = controller.deleteRecipe(name);

        assertAll(
                ()-> assertEquals(name, response.getEntityName(), "name should be equal to input"),
                ()-> assertEquals(OperationName.DELETE.name(), response.getOperationName(), "operation name should be equal delete"),
                ()-> assertEquals(OperationStatus.SUCCESS.name(), response.getOperationStatus(), "operation status should be success")
        );
    }


    private RecipeEntity buildRecipeEntity(){
        List<RecipeComponentEntity> components1 = new ArrayList<>();
        components1.add(new RecipeComponentEntity(100, Uom.G, "butter", "cold"));
        components1.add(new RecipeComponentEntity(250, Uom.G, "sugar", null));
        List<RecipeComponentEntity> components2 = new ArrayList<>();
        components2.add(new RecipeComponentEntity(100, Uom.G, "flour", "sifted"));
        components2.add(new RecipeComponentEntity(250, Uom.G, "chocolate", "melted"));
        List<RecipeProcessEntity> processes = new ArrayList<>();
        processes.add(new RecipeProcessEntity(1, "mix", components1, null));
        processes.add(new RecipeProcessEntity(2, "bake", components2, null));
        return new RecipeEntity("recipe", processes);
    }

    private RecipeDto buildRecipeDto() {
        return mapper.map(buildRecipeEntity(), RecipeDto.class);
    }

    private RecipeRequestModel buildRecipeRequestModel() {
        return mapper.map(buildRecipeEntity(), RecipeRequestModel.class);
    }
}
