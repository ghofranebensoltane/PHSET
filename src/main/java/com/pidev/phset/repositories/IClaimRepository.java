package com.pidev.phset.repositories;

import com.pidev.phset.entities.Claim;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClaimRepository extends CrudRepository<Claim, Integer> {
}
