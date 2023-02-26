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
public class Speaker implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idSpeaker;

    String nameSpeaker;

    String Biography ;

    String numTelSpeaker;

    String emailSpeaker;

    String socialMediaSpeaker;

    Boolean dispoSpeaker;


    @ManyToMany(mappedBy = "speakers")
    Set<Event> events;



}
