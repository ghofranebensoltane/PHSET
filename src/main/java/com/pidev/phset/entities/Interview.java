package com.pidev.phset.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    LocalDateTime dateInterview;
    LocalDateTime finInterview;
    String stateInterview;
    float noteFinal;

    @Enumerated(EnumType.STRING)
    TypeGrid typeInterview;

    @ManyToOne
    Classroom classroom;

    @OneToOne
    JuryAppreciation juryAppreciation;

    @OneToMany(mappedBy = "interview")
    Set<MCQ> mcqs;
    @OneToOne
    GridEvaluation gridEvaluation;

    @OneToOne
    User condidat;

    @ManyToMany(mappedBy = "interviewJury")
    Set<User> jury;





}
