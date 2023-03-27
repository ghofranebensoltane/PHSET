package com.pidev.phset.repositories;

import com.pidev.phset.entities.Classroom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface IClassroomRepository extends CrudRepository<Classroom,Integer> {


    @Query("SELECT c FROM Classroom c WHERE (SELECT cs.availability FROM ClassState cs WHERE cs.date = :date) is true ")
    Classroom findAvailableRoom(@Param("date") LocalDateTime date);

    @Query("select c from Classroom c ")
    Set<Classroom> getAllClassroom();

}
