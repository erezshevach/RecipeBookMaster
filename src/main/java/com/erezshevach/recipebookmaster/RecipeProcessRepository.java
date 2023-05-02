package com.erezshevach.recipebookmaster;

import com.erezshevach.recipebookmaster.io.entity.RecipeEntity;
import com.erezshevach.recipebookmaster.io.entity.RecipeProcessEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RecipeProcessRepository extends CrudRepository<RecipeProcessEntity, Long> {

    RecipeProcessEntity findByProcessPid(String pid);
    List<RecipeProcessEntity> findAllByOfRecipe(RecipeEntity recipe);
    void deleteAllByOfRecipe(RecipeEntity recipe);

}
