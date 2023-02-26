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
public class Team implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idTeam;
    String nameTeam;

    @OneToOne(mappedBy = "team")
    PostVideo postVideo;

    @OneToOne(mappedBy = "team")
    Subject subject;

    @OneToMany(mappedBy = "team")
    Set<Account> accounts;
}
