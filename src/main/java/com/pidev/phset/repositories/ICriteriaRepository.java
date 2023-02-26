package com.pidev.phset.repositories;

import com.pidev.phset.entities.Criteria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICriteriaRepository extends CrudRepository<Criteria,Integer> {
}
