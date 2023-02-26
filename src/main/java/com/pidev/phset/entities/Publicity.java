package com.pidev.phset.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Publicity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long advertisingId;

    String Name;
    int min_age;
    int max_age;
    String text;
    @ManyToOne
    Account account;

}
