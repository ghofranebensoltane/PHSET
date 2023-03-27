package com.pidev.phset.entities;

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
public class Training implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idTraining;
    String titleTraining;
    String descriptionTraining;
    String durationTraining;
    Technology subjectTraining;
    String levelTraining;
    Float priceTraining;
    Integer stateTraining = 0;
    @Enumerated(EnumType.STRING)
    TypeTraining typeTraining;

    @OneToMany(mappedBy = "training")
    Set<AvisTraining> avisTrainings;

    @OneToMany(mappedBy = "training")
    Set<Course> courses;

    @ManyToMany
    Set<Account> accounts;
}
