package com.erezshevach.recipebookmaster.data.entity;

import com.erezshevach.recipebookmaster.shared.Uom;
import com.erezshevach.recipebookmaster.shared.Utils;
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

    final Integer sampleSequence = 7;
    final String sampleDescription = "do something";
    RecipeEntity sampleRecipe = new RecipeEntity("recipe");
    List<RecipeComponentEntity> sampleComponents;

    @BeforeEach
    void setUp() {
        sampleComponents = new ArrayList<>();
        sampleComponents.add(new RecipeComponentEntity(100, Uom.G, "butter", "rt"));
        sampleComponents.add(new RecipeComponentEntity(200, Uom.G, "sugar", null));
    }

    @Test
    @DisplayName("creation - with sequence, description and recipe")
    void testCreation_withSeqDescRecipe() {
        RecipeProcessEntity process = new RecipeProcessEntity(sampleSequence, sampleDescription, sampleRecipe);

        assertAll(
                () -> assertEquals(sampleSequence, process.getSequence(), "process creation: sequence should be equal to input"),
                () -> assertEquals(sampleDescription, process.getDescription(), "process creation: description should be equal to input"),
                () -> assertEquals(sampleRecipe, process.getOfRecipe(), "process creation: recipe should be equal to input")
        );
    }

    @Test
    @DisplayName("creation - with components list")
    void testCreation_withComponents() {
        RecipeProcessEntity process = new RecipeProcessEntity(sampleSequence, sampleDescription, sampleComponents, sampleRecipe);

        assertAll(
                () -> assertEquals(sampleSequence, process.getSequence(), "process creation: sequence should be equal to input"),
                () -> assertEquals(sampleDescription, process.getDescription(), "process creation: description should be equal to input"),
                () -> assertTrue(RecipeComponentEntity.compareComponents(sampleComponents.get(0), process.getComponents().get(0)), "process creation: component should be equal to input (0)"),
                () -> assertTrue(RecipeComponentEntity.compareComponents(sampleComponents.get(1), process.getComponents().get(1)), "process creation: component should be equal to input (1)"),
                () -> assertEquals(sampleRecipe, process.getOfRecipe(), "process creation: recipe should be equal to input"),
                () -> assertEquals(process, process.getComponents().get(0).getOfProcess(), "process creation: process should be set to all sub-components ofProcess (0)"),
                () -> assertEquals(process, process.getComponents().get(1).getOfProcess(), "process creation: process should be set to all sub-components ofProcess (1)"),
                () -> assertEquals(sampleRecipe, process.getComponents().get(0).getOfRecipe(), "process creation: recipe should be set to all sub-components ofRecipe (0)"),
                () -> assertEquals(sampleRecipe, process.getComponents().get(1).getOfRecipe(), "process creation: recipe should be set to all sub-components ofRecipe (1)")
        );

    }

    @Test
    @DisplayName("creation - invalid argument throws exception")
    void testCreation_invalidArg() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new RecipeProcessEntity(0, sampleDescription, sampleRecipe), "process creation: 0 or negative sequence should throw exception"),
                () -> assertThrows(IllegalArgumentException.class, () -> new RecipeProcessEntity(sampleSequence, null, sampleRecipe), "process creation: null description should throw exception"),
                () -> assertThrows(IllegalArgumentException.class, () -> new RecipeProcessEntity(sampleSequence, "", sampleRecipe), "process creation: empty description should throw exception"),
                () -> assertThrows(IllegalArgumentException.class, () -> new RecipeProcessEntity(sampleSequence, "   ", sampleRecipe), "process creation: blank description should throw exception")
        );
    }

    @Test
    @DisplayName("creation - nullable argument should allow creation when null")
    void testCreation_nullableArg() {
        assertAll(
                () -> assertDoesNotThrow(() -> new RecipeProcessEntity(sampleSequence, sampleDescription, null), "process creation: null recipe should not throw exception"),
                () -> assertDoesNotThrow(() -> new RecipeProcessEntity(sampleSequence, sampleDescription, null, sampleRecipe), "process creation: null components list should not throw exception")
        );
    }

    @Test
    @DisplayName("setting components")
    void setComponents() {
        RecipeProcessEntity process = new RecipeProcessEntity(sampleSequence, sampleDescription, sampleRecipe);

        process.setComponents(sampleComponents);
        assertAll(
                () -> assertEquals(sampleComponents, process.getComponents(), "process setters: components list should be equal to input"),
                () -> assertEquals(sampleComponents.size(), process.getComponents().size(), "process setters: components list size should be equal to input"),
                () -> assertTrue(RecipeComponentEntity.compareComponents(sampleComponents.get(0), process.getComponents().get(0)), "process setters: component should be equal to input (0)"),
                () -> assertTrue(RecipeComponentEntity.compareComponents(sampleComponents.get(1), process.getComponents().get(1)), "process setters: component should be equal to input (1)"),
                () -> assertEquals(sampleRecipe, process.getComponents().get(0).getOfRecipe(), "process setters: recipe should be set to all sub-components ofRecipe (0)"),
                () -> assertEquals(sampleRecipe, process.getComponents().get(1).getOfRecipe(), "process setters: recipe should be set to all sub-components ofRecipe (1)"),
                () -> assertEquals(process, process.getComponents().get(0).getOfProcess(), "process setters: process should be set to all sub-components ofProcess (0)"),
                () -> assertEquals(process, process.getComponents().get(1).getOfProcess(), "process setters: process should be set to all sub-components ofProcess (1)")
        );
    }

    @Test
    @DisplayName("setting ofRecipe")
    void setOfRecipe() {
        RecipeProcessEntity process = new RecipeProcessEntity(sampleSequence, sampleDescription, sampleComponents, null);

        process.setOfRecipe(sampleRecipe);

        assertAll(
                () -> assertEquals(sampleRecipe, process.getOfRecipe(), "recipe should be equal"),
                () -> assertEquals(sampleRecipe, process.getComponents().get(0).getOfRecipe(), "process setters: recipe should be set to all sub-components ofRecipe (0)"),
                () -> assertEquals(sampleRecipe, process.getComponents().get(1).getOfRecipe(), "process setters: recipe should be set to all sub-components ofRecipe (1)")
        );
    }

    @Test
    @DisplayName("similar - true similarity to other entity")
    void similar_true_entity() {
        RecipeProcessEntity process = new RecipeProcessEntity(sampleSequence, sampleDescription, sampleComponents, sampleRecipe);
        RecipeProcessEntity other;

        other = new RecipeProcessEntity(sampleSequence, sampleDescription, sampleComponents, sampleRecipe);
        assertTrue(RecipeProcessEntity.compareProcesses(process, other), "process similarity: similar sequence, description and components should be considered similar");

        other = new RecipeProcessEntity(sampleSequence, sampleDescription, sampleComponents, null);
        assertTrue(RecipeProcessEntity.compareProcesses(process, other), "process similarity: different ofRecipe should not affect similarity");
    }

    @Test
    @DisplayName("similar - false similarity to other entity")
    void similar_false_entity() {
        RecipeProcessEntity process = new RecipeProcessEntity(sampleSequence, sampleDescription, sampleComponents, sampleRecipe);
        RecipeProcessEntity other;

        other = new RecipeProcessEntity(15 , sampleDescription, sampleComponents, sampleRecipe);
        assertFalse(RecipeProcessEntity.compareProcesses(process, other), "process similarity: different sequence should not be considered similar");

        other = new RecipeProcessEntity(sampleSequence, "other description", sampleComponents, sampleRecipe);
        assertFalse(RecipeProcessEntity.compareProcesses(process, other), "process similarity: different description should not be considered similar");

        other = new RecipeProcessEntity(sampleSequence, sampleDescription, null, sampleRecipe);
        assertFalse(RecipeProcessEntity.compareProcesses(process, other), "process similarity: different components list should not be considered similar (null)");

        List<RecipeComponentEntity> otherComponents = new ArrayList<>();
        otherComponents.add(new RecipeComponentEntity(100, Uom.G, "butter", "rt"));
        otherComponents.add(new RecipeComponentEntity(200, Uom.G, "sugar", null));
        otherComponents.add(new RecipeComponentEntity(300, Uom.G, "flour", null));
        other = new RecipeProcessEntity(sampleSequence, sampleDescription, otherComponents, sampleRecipe);
        assertFalse(RecipeProcessEntity.compareProcesses(process, other), "process similarity: different components list should not be considered similar (additional record)");

        otherComponents.remove(2);
        otherComponents.get(0).setIngredient("salt");
        other = new RecipeProcessEntity(sampleSequence, sampleDescription, otherComponents, sampleRecipe);
        assertFalse(RecipeProcessEntity.compareProcesses(process, other), "process similarity: different components list should not be considered similar (one different ingredient)");

        otherComponents.remove(0);
        other = new RecipeProcessEntity(sampleSequence, sampleDescription, otherComponents, sampleRecipe);
        assertFalse(RecipeProcessEntity.compareProcesses(process, other), "process similarity: different components list should not be considered similar (less records)");
    }

    @Test
    @DisplayName("similar - true similarity to other dto")
    void similar_true_dto() {
        RecipeProcessEntity process;
        RecipeProcessDto other = new RecipeProcessDto();
        other.setSequence(sampleSequence);
        other.setDescription(sampleDescription);
        ModelMapper mapper = new ModelMapper();
        List<RecipeComponentDto> componentsDtos = new ArrayList<>();
        componentsDtos.add(mapper.map(new RecipeComponentEntity(100, Uom.G, "butter", "rt"), RecipeComponentDto.class));
        componentsDtos.add(mapper.map(new RecipeComponentEntity(200, Uom.G, "sugar", null), RecipeComponentDto.class));
        other.setComponents(componentsDtos);

        process = new RecipeProcessEntity(sampleSequence, sampleDescription, sampleComponents, sampleRecipe);
        assertTrue(Utils.compareProcesses(process, other), "process similarity: similar sequence, description and components should be considered similar");

        process = new RecipeProcessEntity(sampleSequence, sampleDescription, sampleComponents, null);
        assertTrue(Utils.compareProcesses(process, other), "process similarity: different ofRecipe should not affect similarity");
    }

    @Test
    @DisplayName("similar - false similarity to other dto")
    void similar_false_dto() {
        RecipeProcessEntity process;
        RecipeProcessDto other = new RecipeProcessDto();
        other.setSequence(sampleSequence);
        other.setDescription(sampleDescription);
        ModelMapper mapper = new ModelMapper();
        List<RecipeComponentDto> componentsDtos = new ArrayList<>();
        componentsDtos.add(mapper.map(new RecipeComponentEntity(100, Uom.G, "butter", "rt"), RecipeComponentDto.class));
        componentsDtos.add(mapper.map(new RecipeComponentEntity(200, Uom.G, "sugar", null), RecipeComponentDto.class));
        other.setComponents(componentsDtos);

        process = new RecipeProcessEntity(15 , sampleDescription, sampleComponents, sampleRecipe);
        assertFalse(Utils.compareProcesses(process, other), "process similarity: different sequence should not be considered similar");

        process = new RecipeProcessEntity(sampleSequence, "other description", sampleComponents, sampleRecipe);
        assertFalse(Utils.compareProcesses(process, other), "process similarity: different description should not be considered similar");

        process = new RecipeProcessEntity(sampleSequence, sampleDescription, null, sampleRecipe);
        assertFalse(Utils.compareProcesses(process, other), "process similarity: different components list should not be considered similar");

        sampleComponents.get(0).setIngredient("salt");
        process = new RecipeProcessEntity(sampleSequence, sampleDescription, sampleComponents, sampleRecipe);
        assertFalse(Utils.compareProcesses(process, other), "process similarity: different components list should not be considered similar");

        sampleComponents.remove(0);
        process = new RecipeProcessEntity(sampleSequence, sampleDescription, sampleComponents, sampleRecipe);
        assertFalse(Utils.compareProcesses(process, other), "process similarity: different components list should not be considered similar");
    }
}