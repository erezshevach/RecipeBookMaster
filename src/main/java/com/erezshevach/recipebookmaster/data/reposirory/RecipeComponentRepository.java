package com.erezshevach.recipebookmaster.data.reposirory;

import com.erezshevach.recipebookmaster.data.entity.RecipeComponentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecipeComponentRepository extends CrudRepository<RecipeComponentEntity, Long> {

}
