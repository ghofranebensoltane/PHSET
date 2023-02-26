package com.pidev.phset.repositories;

import com.pidev.phset.entities.Question;
import org.springframework.data.repository.CrudRepository;

public interface IQuestionRepository extends CrudRepository<Question,Integer> {
}
