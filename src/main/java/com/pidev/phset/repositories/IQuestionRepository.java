package com.pidev.phset.repositories;

import com.pidev.phset.entities.Question;
import com.pidev.phset.entities.QuestionLevel;
import com.pidev.phset.entities.TypeTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IQuestionRepository extends CrudRepository<Question,Integer> {
    Question findByNameQuestion(String name);

    List<Question> findByTypeTestAndQuestionLevel(TypeTest a,QuestionLevel b);
    List<Question> findByTypeTest(TypeTest a);


}
