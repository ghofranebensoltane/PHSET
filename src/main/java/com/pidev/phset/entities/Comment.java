package com.pidev.phset.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     int CommentId;
    String commentBody;

    Date commenteDate;
    //@ManyToOne
    //User user; qui veut ecrire une commentaire
    @ManyToOne
    Post post;  //publication qui on veut le commenter
    @ManyToOne
    Comment comment;

}
