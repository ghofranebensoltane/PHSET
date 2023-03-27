package com.pidev.phset.services;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfDocument;

import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pidev.phset.configures.QRCode;
import com.pidev.phset.entities.*;
import com.pidev.phset.entities.Event;
import com.pidev.phset.repositories.*;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.TokenCreateParams;
import javafx.scene.Camera;
import lombok.AllArgsConstructor;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import sun.security.util.ArrayUtil;
import org.apache.commons.text.similarity.LevenshteinDistance;



import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import java.util.Base64;
import java.util.stream.Collectors;

import ognl.DefaultMemberAccess;
@Service
@AllArgsConstructor
public class EventServices implements IEventServices {

    private final EventRepository eventRepository;

    private final PostVideoRepository postVideoRepository;

    private final SubjectRepository subjectRepository;

    private final TeamRepository teamRepository;

    private final SpeakerRepository speakerRepository;

    private final IAccountRepository accountRepository;

    private final IInscriptionRepository inscriptionRepository;

    private final ReservationRepository reservationRepository;

    public final ThematicRepository thematicRepository;

    @Autowired
    private JavaMailSender mailSender;
    private final RoomRepository roomRepository;

    private final IUserRepository userRepository;

    private final CertificateRepository certificateRepository;

    //////////////////////////////////////////////////////// Mail ////////////////////////////////////////////////////////


    @Override
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);

        mailSender.send(message);
    }

    public void sendEmailWithAttachment(String to, String subject, String body, byte[] qrCodeBytes) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);

        ByteArrayResource qrCodeResource = new ByteArrayResource(qrCodeBytes);
        helper.addAttachment("qrCode.png", qrCodeResource);

        mailSender.send(message);
    }


    ///////////////////////////////////////////////////////// Crud Subject And thematic + Assign SubjectToThematicAndTeam/////////////////////////////////////////////////////
    @Override
    public List<Subject> retrieveAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        subjectRepository.findAll().forEach(subjects::add);
        return subjects;
    }

    @Override
    public Subject addSubject(Subject sub) {
        return subjectRepository.save(sub);
    }

    @Override
    public Subject updateSubject(Subject sub) {
        return subjectRepository.save(sub);
    }

    @Override
    public Subject retrieveSubject(Integer idSub) {
        return subjectRepository.findById(idSub).orElse(null);
    }

    @Override
    public void removeSubject(Integer idSub) {
        subjectRepository.deleteById(idSub);
    }

    @Override
    public Thematic addThematic(Thematic thematic) {
        return thematicRepository.save(thematic);
    }

    ///////////////////////////////////AssignSubjectToThematicAndTeam
    @Override
    @Transactional
    public void addSubjectAndAssignThematicAndTeam(Subject s, Integer idteam, Integer idthem) {
        Thematic c = thematicRepository.findById(idthem).orElse(null);
        Team m = teamRepository.findById(idteam).orElse(null);

        s.setTeam(m);
        s.setThematic(c);
        subjectRepository.save(s);
    }



    ///////////////////////////////////////////////////////// Crud Team + Assign Account To Team ///////////////////
    @Override
    public List<Team> retrieveAllTeams() {
        List<Team> teams = new ArrayList<>();
        teamRepository.findAll().forEach(teams::add);
        return teams;
    }

    @Override
    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Team updateTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Team retrieveTeam(Integer idTeam) {
        return teamRepository.findById(idTeam).orElse(null);
    }

    @Override
    public void removeSTeam(Integer idTeam) {
        teamRepository.deleteById(idTeam);
    }

    @Override
    //@Scheduled(cron = "0 0 0 1 9 *")
    @Transactional
    public void assignStudentToTeam() throws MessagingException {
        List<Account> accounts = accountRepository.retrieveAccount(); // Par classe 1
        List<Team> teams = new ArrayList<>();

        //Creation des comptes depend de nombre des etudiants
        for (int a = 0; a < accounts.size(); a = a + 5) {
            Team t = new Team();
            teamRepository.save(t);
        }

        Event e = eventRepository.findDistinctFirstByTypeEvent(TypeEvent.APP0);

        teamRepository.findAll().forEach(teams::add);

        for (Team team : teams) {
            for (Account account : accounts) {
                if (team.getAccounts() == null || team.getAccounts().size() < 5) {
                    account.setTeam(team);
                    accountRepository.save(account);
                    String to = account.getInscription().getUser().getEmail();
                    String body = "Dear " + account.getInscription().getUser().getLastName() + " " + account.getInscription().getUser().getFirstName() + ",\n" +
                            "\n" +
                            "Welcome To esprit" + "We hope this message finds you well. We would like to formally invite you to attend " +
                            "\n" +
                            e.getTitleEvent() + " wich represents the inaugural activity of the 1st years of all specialties at ESPRIT. \n" +
                            "\n" +
                            "this year, the week of" + e.getTitleEvent() + " ill take place from " + e.getDateS() + "to" + e.getDateF() + " at Ariana, Hay El Ghazella Esprit Bloc: " + e.getRoom().getNameRoom() + ".\n" +
                            "\n" +
                            "The purpose of the APP-O week is to familiarize you with Esprit's values, to introduce you to its project-based pedagogy and to allow you to get to know your fellow students who have just joined Esprit.\n" +
                            "Your presence at this event is essential,\n" +
                            "The APP0 will make you earn your first ECTS (European Credit Transfer and Accumulation System) since it is a graded activity.\n" +
                            "Thank you for your time and consideration. We look forward to hearing from you soon.\n" +
                            "\n" +
                            "N.B.: Your Team number is " + account.getTeam().getIdTeam() +
                            "Thank you for your time and consideration. We hope to see you at " + e.getTitleEvent() +
                            "\n" +
                            "Best regards,\n" +
                            "\n" +
                            "Ecole supérieur privée d'ingénierie et de technologies";

                    sendEmail(to, SetSubject(e), body);
                }
            }

            for (int y = 0; y < 5 && y < accounts.size(); y++) {
                accounts.remove(y);
            }

        }

        for (Team p : teams) {
            for (Account o : accounts) {
                if (o.getInscription().getUser().getRole().equals(Role.ROLE_Professor) && o.getTeam() == null) {
                    o.setTeam(p);
                }
            }
        }

    }


    /////////////////////////////////////////////////// Crud Speaker/////////////////////////////////////////
    @Override
    public Speaker addSpeaker(Speaker speaker) {

        return speakerRepository.save(speaker);
    }

    @Override
    public Speaker updateSpeaker(Speaker speaker) {
        return speakerRepository.save(speaker);
    }

    @Override
    public Speaker retrieveSpeaker(Integer idSpeaker) {
        return speakerRepository.findById(idSpeaker).orElse(null);
    }

    @Override
    public void removeSpeaker(Integer idSpeaker) {
        speakerRepository.deleteById(idSpeaker);

    }

    @Override
    public List<Speaker> retrieveAllSpeakers() {
        List<Speaker> speakers = new ArrayList<>();
        speakerRepository.findAll().forEach(speakers::add);
        return speakers;
    }


    ///////////////////////////////////////////////////// Crud Event + AssignEventToSpeakerAndRooms////////////////////////////////////////

    @Override
    public List<Event> retrieveAllEvents() {
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().forEach(events::add);
        return events;
    }

    @Override
    public Event updateEvent(Event e) {
        return eventRepository.save(e);
    }

    @Override
    public Event retrieveEvent(Integer idEvent) {
        return eventRepository.findById(idEvent).orElse(null);
    }

    @Override
    public void removeEvent(Integer idEvent) {
        eventRepository.deleteById(idEvent);
    }


    ////////////////////////////////////////////////// Assign men hneee
    @Transactional
    @Override
    public Event addEvent(Event e, MultipartFile file) throws MessagingException, IOException {

        if(file!=null){
            byte[] bytes = file.getBytes();
            e.setImageEvent(bytes);
        }

        eventRepository.save(e);
        if (e.getModeEvent().equals(Mode.Face_To_Face)) {
            assignRoom(e);
        }
        AssignSpeakerToEvent(e.getIdEvent());
        findUsersByEvent(e);

        return e;
    }

    @Override
    public double getScore(Speaker speaker, Event event) {
        double score = 0;
        String bio = speaker.getBiography().toLowerCase();
        TypeEvent eventType = event.getTypeEvent();
        JaroWinklerDistance distance = new JaroWinklerDistance();
        double threshold = 0.8; // Seuil de similarité minimum pour être considéré comme une correspondance
        if (eventType == TypeEvent.APP0 ){
            if(distance.apply(bio, "ancien étudiant") >= threshold|| distance.apply(bio, "professeur") >= threshold) {
                score += 5;
            }
        } else if (eventType == TypeEvent.Educational_Event) {
            if (distance.apply(bio, "formateur") >= threshold || distance.apply(bio, "professeur") >= threshold) {
                score += 8;
            }
        } else if (eventType == TypeEvent.Leisure_Event) {
            if (distance.apply(bio, "influenceur") >= threshold) {
                score += 6;
            } else if (distance.apply(bio, "animateur") >= threshold) {
                score += 4;
            }
        }
        return score;
    }


    @Override
    public void AssignSpeakerToEvent(int h) throws MessagingException {

        List<Speaker> speakers = new ArrayList<>();
        speakerRepository.findAll().forEach(speakers::add);
        Event event = eventRepository.findById(h).orElse(null);
        double i = 0;
        Set<Speaker> affectes = new HashSet<>();
        for (Speaker speaker : speakers) {
            i = getScore(speaker, event);
            if (i > 0) {
                speaker.getEvents().add(event);
                affectes.add(speaker);
                String to = speaker.getEmailSpeaker();
                UUID code = UUID.randomUUID();
                speaker.setActivationCode(code.toString());
                event.setActivationCode(code.toString());
                String confirmationLink = "http://localhost:8082/Rev/event/confirm?action=confirm&code=" + code;
                String body = "Dear " + speaker.getNameSpeaker() + ",\n\n" +
                        "\n" +
                        "I hope this message finds you well. I am writing to formally invite you to be a keynote speaker at " + event.getTitleEvent() + ", which will take place on " + event.getDateS() + " at Ariana, Hay El Ghazella Esprit Bloc: " + ".\n" + //+ event.getRoom().getNameRoom()
                        "\n" +
                        "We are impressed by your extensive knowledge and experience in " + speaker.getBiography() + ", and we believe that your insights would be valuable to our audience. Your expertise would greatly contribute to the success of our event, and we would be honored to have you as our guest speaker.\n" +
                        "\n" +
                        "Our event aims to " + event.getTitleEvent() + ".\n" +
                        "\n" +
                        "Please let us know at your earliest convenience if you are available to speak at our event. We would be happy to provide you with additional information about the event, such as the audience demographics, event schedule, and other logistical details.\n" +
                        "\n" +
                        "Thank you for your time and consideration. We look forward to hearing from you soon.\n\n" +
                        "\n" +
                        "Best regards,\n\n" +
                        "\n" +
                        "Ecole supérieur privée d'ingénierie et de technologies \n\n" +
                        "\n" +
                        "Confirmation link : \n" + confirmationLink;

                sendEmail(to, SetSubject(event), body);

            }
            i = 0;
        }
        event.setSpeakers(affectes);
        eventRepository.save(event);
    }

    /////////////////// subject de l'email
    @Override
    public String SetSubject(Event event) {
        String subject = null;
        if (event.getTypeEvent().equals(TypeEvent.APP0)) {
            subject = "Invitation to the integration week " + event.getTitleEvent();
        } else if (event.getTypeEvent().equals(TypeEvent.Educational_Event)) {
            subject = "Invitation to the training  " + event.getTitleEvent();
        } else {
            subject = "Invitation to the event  " + event.getTitleEvent();
        }
        return subject;
    }


    /////////////////////////////////////////////// lahnee fazet l liste de confirmation mtaa speaker /////////////////////////////
    @Override
    public void AssignSpeakerToConfirmed(int idsp, int idevent) {
        Speaker speaker = speakerRepository.findById(idsp).orElse(null);
        Event event = eventRepository.findById(idevent).orElse(null);

        event.getSpeakers().remove(speaker);
        if (event.getSpeakersconf().size() == 0) {
            Set<Speaker> speakers = new HashSet<>();
            speakers.add(speaker);
            event.setSpeakersconf(speakers);
        } else {
            event.getSpeakersconf().add(speaker);
        }
        eventRepository.save(event);
    }

    @Override
    public String confirmRegistration(String activationCode, String action) {
        // Vérifier le code d'activation
        if (action.equals("confirm")) {
            Event event = eventRepository.findEventByActivationCode(activationCode);
            Speaker speaker = speakerRepository.findSpeakerByActivationCode(activationCode);

            if (event != null && speaker != null && event.getDateS().isAfter(LocalDateTime.now())) {
                AssignSpeakerToConfirmed(speaker.getIdSpeaker(), event.getIdEvent());
                return "Successful confirmation";
            } else {
                return "Event completed ";
            }
        } else {
            return "Event completed ";
        }
    }


    ////////////add o unassign aadeya
    @Override
    public Room AddRoom(Room room) {
        return roomRepository.save(room);
    }

    //unassign aadeya maghir mani naaytelha mtaa juste admin
    @Override
    public void unassignRoom(int id) {
        Event event = eventRepository.findById(id).orElse(null);
        Room room = event.getRoom();
        event.setRoom(null);
        // room.setAvailable(true);
        roomRepository.save(room);
        eventRepository.save(event);
    }
    /////////////////////////////////////////////////////////////////////// Lehne lel Users Fidèles


    @Override
    public void findUsersByEvent(Event event) throws MessagingException {
        Objects.requireNonNull(event,"L'event est null");

        String typeEvent = event.getTypeEvent().toString();

        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        if(users.isEmpty()){
            return;
        }

        // Users avec nbr reserv Similaire
        Map<User,Integer> map = new HashMap<>();
        for(User user : users){
            if(user.getInscription().getAccount().getReservations()!=null){
                for (Reservation reservation : user.getInscription().getAccount().getReservations()) {
                    if (reservation.getEvent().getTypeEvent().toString().equals(typeEvent)) { // si reservation nafs type event ajouté
                        map.put(user, map.getOrDefault(user, 0) + 1); // nbr reserv silimaire +1
                    }
                }
            }
        }

        //liste trier en fonction nbr reservation similaire
        List<Map.Entry<User, Integer>> sortedEntries = new ArrayList<>(map.entrySet()); // liste trier de façon decroissante pour avoir les 10 tops premier
        sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder())); // decroissant
        List<User> topUsers = new ArrayList<>();
        int count = 0;
        for (Map.Entry<User, Integer> entry : sortedEntries) {
            if (count >= 10) {
                break;
            }
            topUsers.add(entry.getKey());
            count++;
        }

        for(User user : topUsers){
            if(user.getEmail()!=null){
                String to = user.getEmail();
                String subject = "New event";
                String body ="Hello dear students,\n\n" +
                        "\n" +
                        "We are excited to inform you about a new exciting event that we have planned. It's a great opportunity to have fun and enjoy some new experiences. The event will take place on \n"+ event.getDateS()+
                        "and will include a range of activities and games to keep you engaged and entertained. We hope that you will come and join us for this amazing event.\n" +
                        "\n" +
                        "Best regards,\n\n" +
                        "\n" +
                        "Ecole supérieur privée d'ingénierie et de technologies";
                sendEmail(to,subject,body);
                System.out.println(user);
            }
        }

    }



    ////////////////////////////////////////////////////////////////// Lehne mtaa Room ////////////////////////
    public boolean checkDisponibilite(Event event, Room room) {
        LocalDateTime dateStart = event.getDateS();
        LocalDateTime dateEnd = event.getDateF();
        for (Event e : room.getEvents()) {
            if (dateStart.isBefore(e.getDateF()) && dateEnd.isAfter(e.getDateF()) || dateStart.equals(e.getDateS())) {
                return false;
            }
        }
        return true;
    }

    @Scheduled(fixedDelay = 30000)
    @Transactional
    public void scheduledAssignRoom() {
        unassignRoomIfEventEnded();
        List<Room> rooms = new ArrayList<>();
        roomRepository.findAll().forEach(rooms::add);
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().forEach(events::add);
        for (Event event : events) {
            if (event.getDateS().isAfter(LocalDateTime.now())) {
                if (event.getRoom() == null) {
                    for (Room r : rooms) {
                        if (checkDisponibilite(event, r)) {
                            rooms.remove(r);
                            event.setRoom(r);
                            eventRepository.save(event);
                            roomRepository.save(r);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void assignRoom(Event e){
        List<Room> rooms = new ArrayList<>();
        roomRepository.findAll().forEach(rooms::add);
        if (e.getDateS().isAfter(LocalDateTime.now())) {
            if (e.getRoom() == null) {
                for (Room r : rooms) {
                    if (checkDisponibilite(e, r)) {
                        rooms.remove(r);
                        e.setRoom(r);
                        eventRepository.save(e);
                        roomRepository.save(r);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void unassignRoomIfEventEnded() {
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().forEach(events::add);
        for (Event event : events) {
            if (event.getRoom() != null && (event.getDateF().isEqual(LocalDateTime.now()) || event.getDateF().isBefore(LocalDateTime.now()))) {
                Room room = event.getRoom();
                room.setAvailable(true);
                eventRepository.save(event);
                roomRepository.save(room);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////// Reservation et Paiement /////////////////////////////

    @Override
    public void AddAndAssignResevToEventAndAcc(Reservation reservation, int idEvent, int idAccount) {
        Event e = eventRepository.findById(idEvent).orElse(null);
        Account a = accountRepository.findById(idAccount).orElse(null);

        int n = e.getNbPartEvent();

        if (n != 0) {
            if (reservation.getDateRes().isBefore(e.getDateS())) {
                reservation.setEvent(e);
                reservation.setAccount(a);
                reservation.setEtatPres(true);
                reservationRepository.save(reservation);
                n = n - 1;
                e.setNbPartEvent(n);
                eventRepository.save(e);
                System.out.println("Your reservation has been successful");
            }
        }
        else {
            System.out.println("No places available yet");
        }

    }

    //////////////////////Paiement
    @Override
    public String payer(Payement paiement, Model model, int idReservation) throws StripeException {


        Reservation reservation= reservationRepository.findById(idReservation).orElse(null);
        Stripe.apiKey = "sk_test_51Mi6EOGNVtxxlKxBvh1Njx522WouDMV2sVVYA5CH3SoytWq3J34GvPMmovhScMfW7vppG3ahX3wgusNSUXhTc3Vx00jrphdDU3";

        if (reservation.getEvent().getModePay().equals(ModePay.Paid)){
            try {
                TokenCreateParams params = TokenCreateParams.builder()
                        .setCard(TokenCreateParams.Card.builder()
                                .setNumber(paiement.getStripeToken())
                                .setExpMonth(String.valueOf(2))
                                .setExpYear(String.valueOf(2025))
                                .setCvc("123")
                                .build())
                        .build();
                Token token = Token.create(params);


                ChargeCreateParams chargeParams = new ChargeCreateParams.Builder()
                        .setAmount(paiement.getMontant())
                        .setCurrency("EUR")
                        .setSource(token.getId())
                        .build();
                Charge charge = Charge.create(chargeParams);

                model.addAttribute("message", "Paiement effectué avec succès !");
                reservation.setConfirmPay(true);
                reservationRepository.save(reservation);

                String to = reservation.getAccount().getInscription().getUser().getEmail();
                String subject = "Payment Confirmation";
                String body = "Dear \n\n" + reservation.getAccount().getInscription().getUser().getFirstName() + reservation.getAccount().getInscription().getUser().getLastName() +
                        "\n" +
                        "We are writing to confirm the receipt of your payment. We appreciate your prompt action in submitting the payment."
                        + "If you have any further questions or concerns regarding your payment, please do not hesitate to contact us. We are always here to assist you in any way possible."
                        +"Best regards,\n\n" +
                        "\n\n" +
                        "Ecole supérieur privée d'ingénierie et de technologies";
                sendEmail(to,subject,body);

            } catch (StripeException | MessagingException e) {
                return model.addAttribute("message", "Une erreur est survenue lors du paiement : " + e.getMessage()).toString();
            }
            return "success";
        }
        return "event unpaid";
    }

    ///////////////////////////////////////////////////////// Certificat+QRCode+Serveur ///////////////////////////
    @Override
    public String BodyCertif(int i) {
        String s = null;
        Event e = eventRepository.findById(i).orElse(null);
        for (Reservation r : e.getReservations()) {
            if (e.getTypeEvent().equals(TypeEvent.APP0)) {
                s = "For attending the APP0 in ESPRIT , on " + e.getDateS();
            } else if (e.getTypeEvent().equals(TypeEvent.Educational_Event)) {
                s = "For attending the training " + e.getTitleEvent() + "in ESPRIT , on" + e.getDateS();
            } else if (e.getTypeEvent().equals(TypeEvent.Leisure_Event)) {
                s = "For attending the event  " + e.getTitleEvent() + "in ESPRIT , on" + e.getDateS();
            }
        }
        return s;
    }


    @Override
    public String generateUniqueId() {
        // Generate a unique ID using a secure random generator
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[10];
        secureRandom.nextBytes(randomBytes);
        return new BigInteger(1, randomBytes).toString(16);
    }

    /////////////////////////////////// Creation d'une certificat + Set base
    @Override
    public Certificate createCertificates(int idevent) throws DocumentException, IOException, WriterException, MessagingException {
        Event event = eventRepository.findById(idevent).orElse(null);
        String uniqueId = generateUniqueId();

        if (event.getDateF().isAfter(LocalDateTime.now())) {
            for (Reservation r : event.getReservations()) {
                if((event.getModePay().equals(ModePay.Paid) && r.isConfirmPay()) || event.getModePay().equals(ModePay.Unpaid) ){
                    if (r.getEtatPres()) {
                        Certificate c = new Certificate();

                        c.setTitleCertif("Certificate of participation");
                        c.setBodyCertif(BodyCertif(idevent));
                        c.setAccount(r.getAccount());
                        c.setSignatureCertif("");
                        c.setUniqueId(uniqueId);
                        certificateRepository.save(c);

                        generateCertificateok(c, event.getIdEvent());

                    }
                }
                else {
                    System.out.println("Check your payment");
                }
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<byte[]> generateCertificateok(Certificate certificat, int id) throws IOException, DocumentException, WriterException, MessagingException {

        Event e = eventRepository.findById(id).orElse(null);
        File file = new File("certificate" + certificat.getAccount().getIdAccount() + ".pdf");
        Document document = new Document();
        PdfWriter.getInstance(document, Files.newOutputStream(file.toPath()));

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("certificat", certificat);
        context.setVariable("event", e);

        String htmlContent = templateEngine.process("templates/Certificate.html", context);
        PdfWriter.getInstance(document, Files.newOutputStream(file.toPath()));
        document.open();
        ConverterProperties converterProperties = new ConverterProperties();
        HtmlConverter.convertToPdf(htmlContent, Files.newOutputStream(file.toPath()), converterProperties);


        // Upload the PDF file to Dropbox
        DbxRequestConfig config = new DbxRequestConfig("PHSET", "en_US");
        DbxClientV2 client = new DbxClientV2(config, "sl.BakBTpbvO3NfCST5gVEPrGJ_xtOadHUChdUU-j_AyoZUpiuL1XFw_82z3a9MO-nQpBsu5UouS0-Yud0PchESM1R4J0xJhkFUI-TPpWGnrYmMkJ353YKoR9MuNDkMTUYk0eqI7D6e-85Z");
        String dropboxLink = null;
        try (InputStream in = new FileInputStream(file)) {
            FileMetadata metadata = client.files().uploadBuilder("/pdfs/" + e.getIdEvent() + certificat.getAccount().getIdAccount()+ "_certificate.pdf").uploadAndFinish(in);
            dropboxLink = client.sharing().createSharedLinkWithSettings(metadata.getPathDisplay()).getUrl();
        } catch (DbxException ex) {
            throw new RuntimeException(ex);
        }

        // Generate the QR code image
        BufferedImage qrCode = QRCode.generateQRCodeImage(dropboxLink, 350, 350);
        ByteArrayOutputStream qrCodeStream = new ByteArrayOutputStream();
        ImageIO.write(qrCode, "png", qrCodeStream);
        byte[] qrCodeBytes = qrCodeStream.toByteArray();

        // Send the email with the PDF and QR code attachments
        String to = certificat.getAccount().getInscription().getUser().getEmail();
        String emailSubject = "Certificate and QR Code";
        String emailBody = "Please find attached your certificate and QR code.";
        sendEmailWithAttachment(to, emailSubject, emailBody, qrCodeBytes);

        // Read the PDF file into a byte array
        byte[] contents = Files.readAllBytes(file.toPath());

        // Set the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentDispositionFormData("attachment", "certificate.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        // Create the ResponseEntity with the PDF contents and headers


        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;

    }

    /////////////////////////////////////////////////////////  Crud PostVideo + Creaate + UploadVideo ///////////////////////////////////
    @Override
    public List<PostVideo> retrieveAllPostsVideo() {
        List<PostVideo> posts = new ArrayList<>();
        postVideoRepository.findAll().forEach(posts::add);
        return posts;
    }

    @Override
    public PostVideo addPostVideo(PostVideo p) {
        return postVideoRepository.save(p);
    }

    @Override
    public PostVideo updatePostVideo(PostVideo p) {
        return postVideoRepository.save(p);
    }

    @Override
    public PostVideo retrievePostVideo(Integer idPostV) {
        return postVideoRepository.findById(idPostV).orElse(null);
    }

    @Override
    public void removePostVideo(Integer idPostV) {
        postVideoRepository.deleteById(idPostV);

    }


    @Override
    public PostVideo createPostVid(PostVideo postVideo, int idacc, MultipartFile multipartFile) {

        Account a = accountRepository.findById(idacc).orElse(null);
        Event e = eventRepository.findDistinctFirstByTypeEvent(TypeEvent.APP0);

        if (postVideo.getDatePostVid().isBefore(e.getDateF())) {
            System.out.println("You cannot post the video");
        }
        if (postVideo.getDatePostVid().isAfter(e.getDateF().plusDays(2))) {
            System.out.println("You passed the dealind you cannot post the video");
        } else if (a.getTeam().getPostVideo() != null) {
            System.out.println("One of your team mates already post your video");
        } else {
            postVideo.setTeam(a.getTeam());
            postVideoRepository.save(postVideo);
            handleUpload(postVideo, multipartFile);
        }
        return null;
    }


    ////////////////////Uplaoad
    @Override
    public ResponseEntity<String> handleUpload(PostVideo postVideo, MultipartFile video) {
        // Vérifier que le fichier vidéo est bien sélectionné
        if (video.isEmpty()) {
            return ResponseEntity.badRequest().body("Veuillez sélectionner une vidéo");
        }

        // Configurer l'API Dropbox
        DbxRequestConfig config = new DbxRequestConfig("PHSET", "en_US");
        String accessToken = "sl.BamXK0wHs3-qcKnmFiryY3FwPhX9FVtn3kjzYly9gK0HMLZslIA92KzipG2q2aoo1kjXTqm8D3mFboigGi_sOGYrFEVtbhdfGHWIBNRNvZhVI_19KWvfiR4tralgd-DYhfxoMUMnWuQB";
        DbxClientV2 client = new DbxClientV2(config, accessToken);

        // Télécharger la vidéo sur le serveur Dropbox
        String dropboxPath = "/uploads/" + video.getOriginalFilename();
        FileMetadata metadata;
        String path = null;
        try (InputStream in = video.getInputStream()) {
            metadata = client.files().uploadBuilder(dropboxPath)
                    .withMode(WriteMode.ADD)
                    .withClientModified(new Date())
                    .uploadAndFinish(in);
            path = client.sharing().createSharedLinkWithSettings(metadata.getPathDisplay()).getUrl();
            System.out.println(path + "hahahah");
        } catch (IOException | DbxException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Une erreur s'est produite lors du téléchargement de la vidéo");
        }

        postVideo.setLinkPostVid(path);
        postVideoRepository.save(postVideo);


        return null;
    }

    /////////////////////////////////////////////////////////// Calcul Score + Winner /////////////////////////
    @Override
    //@Scheduled(fixedDelay = 30000)
    public Team CalculLikes() {
        int score = 0;
        Team winner = new Team();
        List<PostVideo> postVideos = new ArrayList<>();
        postVideoRepository.findAll().forEach(postVideos::add);
        for (PostVideo t : postVideos) {
            if (t.getDatePostVid().plusDays(2).isAfter(LocalDateTime.now())) {
                if (score < t.getNbLikePostVid()) {
                    score = t.getNbLikePostVid();
                }
            } else {
                System.out.println("Pas encore");
            }

        }
        for (PostVideo t : postVideos) {
            if (t.getNbLikePostVid() == score) {
                winner = t.getTeam();
                return winner;
            }
        }
        return null;
    }

    ///////////////////////////////////////////////////////// Planification de la journeé


    public void planEvent(int id) throws DocumentException, IOException {
        Event event = eventRepository.findById(id).orElse(null);
        List<Speaker> confirmedSpeakers = new ArrayList<>();
        event.getSpeakersconf().forEach(confirmedSpeakers::add);

        Document document = new Document();
        PdfWriter.getInstance(document, Files.newOutputStream(Paths.get("D:\\Porjet PI\\PHSET-Spring-Angular\\src\\main\\java\\com\\pidev\\phset\\services\\Plannig" + event.getIdEvent() +".pdf")));
        document.open();
        String description = event.getDescriptionEvent();
        int i =0;
        boolean dj = false;
        if (description.toLowerCase().contains("dj")) {
            dj = true;
            i++;
        }

        boolean bk = false;
        if (description.toLowerCase().contains("break")) {
            bk = true;
            i++;
        }

        boolean game = false;
        if (description.toLowerCase().contains("game")) {
            game = true;
            i++;
        }

        i= i + confirmedSpeakers.size();

        Paragraph title = new Paragraph("Event planning", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        PdfPTable table = new PdfPTable(i);
        table.setWidthPercentage(100);

        for (Speaker speaker : confirmedSpeakers) {
            //table.addCell("Speaker" + confirmedSpeakers.indexOf(speaker));
            table.addCell(""+speaker.getNameSpeaker());
        }

        if (dj) {
            table.addCell("DJ");
        }

        if (bk) {
            table.addCell("Break");
        }

        if (game) {
            table.addCell("game");
        }

        //besh nekhou sweyaa o minute
        LocalTime startTime = LocalTime.of(event.getDateS().getHour(),event.getDateS().getMinute());

        for (Speaker speaker : confirmedSpeakers) {
            LocalTime endTime = startTime.plusMinutes(15);
            table.addCell(startTime + " -> " + endTime);
            startTime = endTime;

        }
        if (dj) {
            LocalTime endTime = startTime.plusHours(1);
            table.addCell(startTime + " -> " + endTime);
            startTime = endTime;

        }
        if (bk) {
            LocalTime endTime = startTime.plusMinutes(15);
            table.addCell(startTime + " -> " + endTime);
            startTime = endTime;

        }
        if (game) {
            LocalTime endTime = startTime.plusHours(1);
            table.addCell(startTime + " -> " + endTime);
            startTime = endTime;

        }

        document.add(table);
        document.close();

    }

    //////////////////////////////////////////////////////////////// Set Presence ///////////////////////////////

    @Override // il faut connecté
    public void SetPresence(int idAcc) {
        Boolean p = true;
        Account a = accountRepository.findById(idAcc).orElse(null);
        for (Reservation r : a.getReservations()) {
            r.setEtatPres(p);
            reservationRepository.save(r);
        }
    }

    ///////////////////////////////////////////////////// Trii //////////////////////////////////////

    public List<Event> sort() {
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().forEach(events::add);

        Collections.sort(events, Comparator.comparing(Event::getDateS)
                .thenComparing(Event::getDateF)
                .thenComparing(Event::getNbPartEvent) //.reversed()
                .thenComparing(Event::getTitleEvent)
        );
        return events;
    }

    ///////////////////////////////////////////////////////// Recherche ////////////////////////////////////

    @Override
    public List<Event> searchEvents(String a, Float b, Integer c, String d, LocalDateTime e, LocalDateTime f, Mode g, ModePay h, TypeEvent i, Integer j) {
        Room m = null;
        if (j != null) {
            m = roomRepository.findById(j).orElse(null);
        }

        return eventRepository.findByTitleEventOrPriceEventOrNbPartEventOrDescriptionEventOrDateSOrDateFOrModeEventOrModePayOrTypeEventOrRoom(a, b, c, d, e, f, g, h, i, m);
    }


    //////////////////////////////////////////////////// Statistique //////////////////////////////////

    @Override
    public Map<String, Long> countEventsByType() {
        Map<String, Long> result = new HashMap<>();
        result.put("Educational event", eventRepository.countByTypeEvent(TypeEvent.Educational_Event));
        result.put("Leisure event", eventRepository.countByTypeEvent(TypeEvent.Leisure_Event));
        return result;
    }

    @Override
    public Map<String, Long> countEventsByModePay() {
        Map<String, Long> result = new HashMap<>();
        result.put("Paid", eventRepository.countByModePay(ModePay.Paid));
        result.put("Unpaid", eventRepository.countByModePay(ModePay.Unpaid));
        return result;
    }

}






