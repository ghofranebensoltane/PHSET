package com.pidev.phset.repositories;

import com.pidev.phset.entities.Decission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDecissionRepository extends CrudRepository<Decission, Integer> {
}
