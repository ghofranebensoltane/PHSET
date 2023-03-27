package com.pidev.phset.services;

import com.pidev.phset.repositories.IUserRepository;
import com.pidev.phset.security.JWT.JwtUtils;
import com.pidev.phset.security.Services.UserDetailsServiceImpl;
import com.pidev.phset.security.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final  AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final  JwtUtils jwtUtils;

    @Transactional
    public TokenDto authenticateUser(String username, String userPassword) {
        String encode = encoder.encode(userPassword);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, userPassword));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.generateJwtToken(authentication);
        String refreshToken = jwtUtils.refreshToken(authentication);
        TokenDto tokenDto = new TokenDto();
        tokenDto.setAccessToken(accessToken);
        tokenDto.setRefreshToken(refreshToken);

        return tokenDto;
    }
    @Transactional
    public String refreshToken(String token) {
        String userNameFromJwtToken = jwtUtils.getUserNameFromJwtToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userNameFromJwtToken);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return  jwtUtils.refreshToken(authentication);

    }


}