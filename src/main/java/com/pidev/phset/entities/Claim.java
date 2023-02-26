package com.pidev.phset.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Claim implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idClaim;

    String object;

    String content;

    @Enumerated(EnumType.STRING)
    EtatClaim etat;

    Date dateClaim;

    @OneToOne(cascade = CascadeType.ALL)
    Decission decission;

    @ManyToOne
    Account account;
}
