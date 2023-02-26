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
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idRes;
    @Temporal(TemporalType.DATE)
    Date dateRes;
    Boolean etatPres ;

    @ManyToOne
    Event event;

    @ManyToOne
    Inscription inscription;

    @ManyToOne
    Account account;
}
