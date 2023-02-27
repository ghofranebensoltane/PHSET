package com.pidev.phset.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Speaker implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idSpeaker;

    String nameSpeaker;

    String biography ;

    String numTelSpeaker;

    String emailSpeaker;

    String socialMediaSpeaker;

    @JsonIgnore
    @ManyToMany(mappedBy = "speakers")
    Set<Event> events;

    @ManyToMany(mappedBy = "speakersconf")
    Set<Event> eventsconf;



}
