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
    String subjectTraining;
    String levelTraining;
    Float priceTraining;
    String stateTraining;
    @Enumerated(EnumType.STRING)
    TypeTraining typeTraining;

    @OneToMany(mappedBy = "training")
    Set<AvisTraining> avisTrainings;

    @ManyToMany(mappedBy = "trainings")
    Set<Cours> cours;

    @ManyToMany
    Set<Account> accounts;
}
