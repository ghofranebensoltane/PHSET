package com.pidev.phset.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idInscription;
    LocalDateTime dateInscription;
    String cv;
    String coverLetter;
    String classStudent; // 1er,2eme ...
    String departProf; //web,ssd...
    @JsonIgnore
    Boolean state = false;

    @OneToOne
    Account account;

    @OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    //@JsonIgnore
    User user;

    @ManyToOne
    //@JsonIgnore
    Offer offer;

    @Enumerated(EnumType.STRING)
    TypeTraining typeTraining;


}
