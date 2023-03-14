package com.pidev.phset.repositories;

import com.pidev.phset.entities.PostVideo;
import com.pidev.phset.entities.Team;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface PostVideoRepository extends CrudRepository<PostVideo, Integer> {

    Boolean findByTeam(Team team);

    Boolean findByDatePostVidIsBetween(LocalDateTime date1 , LocalDateTime date2);
}