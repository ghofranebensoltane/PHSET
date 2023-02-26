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
public class QuestionExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idQuestExam;
    String textQuestExam;

    @ManyToOne
    Exam exam;

    @OneToOne
    ReponseExam reponseExam;
}