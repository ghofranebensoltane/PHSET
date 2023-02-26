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
public class Criteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idCriteria;
    String nameCriteria;

    @ManyToOne
    GridEvaluation gridEvaluation;
}
