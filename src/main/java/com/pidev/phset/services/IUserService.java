package com.pidev.phset.services;
import com.pidev.phset.entities.User;


import java.util.List;

public interface IUserService {
    //User
    List<User> getAllUsers();

    User addUser (User user);

    User updateUser (User user);

    User getUser (Integer iduser);

    void removeUser(Integer iduser);

    User generateAccountUser(User user);

}