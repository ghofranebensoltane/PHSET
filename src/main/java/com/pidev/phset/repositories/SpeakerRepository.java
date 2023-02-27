package com.pidev.phset.repositories;

import com.pidev.phset.entities.Speaker;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Date;

public interface SpeakerRepository extends CrudRepository<Speaker, Integer> {
}