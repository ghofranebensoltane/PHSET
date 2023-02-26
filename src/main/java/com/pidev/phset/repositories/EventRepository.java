package com.pidev.phset.repositories;

import com.pidev.phset.entities.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {
}