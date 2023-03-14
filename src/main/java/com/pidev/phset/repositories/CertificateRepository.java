package com.pidev.phset.repositories;

import com.pidev.phset.entities.Certificate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CertificateRepository extends CrudRepository<Certificate, Integer> {


}