package com.erezshevach.recipebookmaster.service.impl;

import com.erezshevach.recipebookmaster.RecipeRepository;
import com.erezshevach.recipebookmaster.Uom;
import com.erezshevach.recipebookmaster.io.entity.RecipeComponentEntity;
import com.erezshevach.recipebookmaster.io.entity.RecipeEntity;
import com.erezshevach.recipebookmaster.io.entity.RecipeProcessEntity;
import com.erezshevach.recipebookmaster.recipebookmaster.exceptions.RecipeException;
import com.erezshevach.recipebookmaster.shared.Utils;
import com.erezshevach.recipebookmaster.shared.dto.RecipeComponentDto;
import com.erezshevach.recipebookmaster.shared.dto.RecipeDto;
import com.erezshevach.recipebookmaster.shared.dto.RecipeProcessDto;
import com.erezshevach.recipebookmaster.ui.model.response.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    @InjectMocks
    RecipeServiceImpl service;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    Utils utils;

    final String name = "name";
    final String processPid = "P123456789";
    final String componentPid = "C123456789";
    final int page = 1;
    final int limit = 5;
    final ModelMapper mapper = new ModelMapper();
    RecipeEntity recipe;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("create recipe - new")
    void createRecipe_new() {
        when(utils.generateProcessId(10)).thenReturn(processPid);
        when(utils.generateComponentId(10)).thenReturn(componentPid);
        when(recipeRepository.findRecipeByName(anyString())).thenReturn(null);
        RecipeDto recipeDtoIn = mapper.map(buildRecipeEntity(), RecipeDto.class);
        RecipeEntity storedRecipe = mapper.map(recipeDtoIn, RecipeEntity.class);
        when(recipeRepository.save(any(RecipeEntity.class))).thenReturn(storedRecipe);

        RecipeDto recipeDtoOut = service.createRecipe(recipeDtoIn);

        assertAll(
                () -> assertNotNull(recipeDtoOut),
                () -> assertEquals(recipeDtoIn.getName(), recipeDtoOut.getName(), "name should equal input"),
                () -> assertTrue(recipeDtoIn.getProcesses().get(0).similar(recipeDtoOut.getProcesses().get(0)), "process should equal input (0)"),
                () -> assertTrue(recipeDtoIn.getProcesses().get(1).similar(recipeDtoOut.getProcesses().get(1)), "process should equal input (1)"),
                () -> assertTrue(recipeDtoIn.getProcesses().get(0).getComponents().get(0).similar(recipeDtoOut.getProcesses().get(0).getComponents().get(0)), "component should equal input (0/0)"),
                () -> assertTrue(recipeDtoIn.getProcesses().get(1).getComponents().get(1).similar(recipeDtoOut.getProcesses().get(1).getComponents().get(1)), "component should equal input (1/1)"),
                () -> verify(recipeRepository, times(1)).findRecipeByName(anyString()),
                () -> verify(recipeRepository, times(1)).save(any(RecipeEntity.class))
        );
    }

    @Test
    @DisplayName("create recipe - no processes in recipeDtoIn")
    void createRecipe_noProcesses() {
        when(utils.generateProcessId(10)).thenReturn(processPid);
        when(utils.generateComponentId(10)).thenReturn(componentPid);
        when(recipeRepository.findRecipeByName(anyString())).thenReturn(null);
        RecipeDto recipeDtoIn = mapper.map(buildRecipeEntity(), RecipeDto.class);

        recipeDtoIn.setProcesses(new ArrayList<>());
        assertThrows(RecipeException.class, () -> service.createRecipe(recipeDtoIn), "RecipeException should be thrown if recipeDto have no processes (empty)");

        recipeDtoIn.setProcesses(null);
        assertThrows(RecipeException.class, () -> service.createRecipe(recipeDtoIn), "RecipeException should be thrown if recipeDto have no processes (null)");
    }

    @Test
    @DisplayName("create recipe - no name in recipeDtoIn")
    void createRecipe_noName() {
        when(utils.generateProcessId(10)).thenReturn(processPid);
        when(utils.generateComponentId(10)).thenReturn(componentPid);
        when(recipeRepository.findRecipeByName(anyString())).thenReturn(null);
        RecipeDto recipeDtoIn = mapper.map(buildRecipeEntity(), RecipeDto.class);

        recipeDtoIn.setName("   ");
        assertThrows(RecipeException.class, () -> service.createRecipe(recipeDtoIn), "RecipeException should be thrown if recipeDto have no name (blank)");

        recipeDtoIn.setName("");
        assertThrows(RecipeException.class, () -> service.createRecipe(recipeDtoIn), "RecipeException should be thrown if recipeDto have no name (empty)");

        recipeDtoIn.setProcesses(null);
        assertThrows(RecipeException.class, () -> service.createRecipe(recipeDtoIn), "RecipeException should be thrown if recipeDto have no name (null)");

    }

    @Test
    @DisplayName("create recipe - set public IDs")
    void createRecipe_publicIds() {
        when(utils.generateProcessId(10)).thenReturn(processPid);
        when(utils.generateComponentId(10)).thenReturn(componentPid);
        when(recipeRepository.findRecipeByName(anyString())).thenReturn(null);
        RecipeDto recipeDtoIn = mapper.map(buildRecipeEntity(), RecipeDto.class);
        RecipeEntity storedRecipe = mapper.map(recipeDtoIn, RecipeEntity.class);
        when(recipeRepository.save(any(RecipeEntity.class))).thenReturn(storedRecipe);

        assertNull(recipeDtoIn.getProcesses().get(0).getProcessPid());
        RecipeDto recipeDtoOut = service.createRecipe(recipeDtoIn);

        assertAll(
                () -> assertEquals(processPid, recipeDtoIn.getProcesses().get(0).getProcessPid(), "process public ID should be generated and set (0)"),
                () -> assertEquals(processPid, recipeDtoIn.getProcesses().get(1).getProcessPid(), "process public ID should be generated and set (1)"),
                () -> verify(utils, times(recipeDtoIn.getProcesses().size())).generateProcessId(10),
                () -> assertEquals(componentPid, recipeDtoIn.getProcesses().get(0).getComponents().get(0).getComponentPid(), "component public ID should be generated and set (0/0)"),
                () -> assertEquals(componentPid, recipeDtoIn.getProcesses().get(0).getComponents().get(1).getComponentPid(), "component public ID should be generated and set (0/1)"),
                () -> verify(utils, times(4)).generateComponentId(10)
                //checking recipeDtoIn rather than recipeDtoOut because In is the one altered by the method, while Out is mocked
        );
    }

    @Test
    @DisplayName("create recipe - throws exception when record already exists")
    void createRecipe_recordExists() {
        when(utils.generateProcessId(10)).thenReturn(processPid);
        when(utils.generateComponentId(10)).thenReturn(componentPid);
        when(recipeRepository.findRecipeByName(anyString())).thenReturn(new RecipeEntity("existing recipe"));
        RecipeDto recipeDtoIn = mapper.map(buildRecipeEntity(), RecipeDto.class);

        assertThrows(RecipeException.class, () -> service.createRecipe(recipeDtoIn), "RecipeException should be thrown when recipe with similar name already exists");
    }

    @Test
    @DisplayName("create recipe - setting component's related process sequence")
    void createRecipe_relatedProcessSequence() {
        when(utils.generateProcessId(10)).thenReturn(processPid);
        when(utils.generateComponentId(10)).thenReturn(componentPid);
        when(recipeRepository.findRecipeByName(anyString())).thenReturn(null);
        RecipeDto recipeDtoIn = mapper.map(buildRecipeEntity(), RecipeDto.class);
        RecipeEntity storedRecipe = mapper.map(recipeDtoIn, RecipeEntity.class);
        when(recipeRepository.save(any(RecipeEntity.class))).thenReturn(storedRecipe);

        RecipeDto recipeDtoOut = service.createRecipe(recipeDtoIn);

        int firstProcessSeq = recipeDtoIn.getProcesses().get(0).getSequence();
        int secondProcessSeq = recipeDtoIn.getProcesses().get(1).getSequence();
        assertAll(
                () -> assertEquals(firstProcessSeq, recipeDtoOut.getProcesses().get(0).getComponents().get(0).getRelatedProcessSequence(), "related process sequence should be set to each component (0/0)"),
                () -> assertEquals(firstProcessSeq, recipeDtoOut.getProcesses().get(0).getComponents().get(1).getRelatedProcessSequence(), "related process sequence should be set to each component (0/1)"),
                () -> assertEquals(secondProcessSeq, recipeDtoOut.getProcesses().get(1).getComponents().get(0).getRelatedProcessSequence(), "related process sequence should be set to each component (1/0)"),
                () -> assertEquals(secondProcessSeq, recipeDtoOut.getProcesses().get(1).getComponents().get(1).getRelatedProcessSequence(), "related process sequence should be set to each component (1/0)")
        );
    }

    @Test
    @DisplayName("get recipe by name")
    void getRecipeByName() {
        recipe = buildRecipeEntity();
        when(recipeRepository.findRecipeByName(anyString())).thenReturn(recipe);

        RecipeDto recipeDto = service.getRecipeByName(name);

        assertAll(
                () -> assertNotNull(recipeDto, "dto return value should not be null"),
                () -> assertEquals(name, recipeDto.getName(), "dto's name should equal searched name"),
                () -> assertTrue(recipe.getProcesses().get(0).similar(recipeDto.getProcesses().get(0)), "dto's process should equal the entity's process (0)"),
                () -> assertTrue(recipe.getProcesses().get(0).getComponents().get(0).similar(recipeDto.getProcesses().get(0).getComponents().get(0)), "dto's component should equal the entity's component (0/0)")
        );
    }

    @Test
    @DisplayName("get recipe by name - no name provided")
    void getRecipeByName_noName() {
        when(recipeRepository.findRecipeByName(anyString())).thenReturn(null);
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> service.getRecipeByName(""), "IllegalArgumentException should be thrown when name is empty"),
                () -> assertThrows(IllegalArgumentException.class, () -> service.getRecipeByName("   "), "IllegalArgumentException should be thrown when name is blank"),
                () -> assertThrows(IllegalArgumentException.class, () -> service.getRecipeByName(null), "IllegalArgumentException should be thrown when name is null")
        );
    }

    @Test
    @DisplayName("get recipe by name - setting component's related process sequence")
    void getRecipeByName_relatedProcessSequence() {
        recipe = buildRecipeEntity();
        when(recipeRepository.findRecipeByName(anyString())).thenReturn(recipe);

        RecipeDto recipeDto = service.getRecipeByName(name);

        int firstProcessSeq = recipe.getProcesses().get(0).getSequence();
        int secondProcessSeq = recipe.getProcesses().get(1).getSequence();
        assertAll(
                () -> assertEquals(firstProcessSeq, recipeDto.getProcesses().get(0).getComponents().get(0).getRelatedProcessSequence(), "related process sequence should be set to each component (0/0)"),
                () -> assertEquals(firstProcessSeq, recipeDto.getProcesses().get(0).getComponents().get(1).getRelatedProcessSequence(), "related process sequence should be set to each component (0/1)"),
                () -> assertEquals(secondProcessSeq, recipeDto.getProcesses().get(1).getComponents().get(0).getRelatedProcessSequence(), "related process sequence should be set to each component (1/0)"),
                () -> assertEquals(secondProcessSeq, recipeDto.getProcesses().get(1).getComponents().get(1).getRelatedProcessSequence(), "related process sequence should be set to each component (1/0)")
        );
    }

    @Test
    @DisplayName("get recipe by name - throws exception when no record found")
    void getRecipeByName_noRecordFound() {
        when(recipeRepository.findRecipeByName(anyString())).thenReturn(null);

        RecipeException ex = assertThrows(RecipeException.class, () -> service.getRecipeByName("non-existing name"), "RecipeException should be thrown when no record found - non existing");
        assertEquals(ErrorMessages.NO_RECORD_FOUND.getMessage(), ex.getMessage());
    }

    @Test
    @DisplayName("get recipe by partial name")
    void getRecipesByPartialName() {

        List<RecipeEntity> recipes = new ArrayList<>();
        for (int i = 1; i <= limit; i++) {
            recipes.add(new RecipeEntity(name + i));
        }
        Pageable pageable = PageRequest.of(page - 1, limit);
        when(recipeRepository.findRecipesByNameContains(eq(pageable), anyString())).thenReturn(recipes);

        List<RecipeDto> recipeDtos = service.getRecipesByPartialName("partial name", page, limit);

        assertNotNull(recipeDtos, "dto result list should not be null");
        assertEquals(recipes.size(), recipeDtos.size(), "result list size should be full");
        assertTrue(recipes.get(0).similar(recipeDtos.get(0)), "recipe in result list should be as expected");
        assertTrue(recipes.get(1).similar(recipeDtos.get(1)), "recipe in result list should be as expected");
        assertTrue(recipes.get(2).similar(recipeDtos.get(2)), "recipe in result list should be as expected");
    }

    @Test
    @DisplayName("get recipe lists by partial name - empty or blank name")
    void getRecipesByPartialName_noName() {
        List<RecipeEntity> recipes = new ArrayList<>();
        for (int i = 1; i <= limit; i++) {
            recipes.add(new RecipeEntity(name + i));
        }
        Pageable pageable = PageRequest.of(page - 1, limit);
        when(recipeRepository.findRecipesByNameContains(pageable, "")).thenReturn(recipes);

        List<RecipeDto> recipeDtos = service.getRecipesByPartialName("", page, limit);
        assertEquals(recipes.size(), recipeDtos.size(), "result list size should be full for empty name");

        recipeDtos = service.getRecipesByPartialName("    ", page, limit);
        assertEquals(recipes.size(), recipeDtos.size(), "result list size should be full for blank name");

    }

    @Test
    @DisplayName("get recipe lists by partial name - illegal arguments")
    void getRecipesByPartialName_illegalArgs() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> service.getRecipesByPartialName(name, -1, limit), "negative page should throw IllegalArgumentException"),
                () -> assertThrows(IllegalArgumentException.class, () -> service.getRecipesByPartialName(name, page, 0), "non-positive page should throw IllegalArgumentException")
        );
    }

    @Test
    @DisplayName("get recipe lists by partial name - no matches found")
    void getRecipesByPartialName_noMatchesFound() {
        List<RecipeEntity> recipes = new ArrayList<>();
        Pageable pageable = PageRequest.of(page - 1, limit);
        when(recipeRepository.findRecipesByNameContains(pageable, "non-existing partial name")).thenReturn(recipes);

        List<RecipeDto> recipeDtos = service.getRecipesByPartialName("non-existing partial name", page, limit);

        assertTrue(recipeDtos.isEmpty(), "result list should be empty for non-existing name");
    }

    @Test
    @DisplayName("get recipes list by partial name - setting component's related process sequence")
    void getRecipeByPartialName_relatedProcessSequence() {
        recipe = buildRecipeEntity();
        List<RecipeEntity> recipes = new ArrayList<>();
        recipes.add(recipe);
        Pageable pageable = PageRequest.of(page - 1, limit);
        when(recipeRepository.findRecipesByNameContains(eq(pageable), anyString())).thenReturn(recipes);

        List<RecipeDto> recipeDtos = service.getRecipesByPartialName("partial name", page, limit);

        int firstProcessSeq = recipe.getProcesses().get(0).getSequence();
        int secondProcessSeq = recipe.getProcesses().get(1).getSequence();
        assertAll(
                () -> assertEquals(firstProcessSeq, recipeDtos.get(0).getProcesses().get(0).getComponents().get(0).getRelatedProcessSequence(), "related process sequence should be set to each component (0/0)"),
                () -> assertEquals(firstProcessSeq, recipeDtos.get(0).getProcesses().get(0).getComponents().get(1).getRelatedProcessSequence(), "related process sequence should be set to each component (0/1)"),
                () -> assertEquals(secondProcessSeq, recipeDtos.get(0).getProcesses().get(1).getComponents().get(0).getRelatedProcessSequence(), "related process sequence should be set to each component (1/0)"),
                () -> assertEquals(secondProcessSeq, recipeDtos.get(0).getProcesses().get(1).getComponents().get(1).getRelatedProcessSequence(), "related process sequence should be set to each component (1/0)")
        );
    }

    @Test
    @DisplayName("delete recipe - no name provided")
    void deleteRecipe_noName() {
        when(recipeRepository.findRecipeByName(anyString())).thenReturn(recipe);

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> service.deleteRecipe(""), "IllegalArgumentException should be thrown when provided name is empty"),
                () -> assertThrows(IllegalArgumentException.class, () -> service.deleteRecipe("   "), "IllegalArgumentException should be thrown when provided name is blank"),
                () -> assertThrows(IllegalArgumentException.class, () -> service.deleteRecipe(null), "IllegalArgumentException should be thrown when provided name is null")
        );
    }

    @Test
    @DisplayName("delete recipe - throws exception when no record found")
    void deleteRecipe_noRecordFound() {
        when(recipeRepository.findRecipeByName(anyString())).thenReturn(null);

        RecipeException ex = assertThrows(RecipeException.class, () -> service.deleteRecipe("non-existing name"), "RecipeException should be thrown when no record found - non existing");
        assertEquals(ErrorMessages.NO_RECORD_FOUND.getMessage(), ex.getMessage());
    }

    private RecipeEntity buildRecipeEntity() {
        List<RecipeComponentEntity> components1 = new ArrayList<>();
        components1.add(new RecipeComponentEntity(100, Uom.G, "butter", "cold"));
        components1.add(new RecipeComponentEntity(250, Uom.G, "sugar", null));
        List<RecipeComponentEntity> components2 = new ArrayList<>();
        components2.add(new RecipeComponentEntity(100, Uom.G, "flour", "sifted"));
        components2.add(new RecipeComponentEntity(250, Uom.G, "chocolate", "melted"));
        List<RecipeProcessEntity> processes = new ArrayList<>();
        processes.add(new RecipeProcessEntity(1, "mix", components1, null));
        processes.add(new RecipeProcessEntity(2, "bake", components2, null));
        return new RecipeEntity(name, processes);
    }
}