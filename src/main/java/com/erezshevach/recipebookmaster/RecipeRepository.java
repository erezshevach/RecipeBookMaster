package com.erezshevach.recipebookmaster;

import com.erezshevach.recipebookmaster.io.entity.RecipeEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<RecipeEntity, Long>, PagingAndSortingRepository<RecipeEntity, Long> {

    RecipeEntity findRecipeByName(String Name);
    List<RecipeEntity> findRecipesByNameContains(Pageable pageable, String partialName);


}
