package com.erezshevach.recipebookmaster.io.entity;

import com.erezshevach.recipebookmaster.shared.dto.RecipeDto;
import com.erezshevach.recipebookmaster.shared.dto.RecipeProcessDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeEntityTest {

    private final String name = "a name";
    private List<RecipeProcessEntity> processes;

    @BeforeEach
    void setUp() {
        processes = new ArrayList<>();
    }

    @Test
    @DisplayName("creation - with name")
    void testCreation_withName() {
        RecipeEntity recipe = new RecipeEntity(name);

        assertEquals(name, recipe.getName(), "name should be equal to input");
    }

    @Test
    @DisplayName("creation - with name and processes list")
    void testCreation_withNameAndProcesses() {
        processes.add(new RecipeProcessEntity(1, "mix together"));
        processes.add(new RecipeProcessEntity(2, "bake"));
        RecipeEntity recipe = new RecipeEntity(name, processes);
        assertAll(
                () -> assertEquals(name, recipe.getName(), "name should be equal to input"),
                () -> assertEquals(processes, recipe.getProcesses(), "processes list should be equal to input"),
                ()-> assertEquals(processes.size(), recipe.getProcesses().size(), "processes list size should be equal to input"),
                ()-> assertTrue(processes.get(0).similar(recipe.getProcesses().get(0)), "process should be equal to input (0)"),
                ()-> assertTrue(processes.get(1).similar(recipe.getProcesses().get(1)), "process should be equal to input (1)"),
                () -> assertEquals(recipe, recipe.getProcesses().get(0).getOfRecipe(), "recipe should be set to all sub-processes ofRecipe (0)"),
                () -> assertEquals(recipe, recipe.getProcesses().get(1).getOfRecipe(), "recipe should be set to all sub-processes ofRecipe (1)")
        );
    }

    @Test
    @DisplayName("creation - invalid argument throws exception")
    void testCreation_invalidArg() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new RecipeEntity(null), "null name should throw exception"),
                () -> assertThrows(IllegalArgumentException.class, () -> new RecipeEntity(""), "empty name should throw exception"),
                () -> assertThrows(IllegalArgumentException.class, () -> new RecipeEntity("   "), "blank name should throw exception")
        );
    }

    @Test
    @DisplayName("creation - nullable argument allows creation when null")
    void testCreation_nullableArg() {
        assertDoesNotThrow(() -> new RecipeEntity(name, null), "null components list should not throw exception");
    }

    @Test
    @DisplayName("setting processes")
    void setProcesses() {
        RecipeEntity recipe = new RecipeEntity(name, null);
        processes.add(new RecipeProcessEntity(1, "mix together"));
        processes.add(new RecipeProcessEntity(2, "bake"));

        recipe.setProcesses(processes);

        assertAll(
                () -> assertEquals(processes, recipe.getProcesses(), "processes list should be equal"),
                () -> assertEquals(recipe, recipe.getProcesses().get(0).getOfRecipe(), "recipe should be set to all seb-processes ofRecipe (0)"),
                () -> assertEquals(recipe, recipe.getProcesses().get(1).getOfRecipe(), "recipe should be set to all sub-processes ofRecipe (1)")
        );
    }
}
