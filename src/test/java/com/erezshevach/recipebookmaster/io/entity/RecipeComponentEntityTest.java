package com.erezshevach.recipebookmaster.io.entity;

import com.erezshevach.recipebookmaster.Uom;
import com.erezshevach.recipebookmaster.shared.dto.RecipeComponentDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RecipeComponentEntityTest {

    final double quantity = 152.6;
    final Uom uom = Uom.G;
    final String ingredient = "butter";
    final String state = "cold";
    final RecipeEntity recipe = new RecipeEntity("sable");
    final RecipeProcessEntity process = new RecipeProcessEntity(1, "mix", recipe);

    @Test
    @DisplayName("creation with all fields")
    void testCreation() {
        RecipeComponentEntity component = new RecipeComponentEntity(quantity, uom, ingredient, state, process, recipe);

        assertAll(
                () -> assertEquals(quantity, component.getQuantity(), "quantity should be equal to input"),
                () -> assertEquals(uom, component.getUom(), "uom should be equal to input"),
                () -> assertEquals(ingredient, component.getIngredient(), "ingredient should be equal to input"),
                () -> assertEquals(state, component.getState(), "state should be equal to input"),
                () -> assertEquals(process, component.getOfProcess(), "process should be equal to input"),
                () -> assertEquals(recipe, component.getOfRecipe(), "recipe should be equal to input")
        );
    }

    @Test
    @DisplayName("Invalid argument throws exception")
    void testCreation_invalidArg() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new RecipeComponentEntity(0, uom, ingredient, state, process, recipe), "0 or negative quantity should throw exception"),
                () -> assertThrows(IllegalArgumentException.class, () -> new RecipeComponentEntity(quantity, null, ingredient, state, process, recipe), "null UOM should throw exception"),
                () -> assertThrows(IllegalArgumentException.class, () -> new RecipeComponentEntity(quantity, uom, "", state, process, recipe), "invalid ingredient should throw exception")
        );
    }

    @Test
    @DisplayName("nullable argument should allow creation when null")
    void testCreation_nullableArg() {
        assertAll(
                () -> assertDoesNotThrow(() -> new RecipeComponentEntity(quantity, uom, ingredient, null, process, recipe), "null state should not throw exception"),
                () -> assertDoesNotThrow(() -> new RecipeComponentEntity(quantity, uom, ingredient, state, null, recipe), "null process should not throw exception"),
                () -> assertDoesNotThrow(() -> new RecipeComponentEntity(quantity, uom, ingredient, state, process, null), "null recipe should not throw exception")
        );
    }

    @Test
    @DisplayName("similar - true similarity to other entity")
    void similar_true_entity() {
        RecipeComponentEntity component = new RecipeComponentEntity(quantity, uom, ingredient, state);
        RecipeComponentEntity other;

        other = new RecipeComponentEntity(quantity, uom, ingredient, state, null, null);
        assertTrue(component.similar(other), "similar quantity, uom, ingredient and state should be considered similar");

        other = new RecipeComponentEntity(quantity, uom, ingredient, state, new RecipeProcessEntity(), null);
        assertTrue(component.similar(other), "different ofProcess should not affect similarity");

        other = new RecipeComponentEntity(quantity, uom, ingredient, state, null, new RecipeEntity("recipe"));
        assertTrue(component.similar(other), "different ofRecipe should not affect similarity");
    }

    @Test
    @DisplayName("similar - false similarity to other entity")
    void similar_false_entity() {
        RecipeComponentEntity component = new RecipeComponentEntity(quantity, uom, ingredient, state);
        RecipeComponentEntity other;

        other = new RecipeComponentEntity(235, uom, ingredient, state);
        assertFalse(component.similar(other), "different quantities should not be considered similar");

        other = new RecipeComponentEntity(quantity, Uom.UNIT, ingredient, state);
        assertFalse(component.similar(other), "different uom should not be considered similar");

        other = new RecipeComponentEntity(quantity, uom, "milk", state);
        assertFalse(component.similar(other), "different ingredients should not be considered similar");

        other = new RecipeComponentEntity(quantity, uom, ingredient, "boiling");
        assertFalse(component.similar(other), "different states should not be considered similar");

        other = new RecipeComponentEntity(quantity, uom, ingredient, null);
        assertFalse(component.similar(other), "different states should not be considered similar");
    }

    @Test
    @DisplayName("similar - true similarity to other dto")
    void similar_true_dto() {
        RecipeComponentEntity component;
        RecipeComponentDto other = new RecipeComponentDto();
        other.setQuantity(quantity);
        other.setUom(uom);
        other.setIngredient(ingredient);
        other.setState(state);

        component = new RecipeComponentEntity(quantity, uom, ingredient, state, null, null);
        assertTrue(component.similar(other), "similar quantity, uom, ingredient and state should be considered similar");

        component = new RecipeComponentEntity(quantity, uom, ingredient, state, new RecipeProcessEntity(), null);
        assertTrue(component.similar(other), "different ofProcess should not affect similarity");

        component = new RecipeComponentEntity(quantity, uom, ingredient, state, null, new RecipeEntity("recipe"));
        assertTrue(component.similar(other), "different ofRecipe should not affect similarity");
    }

    @Test
    @DisplayName("similar - false similarity to other dto")
    void similar_false_dto() {
        RecipeComponentEntity component;
        RecipeComponentDto other = new RecipeComponentDto();
        other.setQuantity(quantity);
        other.setUom(uom);
        other.setIngredient(ingredient);
        other.setState(state);

        component = new RecipeComponentEntity(235, uom, ingredient, state);
        assertFalse(component.similar(other), "different quantities should not be considered similar");

        component = new RecipeComponentEntity(quantity, Uom.UNIT, ingredient, state);
        assertFalse(component.similar(other), "different uom should not be considered similar");

        component = new RecipeComponentEntity(quantity, uom, "milk", state);
        assertFalse(component.similar(other), "different ingredients should not be considered similar");

        component = new RecipeComponentEntity(quantity, uom, ingredient, "boiling");
        assertFalse(component.similar(other), "different states should not be considered similar");

        component = new RecipeComponentEntity(quantity, uom, ingredient, null);
        assertFalse(component.similar(other), "different states should not be considered similar");
    }
}