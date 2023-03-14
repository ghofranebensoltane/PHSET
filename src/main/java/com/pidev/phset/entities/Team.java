package com.pidev.phset.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
            @JsonIgnore
    PostVideo postVideo;

    @OneToOne(mappedBy = "team")
    @JsonIgnore
    Subject subject;

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    Set<Account> accounts;
}
