package com.pidev.phset.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    EtatInterview etatInterview;

    @Enumerated(EnumType.STRING)
    TypeGrid typeInterview;

    @ManyToOne
    @JsonIgnore
    Classroom classroom;

    @OneToOne
    @JsonIgnore
    JuryAppreciation juryAppreciation;

    @OneToMany(mappedBy = "interview")
    @JsonIgnore
    Set<MCQ> mcqs;
    @OneToOne
    @JsonIgnore
    GridEvaluation gridEvaluation;

    @OneToOne
    @JsonIgnore
    User condidat;

    @ManyToMany(mappedBy = "interviewJury")
    @JsonIgnore
    Set<User> jury;





}
