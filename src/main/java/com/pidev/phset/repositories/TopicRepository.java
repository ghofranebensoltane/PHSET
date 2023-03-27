package com.pidev.phset.repositories;

import com.pidev.phset.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Integer> {

    @Query("select nameTopic from Topic")
    Set<String> getTopicList();
}
