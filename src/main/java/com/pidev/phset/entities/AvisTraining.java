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
public class AvisTraining {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idAvisT;
    String textAvisT;

    @ManyToOne
    Training training;
}
