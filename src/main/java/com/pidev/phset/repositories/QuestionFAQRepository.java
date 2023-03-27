package com.pidev.phset.repositories;

import com.pidev.phset.entities.QuestionFAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionFAQRepository extends JpaRepository<QuestionFAQ,Integer> {


}
