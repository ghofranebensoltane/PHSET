package com.pidev.phset.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class React implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer ReactId;

    @Enumerated(EnumType.STRING)
    ReactEnum reactEnum;
    @ManyToOne
    Post post; // The post to like

    @JsonIgnore

    @ManyToOne
    Account account; // The user who wants to comment


    @ManyToOne
    Comment comments;


}