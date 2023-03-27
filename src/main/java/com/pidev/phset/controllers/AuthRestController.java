package com.pidev.phset.controllers;

import com.pidev.phset.entities.Role;
import com.pidev.phset.security.dto.TokenDto;
import com.pidev.phset.services.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/api/auth")
@Log4j2
public class AuthRestController {

    @Autowired
    private AuthenticationService authenticate;



    @PostMapping("/signin")
    public ResponseEntity<TokenDto> authenticateUser(@RequestParam(value = "username") String username, @RequestParam(value = "userPassword") String userPassword) {
        TokenDto response = authenticate.authenticateUser(username,userPassword);
        log.info("ws : Generate token {}", username);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/refreshtoken")
    public ResponseEntity<String> refreshToken(@RequestParam(value="token") String token) throws Exception {
        String responseEntity = authenticate.refreshToken(token);
        return ResponseEntity.ok(responseEntity);
    }
    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }

}