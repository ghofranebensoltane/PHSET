package com.pidev.phset.repositories;

import com.pidev.phset.entities.Evaluation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEvaluationRepository extends CrudRepository<Evaluation, Integer> {
}
