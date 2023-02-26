package com.pidev.phset.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idInterview;
    String refInterview;
    Date dateInterview;
    Date durationInterview;
    String stateInterview;

    @ManyToOne
    Classroom classroom;

    @OneToOne
    JuryAppreciation juryAppreciation;

    @ManyToMany
    Set<Account> accounts;

    @OneToMany(mappedBy = "interview")
    Set<MCQ> mcqs;
    @OneToOne
    GridEvaluation gridEvaluation;


}
