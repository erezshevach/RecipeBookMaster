package com.erezshevach.recipebookmaster.presentation.controller;

import com.erezshevach.recipebookmaster.shared.Uom;
import com.erezshevach.recipebookmaster.data.entity.RecipeComponentEntity;
import com.erezshevach.recipebookmaster.data.entity.RecipeEntity;
import com.erezshevach.recipebookmaster.data.entity.RecipeProcessEntity;
import com.erezshevach.recipebookmaster.service.RecipeService;
import com.erezshevach.recipebookmaster.shared.Utils;
import com.erezshevach.recipebookmaster.shared.dto.RecipeDto;
import com.erezshevach.recipebookmaster.presentation.model.request.RecipeRequestModel;
import com.erezshevach.recipebookmaster.presentation.model.response.RecipeResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RecipeControllerTest {

    @InjectMocks
    RecipeController controller;
    @Mock
    RecipeService service;
    @Captor
    ArgumentCaptor<RecipeDto> dtoCapture;

    ModelMapper mapper = new ModelMapper();
    RecipeDto recipeDto;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipeDto = buildSampleRecipeDto();
    }

    @Test
    @DisplayName("get recipe")
    void getRecipe() {
        when(service.getRecipeByPid(anyString())).thenReturn(recipeDto);

        RecipeResponseModel response = controller.getRecipe("some name");

        assertAll(
                () -> assertNotNull(response, "response model should not be null"),
                () -> assertTrue(Utils.compareRecipes(recipeDto, response), "response details should be similar to dto details")
        );
    }

    @Test
    @DisplayName("get recipes")
    void getRecipes() {
        int page = 1;
        int limit = 5;
        List<RecipeDto> recipeDtos = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            recipeDtos.add(buildSampleRecipeDto());
        }
        when(service.getRecipeHeadersByPartialName(anyString(), anyInt(), anyInt())).thenReturn(recipeDtos);

        List<RecipeResponseModel> response = controller.getRecipeHeaders("some name", page, limit);

        assertAll(
                () -> assertNotNull(response, "response model list should not be null"),
                () -> assertEquals(recipeDtos.size(), response.size(), "response model list size should be as expected"),
                () -> assertTrue(Utils.compareRecipes(recipeDtos.get(0), response.get(0)), "response details should be similar to dto details (0)"),
                () -> assertTrue(Utils.compareRecipes(recipeDtos.get(1), response.get(1)), "response details should be similar to dto details (1)"),
                () -> assertTrue(Utils.compareRecipes(recipeDtos.get(2), response.get(2)), "response details should be similar to dto details (2)")
        );
    }

    @Test
    @DisplayName("create recipe")
    void createRecipe() {
        RecipeRequestModel request = buildSampleRecipeRequestModel();
        recipeDto = mapper.map(request, RecipeDto.class);
        when(service.createRecipe(any(RecipeDto.class))).thenReturn(recipeDto);

        RecipeResponseModel response = controller.createRecipe(request);

        assertAll(
                () -> assertNotNull(response, "response model should not be null"),
                () -> verify(service, times(1)).createRecipe(any(RecipeDto.class)),
                () -> verify(service).createRecipe(dtoCapture.capture()),
                () -> assertTrue(Utils.compareRecipes(recipeDto, dtoCapture.getValue()), "dto argument should be passed correctly to the service")
        );
    }

    @Test
    @DisplayName("delete recipe")
    void deleteRecipe() {
        String pId = "recipe_XXXXXXXX";
        when(service.deleteRecipeByPid(pId)).thenReturn(buildSampleRecipeDtoAsHeader());
        controller.deleteRecipe(pId);

        assertAll(
                () -> verify(service, times(1)).deleteRecipeByPid(pId)
        );
    }


    private RecipeEntity buildSampleRecipeEntity() {
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

    private RecipeDto buildSampleRecipeDto() {
        return mapper.map(buildSampleRecipeEntity(), RecipeDto.class);
    }

    private RecipeDto buildSampleRecipeDtoAsHeader() {
        RecipeDto dto = new RecipeDto();
        RecipeDto.mapOnlyHeaderValues(dto, buildSampleRecipeEntity());
        return dto;
    }

    private RecipeRequestModel buildSampleRecipeRequestModel() {
        return mapper.map(buildSampleRecipeEntity(), RecipeRequestModel.class);
    }
}
