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
public class MCQ implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idMcq;

    String title;

    float score;



    @Enumerated(EnumType.STRING)
    TypeTest typeTest;

    @JsonIgnore
    @ManyToMany (mappedBy = "mcqs")
    Set<Question> questions;

    @ManyToOne
    @JsonIgnore
    Interview interview;



}
