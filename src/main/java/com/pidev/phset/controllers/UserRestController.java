package com.pidev.phset.controllers;

import com.pidev.phset.entities.Role;
import com.pidev.phset.entities.User;
import com.pidev.phset.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserRestController {
    private final IUserService iUserService;

    @PostMapping("/addUser")
    User addUser (@RequestBody User user)
    {
        return iUserService.addUser(user);
    }
    @PutMapping("/updateUser")
    User updateUser (@RequestBody User user)
    {
        return iUserService.updateUser(user);
    }

    @GetMapping("/getUser/{iduser}")
    @RolesAllowed("ROLE_Admin")
    User getUser (@PathVariable Integer iduser)
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return iUserService.getUser(iduser);
    }
    @GetMapping("/getAllUser")
    List<User> getAllUser()
    {
        return iUserService.getAllUsers();
    }

    @DeleteMapping("/removeUser/{iduser}")
    void removeUser (@PathVariable Integer iduser)
    {
        iUserService.removeUser(iduser);
    }

}