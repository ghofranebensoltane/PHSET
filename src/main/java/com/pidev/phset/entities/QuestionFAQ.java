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
public class QuestionFAQ implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idFAQ;
    String textQuestion;
    Integer view;

    @ManyToOne
    Topic topic;

    @ManyToMany
    Set<Tag> tags;

    @OneToMany(mappedBy = "questionFAQ")
    Set<ReponseFAQ> reponseFAQS;

    @ManyToOne
    Account account;

}
