package com.pidev.phset.repositories;

import com.pidev.phset.entities.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends CrudRepository<Comment,Integer> {
}
