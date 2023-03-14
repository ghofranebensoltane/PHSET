package com.pidev.phset.services;

import com.pidev.phset.entities.Account;
import com.pidev.phset.entities.Post;
import com.pidev.phset.entities.Publicity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IPublicityService {

	void addPublicityForAccounts(int minAge, int maxAge, String name, String text, Date startDate, Date  endDate,
								 int nbrInitialDesvues, int nbrFinalDesvues, float price) ;

	float getTotalCostByAccount(Integer idAccount);
    void addImageToPublicity(Integer publicityId, MultipartFile image) throws IOException;
	

	long getDaysRemaining(Integer idPublicite);

}
