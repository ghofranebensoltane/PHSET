package com.pidev.phset.services;

import com.pidev.phset.entities.User;
import com.pidev.phset.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor

public class UserService implements IUserService{
    private final IUserRepository iUserRepository;

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        iUserRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User addUser(User user) {
        return iUserRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return iUserRepository.save(user);
    }

    @Override
    public User getUser(Integer iduser) {
        return iUserRepository.findById(iduser).orElse(null);
    }

    @Override
    public void removeUser(Integer iduser) {
        iUserRepository.deleteById(iduser);

    }

    @Override
    public User generateAccountUser(User user) {
        Random random = new Random();
        String idUnique=String.format("%05d",random.nextInt(100000));
        String userName = user.getFirstName().toLowerCase() +idUnique;
        user.setFirstName(userName);
        return iUserRepository.save(user);
    }

}