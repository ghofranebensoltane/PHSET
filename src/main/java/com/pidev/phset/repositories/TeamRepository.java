package com.pidev.phset.repositories;

import com.pidev.phset.entities.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Integer> {
}