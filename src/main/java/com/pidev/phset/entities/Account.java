package com.pidev.phset.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.geometry.Pos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAccount;
    private String emailAccount;
    private String passwordAcccount;
    @OneToOne
            @JsonIgnore
    User user;

    @OneToMany(mappedBy = "account")
    @JsonIgnore

    Set<Reservation> reservations;

    @ManyToOne
    @JsonIgnore
    Team team;

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    Set<Claim> claims;

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    Set<Post> posts;

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    Set<Publicity> publicities;

    @OneToOne(mappedBy = "account")
    @JsonIgnore
    Inscription inscription;

    @OneToOne(mappedBy = "account")
    @JsonIgnore
    Badge badge;

    @ManyToMany(mappedBy = "accounts")
    @JsonIgnore
    Set<Training> trainings;

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    Set<QuestionFAQ> questionFAQ;

    @ManyToMany(mappedBy = "accounts")
    @JsonIgnore
    Set<Interview> interviews;
}
