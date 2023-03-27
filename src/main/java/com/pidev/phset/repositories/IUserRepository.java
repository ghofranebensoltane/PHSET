package com.pidev.phset.repositories;

import com.pidev.phset.entities.Role;

import com.pidev.phset.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IUserRepository extends CrudRepository<User,Integer> {
    Optional<User> findByFirstName(String username);

    Boolean existsByFirstName(String username);

    Boolean existsByEmail(String email);
    
    List<User> findByRole(Role a);

    @Query("select u from User u where u.idUser = ?1")
    User findByIdUser(int id);
    @Query("SELECT u FROM User u WHERE u.role='ROLE_Professor' and (SELECT uv.Availability FROM UserAvailability uv WHERE  uv.dateUserAvailability = :date and uv.user=u) is TRUE ORDER BY RAND()")
    List<User> findAvailableJurys(@Param("date") LocalDateTime date);

    @Query("select u from User u where u.role='ROLE_Professor'")
    Set<User> getAllJury();
    @Query("select u from User u where u.role='ROLE_NewUSER'")
    Set<User> getAllCandidates();
    User getFirstByIdUser(Integer idUser);
}
