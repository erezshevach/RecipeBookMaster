package com.erezshevach.recipebookmaster.service.impl;

import com.erezshevach.recipebookmaster.data.reposirory.RecipeProcessRepository;
import com.erezshevach.recipebookmaster.data.entity.RecipeEntity;
import com.erezshevach.recipebookmaster.data.entity.RecipeProcessEntity;
import com.erezshevach.recipebookmaster.exceptions.RecipeException;
import com.erezshevach.recipebookmaster.service.RecipeProcessService;
import com.erezshevach.recipebookmaster.exceptions.ErrorMessages;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RecipeProcessServiceImpl implements RecipeProcessService {
    RecipeProcessRepository processRepository;

    @Autowired
    public RecipeProcessServiceImpl(RecipeProcessRepository processRepository) {
        this.processRepository = processRepository;
    }

    @Override
    @Transactional
    public void deleteProcessByPid(String pid) {
        processRepository.delete(getProcessEntityByPid(pid));
    }

    @Override
    @Transactional
    public void deleteProcessesByOfRecipe(RecipeEntity recipe) {
        processRepository.deleteAllByOfRecipe(recipe);
    }

    private RecipeProcessEntity getProcessEntityByPid(String pid) {
        pid = validatePid(pid);
        RecipeProcessEntity processEntity = processRepository.findByProcessPid(pid);
        if (processEntity == null)
            throw new RecipeException(pid, RecipeProcessEntity.class.getName(), ErrorMessages.NO_RECORD_FOUND.getMessage());

        return processEntity;
    }

    private String validatePid(String pid) {
        if (pid == null || pid.isEmpty() || pid.isBlank())
            throw new IllegalArgumentException("valid pID must be provided");
        return pid.trim();
    }
}
