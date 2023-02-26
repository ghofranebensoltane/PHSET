package com.pidev.phset.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idInscription;
    String firstName;
    String lastName;
    String email;
    Integer cin;
    String passport;
    Date birthDate;
    @Enumerated(EnumType.STRING)
    Civility civility;
    String nationality;
    String phoneNumber;
    String address;
    Date dateInscription;
    String classStudent; // 1er,2eme ...
    String departProf; //web,ssd...
    @Enumerated(EnumType.STRING)
    TypeTraining typeTraining;

    @OneToOne
    Account account;

    @OneToOne
    User user;


}
