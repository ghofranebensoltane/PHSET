package com.pidev.phset.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GridEvaluation implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idGrid;

    @Enumerated(EnumType.STRING)
    TypeGrid typeGrid;

    float scoreGrid;

    @ManyToMany(mappedBy = "gridEvaluations")
    Set<Evaluation> evaluations;

    @OneToOne(mappedBy = "gridEvaluation")
    Interview Interview;





}
