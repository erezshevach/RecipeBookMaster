package com.erezshevach.recipebookmaster;

import com.erezshevach.recipebookmaster.io.entity.RecipeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<RecipeEntity, Long> {

    List<RecipeEntity> getRecipeByName(String Name);

    List<String> findNamesByNameContains(String partialName);

    boolean existsRecipeByName(String name);

}