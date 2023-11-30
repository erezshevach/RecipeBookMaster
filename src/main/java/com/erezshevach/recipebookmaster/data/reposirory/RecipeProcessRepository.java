package com.erezshevach.recipebookmaster.data.reposirory;

import com.erezshevach.recipebookmaster.data.entity.RecipeEntity;
import com.erezshevach.recipebookmaster.data.entity.RecipeProcessEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RecipeProcessRepository extends CrudRepository<RecipeProcessEntity, Long> {

    RecipeProcessEntity findByProcessPid(String pid);
    List<RecipeProcessEntity> findAllByOfRecipe(RecipeEntity recipe);
    void deleteAllByOfRecipe(RecipeEntity recipe);

}
