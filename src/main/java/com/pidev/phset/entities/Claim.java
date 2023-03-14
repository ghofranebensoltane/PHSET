package com.pidev.phset.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pidev.phset.services.IEvaluationServices;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.mail.MessagingException;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Claim implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idClaim;

    String object;

    String content;

    @Enumerated(EnumType.STRING)
    EtatClaim etat = EtatClaim.No_Traited;

    LocalDateTime dateClaim = LocalDateTime.now();

    byte[] image;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    Decission decission;

    @JsonIgnore
    @ManyToOne
    Account account;

}
