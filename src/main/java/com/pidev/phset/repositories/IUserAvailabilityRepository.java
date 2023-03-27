package com.pidev.phset.repositories;

import com.pidev.phset.entities.User;
import com.pidev.phset.entities.UserAvailability;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface IUserAvailabilityRepository extends CrudRepository<UserAvailability,Integer> {

    List<UserAvailability> getAllByUserOrderByDateUserAvailabilityDesc(User user);



}
