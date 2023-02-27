package com.pidev.phset.repositories;

import com.pidev.phset.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}