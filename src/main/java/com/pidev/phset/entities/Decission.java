package com.pidev.phset.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Decission implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idDecission;

    String decission;

    LocalDateTime dateDecission = LocalDateTime.now();

    @OneToOne(mappedBy = "decission")
    Claim claim;
}
