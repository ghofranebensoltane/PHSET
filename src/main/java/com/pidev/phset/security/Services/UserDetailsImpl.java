package com.pidev.phset.security.Services;/*package com.pidev.phset.security.Services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pidev.phset.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Integer id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = String.valueOf(password);
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
       /* List<GrantedAuthority> authorities = user.getRole().
                .map(role -> new SimpleGrantedAuthority(role)
                .collect(Collectors.toList()));

        return new UserDetailsImpl(
                user.getIdUser(),
                user.getFirstName(),
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList());
    }

    private static void collect(Collector<Object,?, List<Object>> toList) {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
*/
