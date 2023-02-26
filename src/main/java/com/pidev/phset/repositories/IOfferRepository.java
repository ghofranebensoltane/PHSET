package com.pidev.phset.repositories;

import com.pidev.phset.entities.Offer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOfferRepository extends CrudRepository<Offer,Integer> {
}
