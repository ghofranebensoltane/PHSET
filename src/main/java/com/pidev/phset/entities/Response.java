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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Response implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idResponse;

    String response;

    Boolean correct;

    @ManyToOne
    @JsonIgnore
    Question question;

}
