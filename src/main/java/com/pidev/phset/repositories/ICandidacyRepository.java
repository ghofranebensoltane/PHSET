package com.pidev.phset.repositories;

import com.pidev.phset.entities.Candidacy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICandidacyRepository extends CrudRepository<Candidacy,Integer> {
}
