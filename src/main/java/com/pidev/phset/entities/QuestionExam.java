package com.pidev.phset.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idQuestExam;
    String textQuestExam;
    @Enumerated(EnumType.STRING)
    Technology technology;

    @ManyToOne
    Exam exam;

    @OneToMany(mappedBy = "questionExam")
    //@JsonIgnore
    Set<ReponseExam> reponsesExam;
}