package com.pidev.phset.repositories;

import com.pidev.phset.entities.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IAccountRepository extends CrudRepository<Account, Integer> {

    @Query("select a from Account a inner join a.inscription ins where ins.classStudent ='1' and ins.user.role='ROLE_Student'")
    List<Account> retrieveAccount();
    
    	//List<Account> findByUserAgeBetween(int minAge, int maxAge);
    
    @Query("select a from Account a where a.inscription.user.firstName = ?1")
    List<Account> findByInscription_User_FirstName(String b);

}
