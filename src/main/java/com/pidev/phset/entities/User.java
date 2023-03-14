package com.pidev.phset.entities;

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
public class User implements Serializable {
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
    private Integer age;
    private Boolean availability;
    @Enumerated(EnumType.STRING)
    private  Role role;

    @OneToOne(mappedBy = "user")
    Account account;

    @OneToOne(mappedBy = "user")
    Inscription inscription;

    @OneToOne(mappedBy = "condidat")
    Interview interview;
    @ManyToMany
    Set<Interview> interviewJury;

}
