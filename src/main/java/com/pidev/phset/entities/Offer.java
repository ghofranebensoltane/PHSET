package com.pidev.phset.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Offer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idOffer;
    String titleOffer;
    String descriptionOffer;
    @Enumerated(EnumType.STRING)
    Speciality speciality;
    @Enumerated(EnumType.STRING)
    TypeContract typeContract;
    float salaryOffer;
    @Enumerated(EnumType.STRING)
    TypeGrid offerType;

    @OneToMany(mappedBy = "offer")
    //@JsonIgnore
    Set<Inscription> inscriptions;
}
