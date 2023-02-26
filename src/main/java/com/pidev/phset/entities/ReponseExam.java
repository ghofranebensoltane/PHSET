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
public class ReponseExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idRepExam;
    String textRepExam;

    @OneToOne(mappedBy = "reponseExam")
    QuestionExam questionExam;
}
