package com.erezshevach.recipebookmaster;

import com.erezshevach.recipebookmaster.io.entity.RecipeComponentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecipeComponentRepository extends CrudRepository<RecipeComponentEntity, Long> {

}
