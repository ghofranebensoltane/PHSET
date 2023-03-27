package com.pidev.phset.security.Services;

import com.pidev.phset.entities.User;
import com.pidev.phset.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Service
@RequiredArgsConstructor

public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUserRepository iUserRepository;
    @Override
    @Transactional
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = iUserRepository.findByFirstName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return user;
    }

}
