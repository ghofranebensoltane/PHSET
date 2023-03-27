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
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idCours;
    String nameCours;
    String descriptionCours;
    Integer creditCours;
    String professorCours;
    String unit;
    @Enumerated(EnumType.STRING)
    Technology technology;
    byte[] picture;
    byte[] pdf;
    byte[] video;

    @ManyToOne
    //@JsonIgnore
    Training training;

    @OneToOne
    Exam exam;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    Set<Chapter> chapters;
}
