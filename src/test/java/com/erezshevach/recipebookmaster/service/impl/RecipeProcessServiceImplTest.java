package com.erezshevach.recipebookmaster.service.impl;

import com.erezshevach.recipebookmaster.RecipeProcessRepository;
import com.erezshevach.recipebookmaster.recipebookmaster.exceptions.RecipeException;
import com.erezshevach.recipebookmaster.ui.model.response.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class RecipeProcessServiceImplTest {
    @InjectMocks
    RecipeProcessServiceImpl service;
    @Mock
    RecipeProcessRepository repository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("delete process by pID - no pID provided")
    void deleteProcessByPid_noPid() {

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> service.deleteProcessByPid(""), "IllegalArgumentException should be thrown when provided pID is empty"),
                () -> assertThrows(IllegalArgumentException.class, () -> service.deleteProcessByPid("   "), "IllegalArgumentException should be thrown when provided pID is blank"),
                () -> assertThrows(IllegalArgumentException.class, () -> service.deleteProcessByPid(null), "IllegalArgumentException should be thrown when provided pID is null")
        );
    }

    @Test
    @DisplayName("delete process by pID - throws exception when no record found")
    void deleteProcessByPid_noRecordFound() {
        when(repository.findByProcessPid(anyString())).thenReturn(null);

        RecipeException ex = assertThrows(RecipeException.class, () -> service.deleteProcessByPid("non-existing pID"), "RecipeException should be thrown when no record found - non existing");
        assertEquals(ErrorMessages.NO_RECORD_FOUND.getMessage(), ex.getMessage());
    }

}