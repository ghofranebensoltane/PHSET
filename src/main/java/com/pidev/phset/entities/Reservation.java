package com.pidev.phset.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idRes;

    LocalDateTime dateRes = LocalDateTime.now();
    Boolean etatPres ;

    boolean confirmPay = false;

    @ManyToOne
            @JsonIgnore
    Event event;

    @ManyToOne
            @JsonIgnore
    Account account;

}
