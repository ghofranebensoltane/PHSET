package com.pidev.phset.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostVideo implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer idPostVid;
    String descriptionPostVid;

    Integer nbLikePostVid;

    String linkPostVid;

    @Temporal(TemporalType.DATE)
    Date datePostVid;

    @OneToOne
    Team team;
}
