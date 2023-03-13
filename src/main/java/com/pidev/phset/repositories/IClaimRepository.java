package com.pidev.phset.repositories;

import com.pidev.phset.entities.Account;
import com.pidev.phset.entities.Claim;
import com.pidev.phset.entities.EtatClaim;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface IClaimRepository extends CrudRepository<Claim, Integer> {
    List<Claim> findByAccount(Account account);

    List<Claim> findClaimsByEtatNotAndEtatNot(EtatClaim a,EtatClaim b);

    @Query("select c from Claim c where c.dateClaim = ?1 or c.account.inscription.user.firstName = ?2 or c.object = ?3 or c.etat = ?4")
    List<Claim> findClaimsByDateClaimOrAccount_Inscription_User_FirstNameOrObjectOrEtat(Date a, String b, String c, EtatClaim d);

    Long countByEtat(EtatClaim traited);

    @Query("select c from Claim c where c.etat = ?1 or c.etat = ?2 and c.dateClaim between ?3 and ?4")
    List<Claim> findByEtatOrEtatAndDateClaimBetween(EtatClaim etat, EtatClaim etat2, Date startDate, Date endDate);


    @Query("SELECT c FROM Claim c WHERE c.etat = 'TRAITED' or c.etat='Refused' AND c.dateClaim >= :start AND c.dateClaim < :end")
    List<Claim> findProcessedClaimsForLastMonth(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("select c from Claim c where c.dateClaim < ?1 and c.etat = ?2 or c.etat = ?3")
    List<Claim> findClaimsByDateClaimIsBeforeAndEtatOrEtat(LocalDateTime a , EtatClaim b, EtatClaim c);

    @Query("select c from Claim c where c.object = ?1")
    List<Claim> findByObject(String object);

}
