package com.erezshevach.recipebookmaster.data.entity;

import com.erezshevach.recipebookmaster.shared.Uom;
import com.erezshevach.recipebookmaster.shared.dto.RecipeComponentDto;
import com.erezshevach.recipebookmaster.shared.dto.RecipeDto;
import com.erezshevach.recipebookmaster.shared.dto.RecipeProcessDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

class RecipeComponentEntityTest {

    final double sampleQuantity = 152.6;
    final Uom sampleUom = Uom.G;
    final String sampleIngredient = "butter";
    final String sampleState = "cold";
    final RecipeEntity sampleRecipe = new RecipeEntity("sable");
    final RecipeProcessEntity sampleProcess = new RecipeProcessEntity(1, "mix", sampleRecipe);
    final RecipeComponentEntity sampleComponent = new RecipeComponentEntity(sampleQuantity, sampleUom, sampleIngredient, sampleState, sampleProcess, sampleRecipe);
    final ModelMapper mapper = new ModelMapper();


    @Test
    @DisplayName("creation - with all fields")
    void testCreation() {
        RecipeComponentEntity createdComponent = new RecipeComponentEntity(sampleQuantity, sampleUom, sampleIngredient, sampleState, sampleProcess, sampleRecipe);

        assertAll(
                () -> assertEquals(createdComponent.getQuantity(), sampleQuantity, "component creation: quantity should be equal to input"),
                () -> assertEquals(createdComponent.getUom(), sampleUom, "component creation: uom should be equal to input"),
                () -> assertEquals(createdComponent.getIngredient(), sampleIngredient, "component creation: ingredient should be equal to input"),
                () -> assertEquals(createdComponent.getState(), sampleState, "component creation: state should be equal to input"),
                () -> assertEquals(createdComponent.getOfProcess(), sampleProcess, "component creation: process should be equal to input"),
                () -> assertEquals(createdComponent.getOfRecipe(), sampleRecipe, "component creation: recipe should be equal to input")
        );
    }

    @Test
    @DisplayName("creation - invalid argument throws exception")
    void testCreation_invalidArg() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new RecipeComponentEntity(0, sampleUom, sampleIngredient, sampleState, sampleProcess, sampleRecipe), "component creation: 0 quantity should throw exception"),
                () -> assertThrows(IllegalArgumentException.class, () -> new RecipeComponentEntity(-100, sampleUom, sampleIngredient, sampleState, sampleProcess, sampleRecipe), "component creation: negative quantity should throw exception"),
                () -> assertThrows(IllegalArgumentException.class, () -> new RecipeComponentEntity(sampleQuantity, null, sampleIngredient, sampleState, sampleProcess, sampleRecipe), "component creation: null UOM should throw exception"),
                () -> assertThrows(IllegalArgumentException.class, () -> new RecipeComponentEntity(sampleQuantity, sampleUom, "", sampleState, sampleProcess, sampleRecipe), "component creation: invalid ingredient should throw exception")
        );
    }

    @Test
    @DisplayName("creation - nullable argument should allow creation when null")
    void testCreation_nullableArg() {
        assertAll(
                () -> assertDoesNotThrow(() -> new RecipeComponentEntity(sampleQuantity, sampleUom, sampleIngredient, null, sampleProcess, sampleRecipe), "component creation: null state should not throw exception"),
                () -> assertDoesNotThrow(() -> new RecipeComponentEntity(sampleQuantity, sampleUom, sampleIngredient, sampleState, null, sampleRecipe), "component creation: null process should not throw exception"),
                () -> assertDoesNotThrow(() -> new RecipeComponentEntity(sampleQuantity, sampleUom, sampleIngredient, sampleState, sampleProcess, null), "component creation: null recipe should not throw exception")
        );
    }

    @Test
    @DisplayName("similar - true similarity to other entity")
    void similar_true_entity() {
        RecipeComponentEntity other = new RecipeComponentEntity(sampleQuantity, sampleUom, sampleIngredient, sampleState);

        assertTrue(sampleComponent.similar(other), "component similarity: similar quantity, uom, ingredient and state should be considered similar");

        other.setOfProcess(null); //changing process
        assertTrue(sampleComponent.similar(other), "component similarity: different ofProcess should not affect similarity");
        other.setOfProcess(sampleProcess); //matching process

        other.setOfRecipe(null); //changing recipe
        assertTrue(sampleComponent.similar(other), "component similarity: different ofRecipe should not affect similarity");
    }

    @Test
    @DisplayName("similar - false similarity to other entity")
    void similar_false_entity() {
        RecipeComponentEntity other = new RecipeComponentEntity(sampleQuantity, sampleUom, sampleIngredient, sampleState);

        other.setQuantity(sampleQuantity+1); //changing quantity
        assertFalse(sampleComponent.similar(other), "component similarity: different quantities should not be considered similar");
        other.setQuantity(sampleQuantity); //matching quantity

        other.setUom(Uom.UNIT); //changing uom
        assertFalse(sampleComponent.similar(other), "component similarity: different uom should not be considered similar");
        other.setUom(sampleUom); //matching uom

        other.setIngredient("milk"); //changing ingredient
        assertFalse(sampleComponent.similar(other), "component similarity: different ingredients should not be considered similar");
        other.setIngredient(sampleIngredient); //matching ingredient

        other.setState("different "+sampleState); //changing state
        assertFalse(sampleComponent.similar(other), "component similarity: different states should not be considered similar");
        other.setState(sampleState); //matching state

        other.setState(null); //changing state
        assertFalse(sampleComponent.similar(other), "component similarity: different (null) states should not be considered similar");
    }

    @Test
    @DisplayName("similar - true similarity to other dto")
    void similar_true_dto() {
        RecipeComponentDto other = new RecipeComponentDto();
        other.setQuantity(sampleQuantity);
        other.setUom(sampleUom);
        other.setIngredient(sampleIngredient);
        other.setState(sampleState);
        other.setOfRecipe(mapper.map(sampleRecipe, RecipeDto.class));
        other.setOfProcess(mapper.map(sampleProcess, RecipeProcessDto.class));

        assertTrue(sampleComponent.similar(other), "component similarity to dto: similar quantity, uom, ingredient and state should be considered similar");

        other.setOfProcess(null); //changing process
        assertTrue(sampleComponent.similar(other), "component similarity to dto: different ofProcess should not affect similarity");
        other.setOfProcess(mapper.map(sampleProcess, RecipeProcessDto.class)); //matching process

        other.setOfRecipe(null); //changing recipe
        assertTrue(sampleComponent.similar(other), "component similarity to dto: different ofRecipe should not affect similarity");
    }

    @Test
    @DisplayName("similar - false similarity to other dto")
    void similar_false_dto() {
        RecipeComponentDto other = new RecipeComponentDto();
        other.setQuantity(sampleQuantity);
        other.setUom(sampleUom);
        other.setIngredient(sampleIngredient);
        other.setState(sampleState);
        other.setOfRecipe(mapper.map(sampleRecipe, RecipeDto.class));
        other.setOfProcess(mapper.map(sampleProcess, RecipeProcessDto.class));

        other.setQuantity(sampleQuantity+1); //changing quantity
        assertFalse(sampleComponent.similar(other), "component similarity to dto: different quantities should not be considered similar");
        other.setQuantity(sampleQuantity); //matching quantity

        other.setUom(Uom.UNIT); //changing uom
        assertFalse(sampleComponent.similar(other), "component similarity to dto: different uom should not be considered similar");
        other.setUom(sampleUom); //matching uom

        other.setIngredient("milk"); //changing ingredient
        assertFalse(sampleComponent.similar(other), "component similarity to dto: different ingredients should not be considered similar");
        other.setIngredient(sampleIngredient); //matching ingredient

        other.setState("different "+sampleState); //changing state
        assertFalse(sampleComponent.similar(other), "component similarity to dto: different states should not be considered similar");
        other.setState(sampleState); //matching state

        other.setState(null); //changing state
        assertFalse(sampleComponent.similar(other), "component similarity to dto: different states should not be considered similar");
    }
}