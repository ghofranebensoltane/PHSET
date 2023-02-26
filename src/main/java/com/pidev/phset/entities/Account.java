package com.pidev.phset.entities;

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
    User user;

    @OneToMany(mappedBy = "account")
    Set<Reservation> reservations;

    @ManyToOne
    Team team;

    @OneToMany(mappedBy = "account")
    Set<Claim> claims;

    @OneToMany(mappedBy = "account")
    Set<Post> posts;

    @OneToMany(mappedBy = "account")
    Set<Publicity> publicities;

    @OneToOne(mappedBy = "account")
    Inscription inscription;

    @OneToOne(mappedBy = "account")
    Badge badge;

    @ManyToMany(mappedBy = "accounts")
    Set<Training> trainings;

    @OneToMany(mappedBy = "account")
    Set<QuestionFAQ> questionFAQ;

    @ManyToMany(mappedBy = "accounts")
    Set<Interview> interviews;
}
