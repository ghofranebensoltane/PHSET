package com.pidev.phset.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Publicity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer PublicityId;
    @NotBlank
    @Size(max = 40)
    String Name;
   // @NotBlank
    //@Size(max = 2)
    int min_age;
   // @NotBlank
   // @Size(max = 2)
    int max_age;
   // @NotBlank
  //  @Size(max = 120)
    String text;
    Date StartDate;
    Date EndDate;
    int nbrIntialdesvues;
    int nbrFinaldesvues;
    float price;
    @Lob
    byte[] picture;
    @ManyToOne
    Account account;

}
