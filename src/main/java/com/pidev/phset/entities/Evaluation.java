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
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Evaluation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idEvaluation;

    String taskEval;

    @Enumerated(EnumType.STRING)
    TypeGrid typeEvaluation;

    @ManyToMany
    @JsonIgnore
    Set<GridEvaluation> gridEvaluations;

    @OneToMany(mappedBy = "evaluation")
    Set<TaskEvaluation> taskEvaluation;
}
