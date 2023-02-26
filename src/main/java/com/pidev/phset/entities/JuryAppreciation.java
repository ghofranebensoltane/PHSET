package com.pidev.phset.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JuryAppreciation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idJuryAppreciation;
    Integer note;
    String comment;

    @OneToOne(mappedBy = "juryAppreciation")
    Interview interview;

}
