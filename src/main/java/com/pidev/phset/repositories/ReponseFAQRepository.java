package com.pidev.phset.repositories;

import com.pidev.phset.entities.ReponseFAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReponseFAQRepository extends JpaRepository<ReponseFAQ,Integer> {


}
