package com.pidev.phset.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @OneToMany(cascade = CascadeType.ALL)
    Set<Post> post;
}
