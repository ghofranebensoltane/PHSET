package com.pidev.phset.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idClass;
    String nameClass;
    Integer capacityClass;
    @Enumerated(EnumType.STRING)
    BlocEsprit blocEsprit;
    String stateClass;

    @OneToMany(mappedBy = "classroom")
    List<Interview>interviews;

    @OneToMany(mappedBy = "classroom")
    List<ClassState> classStates;

    @ManyToOne
    Event event;
}
