package com.pidev.phset.repositories;

import com.pidev.phset.entities.MCQ;
import org.springframework.data.repository.CrudRepository;

public interface IMCQRepository extends CrudRepository<MCQ,Integer> {
}
