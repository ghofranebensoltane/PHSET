package com.pidev.phset.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Post  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer PostId;
    String postTitle;

    Date DatedeCreation;
    String body;
    int nb_Signal;
    int nb_etoil;
    Date StartDate;
    Date EndDate;
    int nbrIntialdesvues;
    int nbrFinaldesvues;
    float price;
    int nb_like;
    @ManyToOne
    Account account;

    @OneToOne
    Like like;
}
