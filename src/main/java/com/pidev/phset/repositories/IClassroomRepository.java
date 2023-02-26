package com.pidev.phset.repositories;

import com.pidev.phset.entities.Classroom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClassroomRepository extends CrudRepository<Classroom,Integer> {
}
