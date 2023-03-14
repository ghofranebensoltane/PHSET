package com.pidev.phset.repositories;

import com.pidev.phset.entities.Interview;
import com.pidev.phset.entities.MCQ;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInterviewRepository extends CrudRepository<Interview,Integer> {


}
