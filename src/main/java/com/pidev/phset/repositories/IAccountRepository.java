package com.pidev.phset.repositories;

import com.pidev.phset.entities.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IAccountRepository extends CrudRepository<Account, Integer> {

    @Query("select a from Account a inner join a.inscription ins where ins.classStudent ='1' and ins.user.role='Student'")
    List<Account> retrieveAccount();


}