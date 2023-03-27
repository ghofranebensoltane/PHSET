package com.pidev.phset.services;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pidev.phset.entities.Account;
import com.pidev.phset.entities.Publicity;
import com.pidev.phset.repositories.IAccountRepository;
import com.pidev.phset.repositories.IPublicityRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PubServiceImpl implements IPublicityService {
    IAccountRepository accountRepository;
    IPublicityRepository publicityRepository;

    @Override
    public void addPublicityForAccounts(int minAge, int maxAge, String name, String text, Date startDate, Date endDate,
                                        int nbrInitialDesvues, int nbrFinalDesvues, float price )  {
        /*
        List<Account> accounts = accountRepository.findByUserAgeBetween(minAge, maxAge);
        for (Account account : accounts) {
            Publicity publicity = new Publicity();
            publicity.setName(name);
            publicity.setMax_age(maxAge);
            publicity.setMin_age(minAge);
            publicity.setText(text);
            publicity.setStartDate(startDate);
            publicity.setEndDate(endDate);
            publicity.setNbrIntialdesvues(nbrInitialDesvues);
            publicity.setNbrFinaldesvues(nbrFinalDesvues);
            publicity.setPrice(price);
            publicity.setAccount(account);
            publicityRepository.save(publicity);

        }

         */
    }


    @Override
    public float getTotalCostByAccount(Integer idAccount) {

        List<Publicity> publicities = publicityRepository.findByAccountIdAccount(idAccount);
        float totalCost = 0.0f;
        for (Publicity publicity : publicities) {
            totalCost += publicity.getPrice();
        }
        return totalCost;

    }
    @Override
    public void addImageToPublicity(Integer publicityId, MultipartFile image) throws IOException {
        Publicity publicity=publicityRepository.findById(publicityId).get();
        publicity.setPicture(image.getBytes());
        publicityRepository.save(publicity);

    }

    @Override
    public long getDaysRemaining(Integer idPublicite) {
        Publicity publicity=publicityRepository.findById(idPublicite).get();
        Instant now = Instant.now();
        Instant end = publicity.getEndDate().toInstant();
        Duration duration = Duration.between(now, end);
        return duration.toDays();
    }



}

