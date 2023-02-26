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
public class Question implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idQuestion;

    String nameQuestion;

    @Enumerated(EnumType.STRING)
    TypeTest typeTest;

    @ManyToOne
    @JsonIgnore
    MCQ mcq;

    @OneToMany(mappedBy = "question")
    Set<Response> responses;
}
