package com.pidev.phset.repositories;

import com.pidev.phset.entities.Account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account,Integer> {

	List<Account> findByUserAgeBetween(int minAge, int maxAge);
}
