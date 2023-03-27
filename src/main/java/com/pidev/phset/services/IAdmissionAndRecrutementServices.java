package com.pidev.phset.services;

import com.pidev.phset.entities.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IAdmissionAndRecrutementServices {


    User addUser(User user);

    //////// **** CLASSSTATE Services **** ////////

    ClassState addClassState(ClassState classState);
    ClassState updateClassState(ClassState classState);
    void removeClassState(Integer idClassState);
    ClassState retrieveClassState(Integer idClassState);
    List<ClassState> retrieveAllClassStates();

    //////// **** CLASSROOM Services **** ////////

    Classroom addClassroom(Classroom classroom);
    Classroom updateClassroom(Classroom classroom);
    void removeClassroom(Integer idClassroom);
    Classroom retrieveClassroom(Integer idClassroom);
    List<Classroom> retrieveAllClassroom();


    //////// **** INSCRIPTION Services **** ////////

    Inscription addInscription(Inscription inscription);
    Inscription updateInscription(Inscription inscription);
    void removeInscription(Integer idInscription);
    Inscription retrieveInscription(Integer idInscription);
    List<Inscription> retrieveAllInscription();


    //////// **** INTERVIEW Services **** ////////

    Interview addInterview(Interview interview);
    Interview updateInterview(Interview interview);
    void removeInterview(Integer idInterview);
    Interview retrieveInterview(Integer idInterview);
    List<Interview> retrieveAllInterview();


    //////// **** OFFER Services **** ////////

    Offer addOffer(Offer offer);
    Offer updateOffer(Offer offer);
    void removeOffer(Integer idOffer);
    Offer retrieveOffer(Integer idOffer);
    List<Offer> retrieveAllOffer();

    //////// **** Algorithme IA **** ////////

    String addInscriptionWithUserAndAssignOffer(Inscription inscription, Integer idOffer);
    void setAndUpdateJuryAvailabilities();
    void setAndUpdateClassAvailabilities();
    void addInterviewAndAssignJuryAndCondidateAndClassroomToNewInscription();

    //////// TWILIO SMS ////////
/*
    Admission addAdmission(Admission admission);
    void sendAcceptanceSMS(Long id);
    void sendSMS(String toPhoneNumber, String message);


 */
    //////// EXTRACT TEXT FROM PDF ////////

    String extractTextFromPdf(String pdfPath) throws IOException;

    ResponseEntity<String> extractText(String path);

    String extractNiveauEtude(String pdfText);

    String extractSpecialite(String pdfText);

    String suggestNiveauEtudeAndSpecialite(String niveauEtude, String specialite);

    ////////////// MAIL ///////////////

    String sendMail();
    String sendMailAttachment() throws MessagingException;

    void sendMailInterview(Date date, String salle, String bloc);






}