package com.pidev.phset.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
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
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idEvent;
    String titleEvent;
    Float priceEvent;
    Integer nbPartEvent;
    String descriptionEvent;

    LocalDateTime dateS;

    LocalDateTime dateF;

    @Enumerated(EnumType.STRING)
    ModePay modePay;

    //Integer capacity;

    @Enumerated(EnumType.STRING)
    Mode modeEvent;

    @Enumerated(EnumType.STRING)
    TypeEvent typeEvent;

    @OneToMany(mappedBy = "event")
    Set<Classroom> classroom;

    @OneToMany(mappedBy = "event")
    Set<Reservation> reservations;

    @ManyToMany
    Set<Speaker> speakers;


    @ManyToMany
    Set<Speaker> speakersconf;

}
