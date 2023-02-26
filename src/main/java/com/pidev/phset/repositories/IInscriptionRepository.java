package com.pidev.phset.repositories;

import com.pidev.phset.entities.Inscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInscriptionRepository extends CrudRepository<Inscription,Integer> {
}
