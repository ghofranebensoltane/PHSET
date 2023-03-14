package com.pidev.phset.repositories;

import com.pidev.phset.entities.Account;
import com.pidev.phset.entities.React;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReactRepository extends CrudRepository<React,Integer> {

    @Query("select distinct r FROM React r inner join Post p where p.account =?1")
    React getReactByAccount(Account account);
}
