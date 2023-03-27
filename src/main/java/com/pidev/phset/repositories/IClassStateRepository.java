package com.pidev.phset.repositories;

import com.pidev.phset.entities.ClassState;
import com.pidev.phset.entities.Classroom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface IClassStateRepository extends CrudRepository<ClassState, Integer> {

    Set<ClassState> getAllByAvailabilityIsTrueAndDateEquals(LocalDateTime date);

    List<ClassState> getAllByClassroomOrderByDateDesc(Classroom classroom);
}
