package com.pidev.phset.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;
    private String firstName;
    private String lastName;
    private Integer cin;
    private String email;
    private Integer phone;
    private String address;
    private String files;
    private String Password;
    private Boolean availability;
    @Enumerated(EnumType.STRING)
    private  Role role;

    @OneToOne(mappedBy = "user")
    Account account;


    @OneToOne(mappedBy = "user")
    Inscription inscription;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getUsername() {
        return  getFirstName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public User(String firstName, String lastName, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.Password = password;
        this.role = Role.valueOf(role);
    }
}
