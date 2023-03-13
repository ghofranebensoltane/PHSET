package com.pidev.phset.repositories;

import com.pidev.phset.entities.Interview;
import com.pidev.phset.entities.MCQ;
import com.pidev.phset.entities.TypeTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IMCQRepository extends CrudRepository<MCQ,Integer> {

    @Query("select m from MCQ m where m.title = ?1 or m.score = ?2 or m.typeTest = ?3 or m.interview = ?4")
    List<MCQ> findByTitleOrScoreOrTypeTestOrInterview(String a, Float b, TypeTest c, Interview d);

}
