package com.pidev.phset.repositories;

import com.pidev.phset.entities.GridEvaluation;
import com.pidev.phset.entities.Interview;
import com.pidev.phset.entities.TypeGrid;
import com.pidev.phset.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;

@Repository
public interface IGridEvaluationRepository extends CrudRepository<GridEvaluation,Integer> {

}
