package com.pidev.phset.services;

import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.pidev.phset.entities.*;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IEventServices {

    //Event
    List<Event> retrieveAllEvents();

    public Event addEvent(Event e, MultipartFile file) throws MessagingException, IOException;

    Event updateEvent (Event e);

    Event retrieveEvent(Integer idEvent);

    void removeEvent(Integer idEvent);




    //PostVideo
    List<PostVideo> retrieveAllPostsVideo();

    PostVideo addPostVideo (PostVideo p);

    PostVideo updatePostVideo (PostVideo p);

    PostVideo retrievePostVideo(Integer idPostV);

    void removePostVideo(Integer idPostV);


    //Subject
    List<Subject> retrieveAllSubjects();

    Subject addSubject (Subject sub);

    Subject updateSubject (Subject sub);

    Subject retrieveSubject (Integer idSub);

    void removeSubject(Integer idSub);


    //Team
    List<Team> retrieveAllTeams();

    Team addTeam (Team team);

    Team updateTeam (Team team);

    Team retrieveTeam (Integer idTeam);

    void removeSTeam(Integer idTeam);


    //Speaker
    List<Speaker> retrieveAllSpeakers();

    Speaker addSpeaker (Speaker speaker);

    Speaker updateSpeaker (Speaker speaker);

    Speaker retrieveSpeaker (Integer idSpeaker);

    void removeSpeaker(Integer idSpeaker);

    void AssignSpeakerToEvent(int event) throws MessagingException;
    public double getScore(Speaker speaker, Event event);

    public void AssignSpeakerToConfirmed(int idsp, int idevent);

    public void assignStudentToTeam() throws MessagingException;

    public void AddAndAssignResevToEventAndAcc(Reservation reservation, int idEvent ,  int idAccount);

    public Room AddRoom ( Room room);

    public void unassignRoom(int id );

    public void unassignRoomIfEventEnded();

     public Team CalculLikes ();
    public List<Event> sort();
    public Certificate createCertificates(int idevent) throws DocumentException, IOException, WriterException, MessagingException;
    public String BodyCertif(int i);
    public ResponseEntity<byte[]> generateCertificateok(Certificate certificat, int id) throws IOException, DocumentException, WriterException, MessagingException;
    public String generateUniqueId();

    public void sendEmail(String to, String subject, String body) throws MessagingException;

    public String SetSubject(Event event);
    public List<Event> searchEvents(String a, Float b, Integer c, String d, LocalDateTime e, LocalDateTime f, Mode g, ModePay h, TypeEvent i, Integer j);

    public void SetPresence (int idAcc);

    public Thematic addThematic(Thematic thematic);

    public void addSubjectAndAssignThematicAndTeam(Subject s, Integer idteam, Integer idthem);

    public Map<String, Long> countEventsByType();

    public Map<String, Long> countEventsByModePay();

    public String confirmRegistration(String activationCode, String action);

    public void sendEmailWithAttachment(String to, String subject, String body, byte[] qrCodeBytes) throws MessagingException;

    public ResponseEntity<String> handleUpload(PostVideo postVideo, MultipartFile video);
    public PostVideo createPostVid(PostVideo postVideo, int idacc, MultipartFile multipartFile);
    public String payer(Payement paiement, Model model, int idReservation) throws StripeException ;


    public void findUsersByEvent(Event event) throws MessagingException;

    public void planEvent(int id) throws DocumentException, IOException ;
    }
