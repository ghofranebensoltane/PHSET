package com.pidev.phset.repositories;

import com.pidev.phset.entities.Account;
import com.pidev.phset.entities.Publicity;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPublicityRepository extends CrudRepository<Publicity,Integer> {
	List<Publicity> findByAccountIdAccount(Integer idAccount);
}
