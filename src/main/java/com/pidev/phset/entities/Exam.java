package com.pidev.phset.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Exam implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idExam;
    String titleExam;
    String descriptionExam;
    Date deadlineExam;
    String subjectExam;
    String levelExam;
    String stateExam;
    Float scoreExam;

    @OneToOne(mappedBy = "exam")
    Course course;

    /*
    @OneToOne(mappedBy = "exam")
    Certificate certificate;
*/
    @OneToMany(mappedBy = "exam")
    Set<QuestionExam> questionExams;
}
