package com.pidev.phset.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GridEvaluation implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idGrid;

    @Enumerated(EnumType.STRING)
    TypeGrid typeGrid;

    float scoreGrid;

    @JsonIgnore
    @ManyToMany(mappedBy = "gridEvaluations")
    Set<Evaluation> evaluations;

    @JsonIgnore
    @OneToOne(mappedBy = "gridEvaluation")
    Interview interview;





}
