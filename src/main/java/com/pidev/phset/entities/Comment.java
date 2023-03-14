package com.pidev.phset.entities;


import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
public class Comment implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer postCommentId;

    String commentBody;

    Date commentedAt;

    @JsonIgnore

    @ManyToOne
    Account account; // The user who wants to comment

    @JsonIgnore
    @ManyToOne
    Post post; // The post to comment


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postCo")
    Set<Comment> postComments; //Reflexive association : A comment can have multiple replies
    @JsonIgnore
    @ManyToOne
    Comment postCo;


    @OneToMany(mappedBy = "comments")
    Set<React> reacts;



}