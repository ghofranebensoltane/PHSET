package com.pidev.phset.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
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

    Integer nbLikePostVid = 0;

    String linkPostVid;

    LocalDateTime datePostVid = LocalDateTime.now();

    @OneToOne
    Team team;
}
