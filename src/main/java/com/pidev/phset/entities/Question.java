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
public class Question implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idQuestion;

    String nameQuestion;

    @Enumerated(EnumType.STRING)
    TypeTest typeTest;

    @Enumerated(EnumType.STRING)
    QuestionLevel questionLevel;

    @ManyToMany
    @JsonIgnore
    Set<MCQ> mcqs;

    @OneToMany(mappedBy = "question")
    Set<Response> responses;
}
