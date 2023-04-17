package com.erezshevach.recipebookmaster.recipebookmaster.exceptions;

import com.erezshevach.recipebookmaster.ui.model.response.RecipeErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class AppExceptionsHandler {
    @ExceptionHandler(value = RecipeException.class)
    public ResponseEntity<Object> handleRecipeException(RecipeException ex, WebRequest request) {
        RecipeErrorMessage error = new RecipeErrorMessage(new Date(), ex.getRecipeName(), ex.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
