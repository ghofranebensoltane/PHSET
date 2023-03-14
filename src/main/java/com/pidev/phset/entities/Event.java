package com.pidev.phset.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    String activationCode;

    byte[] imageEvent;

    @Enumerated(EnumType.STRING)
    ModePay modePay;


    @Enumerated(EnumType.STRING)
    Mode modeEvent;

    @Enumerated(EnumType.STRING)
    TypeEvent typeEvent;

    @OneToMany(mappedBy = "event")
    Set<Classroom> classroom;

    @OneToMany(mappedBy = "event")
    Set<Reservation> reservations;

    @ManyToMany
            @JsonIgnore
    Set<Speaker> speakers;


    @ManyToMany
            @JsonIgnore
    Set<Speaker> speakersconf;


    @ManyToOne
            @JsonIgnore
    Room room;


}
