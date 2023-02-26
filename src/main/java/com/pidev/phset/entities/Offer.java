package com.pidev.phset.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idOffer;
    String titleOffer;
    String descriptionOffer;
    @Enumerated(EnumType.STRING)
    TypeContract typeContract;
    Integer salaryOffer;

    @OneToMany(mappedBy = "offer")
    List<Candidacy> candidacies;
}
