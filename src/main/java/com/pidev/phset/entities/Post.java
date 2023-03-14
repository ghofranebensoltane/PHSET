package com.pidev.phset.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pidev.phset.entities.Account;
import com.pidev.phset.entities.Comment;
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
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer postId;

    String postTitle;

     LocalDateTime createdAt;

    String body;


    int nb_Signal;


    int nb_etoil;


    @ManyToOne
    @JsonIgnore
    Account account;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    List<React> Reacts;


@JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    List<Comment> comments ;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    List<React> reacts;




}