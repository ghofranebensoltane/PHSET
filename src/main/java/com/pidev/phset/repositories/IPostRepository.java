package com.pidev.phset.repositories;

import com.pidev.phset.entities.Account;
import com.pidev.phset.entities.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IPostRepository extends CrudRepository<Post,Integer> {
 //   List<Post> findAllByUser(Account account);
}