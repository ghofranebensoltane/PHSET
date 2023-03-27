package com.pidev.phset.repositories;

import com.pidev.phset.entities.Exam;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends CrudRepository<Exam,Integer> {
}
