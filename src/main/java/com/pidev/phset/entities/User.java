package com.pidev.phset.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idUser;
    String firstName;
    String lastName;
    Integer cin;
    String email;
    String phone;
    String address;
    String files;
    String Password;

    String passport;
    Date birthDate;
    @Enumerated(EnumType.STRING)
    Civility civility;
    String nationality;
    @Enumerated(EnumType.STRING)
    private  Role role;

    @OneToMany(mappedBy = "user")
    Set<UserAvailability> userAvailabilities;


    @OneToOne(mappedBy = "user")
    Inscription inscription;

    @OneToOne(mappedBy = "condidat")
    Interview interview;
    @ManyToMany
    Set<Interview> interviewJury;

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
