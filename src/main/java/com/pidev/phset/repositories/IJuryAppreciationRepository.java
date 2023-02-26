package com.pidev.phset.repositories;

import com.pidev.phset.entities.JuryAppreciation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJuryAppreciationRepository extends CrudRepository<JuryAppreciation,Integer> {
}
