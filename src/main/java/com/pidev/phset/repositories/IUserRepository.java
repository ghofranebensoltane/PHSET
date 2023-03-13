package com.pidev.phset.repositories;

import com.pidev.phset.entities.Role;
import com.pidev.phset.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUserRepository extends CrudRepository<User,Integer> {
    List<User> findByRole(Role a);

    @Query("select u from User u where u.idUser = ?1")
    User findByIdUser(int id);
}
