package com.erezshevach.recipebookmaster.io.entity;

import com.erezshevach.recipebookmaster.Uom;
import com.erezshevach.recipebookmaster.shared.dto.RecipeComponentDto;
import com.erezshevach.recipebookmaster.shared.dto.RecipeProcessDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeProcessEntityTest {

    final Integer sequence = 7;
    final String description = "do something";
    RecipeEntity recipe = new RecipeEntity("recipe");
    List<RecipeComponentEntity> components;

    @BeforeEach
    void setUp() {
        components = new ArrayList<>();
        components.add(new RecipeComponentEntity(100, Uom.G, "butter", "rt"));
        components.add(new RecipeComponentEntity(200, Uom.G, "sugar", null));
    }

    @Test
    @DisplayName("creation with sequence, description and recipe")
    void testCreation_withSeqDescRecipe() {
        RecipeProcessEntity process = new RecipeProcessEntity(sequence, description, recipe);

        assertAll(
                () -> assertEquals(sequence, process.getSequence(), "sequence should be equal to input"),
                () -> assertEquals(description, process.getDescription(), "description should be equal to input"),
                () -> assertEquals(recipe, process.getOfRecipe(), "recipe should be equal to input")
        );
    }

    @Test
    @DisplayName("creation with components list")
    void testCreation_withComponents() {
        RecipeProcessEntity process = new RecipeProcessEntity(sequence, description, components, recipe);

        assertAll(
                () -> assertEquals(sequence, process.getSequence(), "sequence should be equal to input"),
                () -> assertEquals(description, process.getDescription(), "description should be equal to input"),
                () -> assertTrue(components.get(0).similar(process.getComponents().get(0)), "component should be equal to input (0)"),
                () -> assertTrue(components.get(1).similar(process.getComponents().get(1)), "component should be equal to input (1)"),
                () -> assertEquals(recipe, process.getOfRecipe(), "recipe should be equal to input"),
                () -> assertEquals(process, process.getComponents().get(0).getOfProcess(), "process should be set to all sub-components ofProcess (0)"),
                () -> assertEquals(process, process.getComponents().get(1).getOfProcess(), "process should be set to all sub-components ofProcess (1)"),
                () -> assertEquals(recipe, process.getComponents().get(0).getOfRecipe(), "recipe should be set to all sub-components ofRecipe (0)"),
                () -> assertEquals(recipe, process.getComponents().get(1).getOfRecipe(), "recipe should be set to all sub-components ofRecipe (1)")
        );

    }

    @Test
    @DisplayName("Invalid argument throws exception")
    void testCreation_invalidArg() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new RecipeProcessEntity(0, description, recipe), "0 or negative sequence should throw exception"),
                () -> assertThrows(IllegalArgumentException.class, () -> new RecipeProcessEntity(sequence, null, recipe), "null description should throw exception"),
                () -> assertThrows(IllegalArgumentException.class, () -> new RecipeProcessEntity(sequence, "", recipe), "empty description should throw exception"),
                () -> assertThrows(IllegalArgumentException.class, () -> new RecipeProcessEntity(sequence, "   ", recipe), "blank description should throw exception")
        );
    }

    @Test
    @DisplayName("nullable argument should allow creation when null")
    void testCreation_nullableArg() {
        assertAll(
                () -> assertDoesNotThrow(() -> new RecipeProcessEntity(sequence, description, null), "null recipe should not throw exception"),
                () -> assertDoesNotThrow(() -> new RecipeProcessEntity(sequence, description, null, recipe), "null components list should not throw exception")
        );
    }

    @Test
    @DisplayName("setting components")
    void setComponents() {
        RecipeProcessEntity process = new RecipeProcessEntity(sequence, description, recipe);

        process.setComponents(components);
        assertAll(
                () -> assertEquals(components, process.getComponents(), "components list should be equal to input"),
                () -> assertEquals(components.size(), process.getComponents().size(), "components list size should be equal to input"),
                () -> assertTrue(components.get(0).similar(process.getComponents().get(0)), "component should be equal to input (0)"),
                () -> assertTrue(components.get(1).similar(process.getComponents().get(1)), "component should be equal to input (1)"),
                () -> assertEquals(recipe, process.getComponents().get(0).getOfRecipe(), "recipe should be set to all sub-components ofRecipe (0)"),
                () -> assertEquals(recipe, process.getComponents().get(1).getOfRecipe(), "recipe should be set to all sub-components ofRecipe (1)"),
                () -> assertEquals(process, process.getComponents().get(0).getOfProcess(), "process should be set to all sub-components ofProcess (0)"),
                () -> assertEquals(process, process.getComponents().get(1).getOfProcess(), "process should be set to all sub-components ofProcess (1)")
        );
    }

    @Test
    @DisplayName("setting ofRecipe")
    void setOfRecipe() {
        RecipeProcessEntity process = new RecipeProcessEntity(sequence, description, components, null);

        process.setOfRecipe(recipe);

        assertAll(
                () -> assertEquals(recipe, process.getOfRecipe(), "recipe should be equal"),
                () -> assertEquals(recipe, process.getComponents().get(0).getOfRecipe(), "recipe should be set to all sub-components ofRecipe (0)"),
                () -> assertEquals(recipe, process.getComponents().get(1).getOfRecipe(), "recipe should be set to all sub-components ofRecipe (1)")
        );
    }

    @Test
    @DisplayName("similar - true similarity to other entity")
    void similar_true_entity() {
        RecipeProcessEntity process = new RecipeProcessEntity(sequence, description, components, recipe);
        RecipeProcessEntity other;

        other = new RecipeProcessEntity(sequence, description, components, recipe);
        assertTrue(process.similar(other), "similar sequence, description and components should be considered similar");

        other = new RecipeProcessEntity(sequence, description, components, null);
        assertTrue(process.similar(other), "different ofRecipe should not affect similarity");
    }

    @Test
    @DisplayName("similar - false similarity to other entity")
    void similar_false_entity() {
        RecipeProcessEntity process = new RecipeProcessEntity(sequence, description, components, recipe);
        RecipeProcessEntity other;

        other = new RecipeProcessEntity(15 , description, components, recipe);
        assertFalse(process.similar(other), "different sequence should not be considered similar");

        other = new RecipeProcessEntity(sequence, "other description", components, recipe);
        assertFalse(process.similar(other), "different description should not be considered similar");

        other = new RecipeProcessEntity(sequence, description, null, recipe);
        assertFalse(process.similar(other), "different components list should not be considered similar (null)");

        List<RecipeComponentEntity> otherComponents = new ArrayList<>();
        otherComponents.add(new RecipeComponentEntity(100, Uom.G, "butter", "rt"));
        otherComponents.add(new RecipeComponentEntity(200, Uom.G, "sugar", null));
        otherComponents.add(new RecipeComponentEntity(300, Uom.G, "flour", null));
        other = new RecipeProcessEntity(sequence, description, otherComponents, recipe);
        assertFalse(process.similar(other), "different components list should not be considered similar (additional record)");

        otherComponents.remove(2);
        otherComponents.get(0).setIngredient("salt");
        other = new RecipeProcessEntity(sequence, description, otherComponents, recipe);
        assertFalse(process.similar(other), "different components list should not be considered similar (one different ingredient)");

        otherComponents.remove(0);
        other = new RecipeProcessEntity(sequence, description, otherComponents, recipe);
        assertFalse(process.similar(other), "different components list should not be considered similar (less records)");
    }

    @Test
    @DisplayName("similar - true similarity to other dto")
    void similar_true_dto() {
        RecipeProcessEntity process;
        RecipeProcessDto other = new RecipeProcessDto();
        other.setSequence(sequence);
        other.setDescription(description);
        ModelMapper mapper = new ModelMapper();
        List<RecipeComponentDto> componentsDtos = new ArrayList<>();
        componentsDtos.add(mapper.map(new RecipeComponentEntity(100, Uom.G, "butter", "rt"), RecipeComponentDto.class));
        componentsDtos.add(mapper.map(new RecipeComponentEntity(200, Uom.G, "sugar", null), RecipeComponentDto.class));
        other.setComponents(componentsDtos);

        process = new RecipeProcessEntity(sequence, description, components, recipe);
        assertTrue(process.similar(other), "similar sequence, description and components should be considered similar");

        process = new RecipeProcessEntity(sequence, description, components, null);
        assertTrue(process.similar(other), "different ofRecipe should not affect similarity");
    }

    @Test
    @DisplayName("similar - false similarity to other dto")
    void similar_false_dto() {
        RecipeProcessEntity process;
        RecipeProcessDto other = new RecipeProcessDto();
        other.setSequence(sequence);
        other.setDescription(description);
        ModelMapper mapper = new ModelMapper();
        List<RecipeComponentDto> componentsDtos = new ArrayList<>();
        componentsDtos.add(mapper.map(new RecipeComponentEntity(100, Uom.G, "butter", "rt"), RecipeComponentDto.class));
        componentsDtos.add(mapper.map(new RecipeComponentEntity(200, Uom.G, "sugar", null), RecipeComponentDto.class));
        other.setComponents(componentsDtos);

        process = new RecipeProcessEntity(15 , description, components, recipe);
        assertFalse(process.similar(other), "different sequence should not be considered similar");

        process = new RecipeProcessEntity(sequence, "other description", components, recipe);
        assertFalse(process.similar(other), "different description should not be considered similar");

        process = new RecipeProcessEntity(sequence, description, null, recipe);
        assertFalse(process.similar(other), "different components list should not be considered similar");

        components.get(0).setIngredient("salt");
        process = new RecipeProcessEntity(sequence, description, components, recipe);
        assertFalse(process.similar(other), "different components list should not be considered similar");

        components.remove(0);
        process = new RecipeProcessEntity(sequence, description, components, recipe);
        assertFalse(process.similar(other), "different components list should not be considered similar");
    }
}