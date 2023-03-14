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
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idRoom;

    String nameRoom;

    Boolean available;


    @OneToMany(mappedBy = "room")
    Set<Event> events;
}
