package com.pidev.phset.services;

import com.pidev.phset.entities.*;
import com.pidev.phset.repositories.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@Slf4j
public class AdmissionAndRecrutementServices implements IAdmissionAndRecrutementServices {
    @Autowired IClassStateRepository classStateRepository;
    @Autowired IClassroomRepository classroomRepository;
    @Autowired IInscriptionRepository inscriptionRepository;
    @Autowired IInterviewRepository interviewRepository;
    @Autowired IOfferRepository offerRepository;
    @Autowired IUserRepository userRepository;
    @Autowired IUserAvailabilityRepository userAvailabilityRepository;
    //@Autowired AdmissionRepository admissionRepository;
    @Autowired private JavaMailSender sender;

    //////// **** CLASSSTATE Services **** ////////

    @Override
    public User addUser(User user){
        return  userRepository.save(user);
    };

    //////// **** CLASSSTATE Services **** ////////

    @Override
    public ClassState addClassState(ClassState classState) {
        return classStateRepository.save(classState);
    }

    @Override
    public ClassState updateClassState(ClassState classState) {
        return classStateRepository.save(classState);
    }

    @Override
    public void removeClassState(Integer idClassState) {
        classStateRepository.deleteById(idClassState);
    }

    @Override
    public ClassState retrieveClassState(Integer idClassState) {
        return classStateRepository.findById(idClassState).orElse(null);
    }

    @Override
    public List<ClassState> retrieveAllClassStates() {
        return (List<ClassState>) classStateRepository.findAll();
    }

    //////// **** CLASSROOM Services **** ////////

    @Override
    public Classroom addClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    @Override
    public Classroom updateClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    @Override
    public void removeClassroom(Integer idClassroom) {
        classroomRepository.deleteById(idClassroom);
    }

    @Override
    public Classroom retrieveClassroom(Integer idClassroom) {
        return classroomRepository.findById(idClassroom).orElse(null);
    }

    @Override
    public List<Classroom> retrieveAllClassroom() {
        return (List<Classroom>) classroomRepository.findAll();
    }


    //////// **** INSCRIPTION Services **** ////////

    @Override
    public Inscription addInscription(Inscription inscription) {
        return inscriptionRepository.save(inscription);
    }

    @Override
    public Inscription updateInscription(Inscription inscription) {
        return inscriptionRepository.save(inscription);
    }

    @Override
    public void removeInscription(Integer idInscription) {
        inscriptionRepository.deleteById(idInscription);
    }

    @Override
    public Inscription retrieveInscription(Integer idInscription) {
        return inscriptionRepository.findById(idInscription).orElse(null);
    }

    @Override
    public List<Inscription> retrieveAllInscription() {
        return (List<Inscription>) inscriptionRepository.findAll();
    }

    //////// **** INTERVIEW Services **** ////////

    @Override
    public Interview addInterview(Interview interview) {
        return interviewRepository.save(interview);
    }

    @Override
    public Interview updateInterview(Interview interview) {
        return interviewRepository.save(interview);
    }

    @Override
    public void removeInterview(Integer idInterview) {
        interviewRepository.deleteById(idInterview);
    }

    @Override
    public Interview retrieveInterview(Integer idInterview) {
        return interviewRepository.findById(idInterview).orElse(null);
    }

    @Override
    public List<Interview> retrieveAllInterview() {
        return (List<Interview>) interviewRepository.findAll();
    }

    //////// **** OFFER Services **** ////////

    @Override
    public Offer addOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public Offer updateOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public void removeOffer(Integer idOffer) {
        offerRepository.deleteById(idOffer);
    }

    @Override
    public Offer retrieveOffer(Integer idOffer) {
        return offerRepository.findById(idOffer).orElse(null);
    }

    @Override
    public List<Offer> retrieveAllOffer() {
        return (List<Offer>) offerRepository.findAll();
    }


    ///////////////////////// **** Algorithme  **** /////////////////////////////////////////////////////////////



    @Override
    @Transactional
    public String addInscriptionWithUserAndAssignOffer(Inscription inscription, Integer idOffer){
        Offer offer = offerRepository.findById(idOffer).orElse(null);
        String tel = inscription.getUser().getPhone();
        inscription.getUser().setInscription(inscription);
        inscription.setOffer(offer);
        inscription.setDateInscription(LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 0, 00, 00));
        inscriptionRepository.save(inscription);
        // sendSMS(tel, "Inscription bien reçu !!!");
        return ("Added Successfully :) ");
    }




//    @Override
//    public String savePDFFile(MultipartFile file) throws IOException {
//
//        // Check if the file is a PDF file
//        if (!file.getContentType().equals(MediaType.APPLICATION_PDF_VALUE)) {
//            throw new InvalidFileFormatException("Invalid file format");
//        }
//
//        // Code to save the PDF file
//        Path filePath = Paths.get("C:\\Users\\LENOVO\\Documents\\ESPRIT\\Semester 2\\PIDEV\\PHSET-Spring-Angular\\src\\main\\java\\com\\pidev\\phset\\uploadedFiles\\" + file.getOriginalFilename());
//        Files.write(filePath, file.getBytes());
//
//        return filePath.toString();
//    }


    @Override
    @Transactional
    @Scheduled(cron = "*/5 * * * * *")
    public void setAndUpdateJuryAvailabilities(){
        Set<User> allJuryList = new HashSet<>();
        allJuryList = userRepository.getAllJury();
        for (User j : allJuryList) {
            if(j.getUserAvailabilities().size()==0){ //new user
                for (int i=0; i<30; i++){ //30 jours dispo
                    UserAvailability ua = new UserAvailability();
                    ua.setDateUserAvailability(LocalDateTime.of(LocalDateTime.now().getYear(),LocalDateTime.now().getMonth(),LocalDateTime.now().plusDays(i+1).getDayOfMonth(),0,0,0));//il commence demain
                    ua.setAvailability(true);
                    ua.setUser(j);
                    userAvailabilityRepository.save(ua);
                }
            }
            else if (j.getUserAvailabilities().size()<30 && j.getUserAvailabilities().size()>1){
                List<UserAvailability> list = new ArrayList<>();
                list = userAvailabilityRepository.getAllByUserOrderByDateUserAvailabilityDesc(j);//liste des userAvailability classé par ordre dec (date)
                LocalDateTime lt = list.get(0).getDateUserAvailability();
                UserAvailability ua = new UserAvailability();
                ua.setDateUserAvailability(lt.plusDays(1));//date apres
                ua.setAvailability(true);
                ua.setUser(j);
                userAvailabilityRepository.save(ua);
            }
        }
        for (User j : allJuryList) {//date passée sera supprimé (donnees inutules)
            for (UserAvailability ua : j.getUserAvailabilities()) {
                if (ua.getDateUserAvailability().isBefore(LocalDateTime.now())) {
                    userAvailabilityRepository.deleteById(ua.getIdUserAvailability());
                }
            }
        }
        for (User j : allJuryList) {//chaque jury peut faire 4 entretien par j
            for (UserAvailability ua : j.getUserAvailabilities()) {
                LocalDateTime date = ua.getDateUserAvailability();
                int k = 0 ;
                for (Interview i : j.getInterviewJury()) {
                    if (i.getDateInterview().equals(date)){
                        k ++ ;
                    }
                }
                if (k==4){
                    ua.setAvailability(false);//deja complet ce jour la
                    userAvailabilityRepository.save(ua);
                }
            }
        }
    }

    @Override
    @Transactional
    @Scheduled(cron = "*/5 * * * * *")
    public void setAndUpdateClassAvailabilities(){
        Set<Classroom> allClassList = new HashSet<>();
        classroomRepository.findAll().forEach(allClassList::add);
        if (allClassList.size()!=0){
            for (Classroom c : allClassList) {
                if(c.getClassStates().size()==0){
                    for (int i=0; i<30; i++){
                        ClassState cs = new ClassState();
                        cs.setDate((LocalDateTime.of(LocalDateTime.now().getYear(),LocalDateTime.now().getMonth(),LocalDateTime.now().plusDays(i+1).getDayOfMonth(),0,0,0)));//commence demain
                        cs.setAvailability(true);
                        cs.setClassroom(c);
                        classStateRepository.save(cs);
                    }
                } else if (c.getClassStates().size()<30 && c.getClassStates().size()>1){
                    List<ClassState> list = new ArrayList<>();
                    list = classStateRepository.getAllByClassroomOrderByDateDesc(c);
                    LocalDateTime lt = list.get(0).getDate();
                    ClassState classState = new ClassState();
                    classState.setDate(lt.plusDays(1));
                    classState.setAvailability(true);
                    classState.setClassroom(c);
                    classStateRepository.save(classState);
                }
            }
            for (Classroom c : allClassList) {
                for (ClassState cs : c.getClassStates()) {
                    if (cs.getDate().isBefore(LocalDateTime.now())) {
                        classStateRepository.deleteById(cs.getIdClassState());
                    }
                }
            }
            for (Classroom c : allClassList) {
                for (ClassState cs : c.getClassStates()) {
                    LocalDateTime date = cs.getDate();
                    int k = 0 ;
                    for (Interview i : c.getInterviews()) {
                        if (i.getDateInterview().equals(date)){
                            k ++ ;
                        }
                    }
                    if (k==4){
                        cs.setAvailability(false);
                        classStateRepository.save(cs);
                    }
                }
            }
        }else {
            System.out.print("Pas de class !!!");
        }

    }

    /////////////ok//////////////

    @Override
    public void addInterviewAndAssignJuryAndCondidateAndClassroomToNewInscription(){
        ///////  **** JOB ****  ///////
        Set<Inscription> jobInscriptions = new HashSet<>();
        jobInscriptions = inscriptionRepository.listNewInscriptionsForJob();

        for (Inscription ins : jobInscriptions) {
            LocalDateTime dateTime = ins.getDateInscription().plusDays(1);
            List<User> availableJurys = userRepository.findAvailableJurys(dateTime);
            if (availableJurys.size() < 2) {
                throw new RuntimeException("Il n'y a pas suffisamment de jurys disponibles à cette date.");
            }
            Classroom availableRoom = classroomRepository.findAvailableRoom(dateTime);
            if (availableRoom == null) {
                throw new RuntimeException("Il n'y a pas de salle disponible à cette date.");
            }

            Interview interview = new Interview();
            interview.setRefInterview(UUID.randomUUID().toString());
            interview.setStateInterview("planifié");
            interview.setDateInterview(dateTime);
            interview.setFinInterview(dateTime.plusHours(2));
            interview.setClassroom(availableRoom);
            Set<User> twoJury = new HashSet<>();
            twoJury.add(availableJurys.get(0));
            twoJury.add(availableJurys.get(1));
            interview.setJury(twoJury);
            interview.setCondidat(ins.getUser());
            interviewRepository.save(interview);
            //sendMailInterview(dateTime,availableRoom);
        }

    }

//
//    @Override
//    public void addInterviewAndAssignJuryAndCondidateAndClassroomToNewInscription(){
//        ///////  **** JOB ****  ///////
//        Set<Inscription> jobInscriptions = new HashSet<>();
//        jobInscriptions = inscriptionRepository.listNewInscriptionsForJob();
//
//        for (Inscription ins : jobInscriptions) {
//            if (ins.getOffer().getOfferType().equals(OfferType.Job)){
//                LocalDateTime dateTime = ins.getDateInscription().plusDays(1);
//                List<User> availableJurys = userRepository.findAvailableJurys(dateTime);
//                if (availableJurys.size() < 2) {
//                    throw new RuntimeException("Il n'y a pas suffisamment de jurys disponibles à cette date.");
//                }
//                Classroom availableRoom = classroomRepository.findAvailableRoom(dateTime);
//                if (availableRoom == null) {
//                    throw new RuntimeException("Il n'y a pas de salle disponible à cette date.");
//                }
//
//                Interview interview = new Interview();
//                interview.setRefInterview(UUID.randomUUID().toString());
//                interview.setStateInterview("planifié");
//                interview.setDateInterview(dateTime);
//                interview.setFinInterview(dateTime.plusHours(2));
//                interview.setClassroom(availableRoom);
//                Set<User> twoJury = new HashSet<>();
//                twoJury.add(availableJurys.get(0));
//                twoJury.add(availableJurys.get(1));
//                interview.setJury(twoJury);
//                interview.setCondidat(ins.getUser());
//                interviewRepository.save(interview);
//                //sendMailInterview(dateTime,availableRoom);
//            } else if (ins.getOffer().getOfferType().equals(OfferType.Admission)) {
//                LocalDateTime dateTime = ins.getDateInscription().plusDays(1);
//                List<User> availableJurys = userRepository.findAvailableJurys(dateTime);
//                if (availableJurys.size() < 1) {
//                    throw new RuntimeException("Il n'y a pas de jury disponibles à cette date.");
//                }
//                Classroom availableRoom = classroomRepository.findAvailableRoom(dateTime);
//                if (availableRoom == null) {
//                    throw new RuntimeException("Il n'y a pas de salle disponible à cette date.");
//                }
//
//                Interview interview = new Interview();
//                interview.setRefInterview(UUID.randomUUID().toString());
//                interview.setStateInterview("planifié");
//                interview.setDateInterview(dateTime);
//                interview.setFinInterview(dateTime.plusHours(2));
//                interview.setClassroom(availableRoom);
//                Set<User> twoJury = new HashSet<>();
//                twoJury.add(availableJurys.get(0));
//                twoJury.add(availableJurys.get(1));
//                interview.setJury(twoJury);
//                interview.setCondidat(ins.getUser());
//                interviewRepository.save(interview);
//            }
////            else {
////             // condidature spontannee   (( envoi mail pour condidat et admin: cond= stanna lahdha admin= andek condidature ))
////            }
//        }
//
//
//    }


//    @Transactional
//    @Scheduled(cron = "*/5 * * * * *")
//    public void addInterviewAndAssignJuryAndCondidateAndClassroomAndBlocToNewInscription(){

//        int numberNewAdmissionInscription = inscriptionRepository.countNewInscriptionsForAdmission();
//        Set<Inscription> admissionInscriptions = new HashSet<>();
//        admissionInscriptions = inscriptionRepository.listNewInscriptionsForAdmission();
//
//        int numberNewSpontaneousInscription = inscriptionRepository.countNewInscriptionsForSpontaneous();
//        Set<Inscription> spontaneousInscriptions = new HashSet<>();// juste notif classement par specialite dans cv//mazelet
//        spontaneousInscriptions = inscriptionRepository.listNewInscriptionsForSpontaneous();
//
//        Set<User> listJury = new HashSet<>();
//        listJury = userRepository.getAllJury();
//
//        Set<Classroom> listclassrooms = new HashSet<>();
//        listclassrooms = classroomRepository.getAllClassroom();
//
//        ///////  **** JOB ****  ///////
//        int numberNewJobInscription = inscriptionRepository.countNewInscriptionsForJob();
//        Set<Inscription> jobInscriptions = new HashSet<>();
//        jobInscriptions = inscriptionRepository.listNewInscriptionsForJob();
//
//
//        for (Inscription ins : jobInscriptions) {
//            if (!ins.getState()){
//                LocalDateTime dateTime = ins.getDateInscription();
//                Interview interview = new Interview();
//                interview.setRefInterview(UUID.randomUUID().toString());//generation ref aleatoire
//                interview.setStateInterview("Programmed");
//                interviewRepository.save(interview);
//                int jurySession1 = 0;
//                int jurySession2 = 0;
//                int jurySession3 = 0;
//                int jurySession4 = 0;
//                int classSession1 = 0;
//                int classSession2 = 0;
//                int classSession3 = 0;
//                int classSession4 = 0;
//                int i = 0;
//                //jury dispo semaine prochaine
//                List<User> juryDispoS1 = new ArrayList<>();
//                List<User> juryDispoS2 = new ArrayList<>();
//                List<User> juryDispoS3 = new ArrayList<>();
//                List<User> juryDispoS4 = new ArrayList<>();
//                //class dispo semaine prochaine
//                List<Classroom> classDispoS1 = new ArrayList<>();
//                List<Classroom> classDispoS2 = new ArrayList<>();
//                List<Classroom> classDispoS3 = new ArrayList<>();
//                List<Classroom> classDispoS4 = new ArrayList<>();
//                while ((jurySession1<2 && classSession1<1) || (jurySession2<2 && classSession2<1) || (jurySession3<2 && classSession3<1) || (jurySession4<2 && classSession4<1)){
//                    jurySession1 = 0;//initialisation
//                    jurySession2 = 0;//initialisation
//                    jurySession3 = 0;//initialisation
//                    jurySession4 = 0;//initialisation
//                    for (User j : listJury) {
//                        for (UserAvailability ua : j.getUserAvailabilities()) { //interv semaine prochaine dispo jury
//                            if(ua.getDateUserAvailability().isAfter(dateTime.plusDays(7+i)) && ua.getAvailability() && j.getInterviewsJury().size()==0){//dispo nhar kemel
//                                juryDispoS1.add(j);
//                                jurySession1 ++;
//                            } else if (ua.getDateUserAvailability().isAfter(dateTime.plusDays(7+i)) && ua.getAvailability() && j.getInterviewsJury().size()==1) {
//                                juryDispoS2.add(j);
//                                jurySession2 ++;
//                            } else if (ua.getDateUserAvailability().isAfter(dateTime.plusDays(7+i)) && ua.getAvailability() && j.getInterviewsJury().size()==2) {
//                                juryDispoS3.add(j);
//                                jurySession3 ++;
//                            } else if (ua.getDateUserAvailability().isAfter(dateTime.plusDays(7+i)) && ua.getAvailability() && j.getInterviewsJury().size()==3) {
//                                juryDispoS4.add(j);
//                                jurySession4 ++;
//                            }
//                        }
//                    }
//                    for (Classroom c : listclassrooms) {
//                        for (ClassState cs : c.getClassStates()) {
//                            if (cs.getDate().equals(dateTime.plusDays(7+i)) && cs.isAvailability() && c.getInterviews().size()==0 && jurySession1>1){
//                                classDispoS1.add(c);
//                                classSession1 ++;
//                            } else if (cs.getDate().equals(dateTime.plusDays(7+i)) && cs.isAvailability() && c.getInterviews().size()==1 && jurySession2>1){
//                                classDispoS2.add(c);
//                                classSession2 ++;
//                            } else if (cs.getDate().equals(dateTime.plusDays(7+i)) && cs.isAvailability() && c.getInterviews().size()==2 && jurySession3>1){
//                                classDispoS3.add(c);
//                                classSession3 ++;
//                            } else if (cs.getDate().equals(dateTime.plusDays(7+i)) && cs.isAvailability() && c.getInterviews().size()==3 && jurySession4>1){
//                                classDispoS4.add(c);
//                                classSession4 ++;
//                            }
//                        }
//                    }
//                    i++;
//                }
//                if (juryDispoS1.size()>1 && classDispoS1.size()>1){
//                    interview.setDateInterview(LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth()+6+i, 8, 30, 00));
//                    interview.setClassroom(classDispoS1.get(0));
//                    interviewRepository.save(interview);
//                } else if (juryDispoS2.size()>1 && classDispoS2.size()>1) {
//                    interview.setDateInterview(LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth()+6+i, 10, 30, 00));
//                    interview.setClassroom(classDispoS2.get(0));
//                    interviewRepository.save(interview);
//                } else if (juryDispoS3.size()>1 && classDispoS3.size()>1) {
//                    interview.setDateInterview(LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth()+6+i, 13, 00, 00));
//                    interview.setClassroom(classDispoS3.get(0));
//                    interviewRepository.save(interview);
//                } else if (juryDispoS4.size()>1 && classDispoS4.size()>1) {
//                    interview.setDateInterview(LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth()+6+i, 15, 00, 00));
//                    interview.setClassroom(classDispoS4.get(0));
//                    interviewRepository.save(interview);
//                }
//                ins.setState(true);
//                inscriptionRepository.save(ins);
//            }else {
//                System.out.println("pas de nouvelle inscription !!!");
//            }
//        }
//    }

    ////////////// TWILIO SMS ///////////////

    // Configurer les informations Twilio
    private final String ACCOUNT_SID = "AC1676e5a0bd04ba492966e78317b6fbd3";
    private final String AUTH_TOKEN = "01ddc4766d8f370d6b540e7532670dd3";
    private final String FROM_PHONE_NUMBER = "+15673444249";

    /*
    @Override
    public Admission addAdmission(Admission admission) {
        return admissionRepository.save(admission);
    }

    @Override
    public void sendAcceptanceSMS(Long id) {
        Admission admission = admissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admission"));

        // Construire le message à envoyer
        String message = "Félicitations " + admission.getStudentName() + "! Vous avez été accepté dans notre cours.";

        // Envoyer le message à l'étudiant
        sendSMS(admission.getStudentPhoneNumber(), message);
    }

    @Override
    public void sendSMS(String toPhoneNumber, String message) {
        Twilio.init("AC1676e5a0bd04ba492966e78317b6fbd3", "ec6715f37b4ad7fef38941ffec35e540");

        Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(FROM_PHONE_NUMBER),
                message
        ).create();
    }

     */

    ////////////// EXTRACT TEXT FROM PDF ///////////////

    @Override
    public String extractTextFromPdf(String pdfPath) throws IOException {
        PDDocument document = PDDocument.load(new File(pdfPath));
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();
        return text;
    }

    @Override
    public ResponseEntity<String> extractText(String path) {
        try {
            String text = extractTextFromPdf(path);

            String niveauEtude = extractNiveauEtude(text);
            String specialite = extractSpecialite(text);
            String rep = suggestNiveauEtudeAndSpecialite(niveauEtude,specialite);
            return ResponseEntity.ok().body(rep);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error extracting text from PDF");
        }
    }

    @Override
    public String extractNiveauEtude(String pdfText) {
        // Recherche du motif "niveau d'étude : [niveau d'étude]" dans le texte
        Pattern pattern = Pattern.compile("Niveau d’etude : ([\\w\\+]+)");
        Matcher matcher = pattern.matcher(pdfText);

        // Si le motif est trouvé, renvoie le niveau d'étude extrait
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "null";
        }
    }

    @Override
    public String extractSpecialite(String pdfText) {
        Pattern pattern = Pattern.compile("Specialite : ([\\w\\+]+)");
        Matcher matcher = pattern.matcher(pdfText);

        // Si le motif est trouvé, renvoie le niveau d'étude extrait
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "null";
        }
    }

    @Override
    public String suggestNiveauEtudeAndSpecialite(String niveauEtude, String specialite) {
        String result="";
        if (specialite.equals("informatique") && (niveauEtude.equals("bac+6")||niveauEtude.equals("bac+5")||niveauEtude.equals("bac+4"))){
            result = "4 SAE";
        } else if (specialite.equals("informatique") && niveauEtude.equals("bac+3")) {
            result = "3 A";
        }
        return result;
    }

    ////////////// MAIL ///////////////

    @Override
    public String sendMail() {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo("chouroukzioud99@gmail.com");
            helper.setText("Greetings :)");
            helper.setSubject("Mail From Esprit");
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while sending mail ..";
        }
        sender.send(message);
        return "Mail Sent Success!";
    }

    @Override
    public void sendMailInterview(Date date,String salle,String bloc) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo("chouroukzioud99@gmail.com");
            helper.setText("Bonjour,"+
                    "Nous vous informons que votre rendez-vous est planifié"+
                    "le :"+date.toString()+" à 10h00 dans la salle "+salle+"dans le bloc "+bloc+
                    "Cordialement,");
            helper.setSubject("Mail From Esprit");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println( "Error while sending mail ..");
        }
        sender.send(message);
    }

    @Override
    public String sendMailAttachment() throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        try {
            helper.setTo("chourouk.zioud@esprit.tn");
            helper.setText("Greetings :)\n Please find the attached docuemnt for your reference.");
            helper.setSubject("Mail From Spring Boot");
            ClassPathResource file = new ClassPathResource("document.PNG");
            helper.addAttachment("document.PNG", file);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while sending mail ..";
        }
        sender.send(message);
        return "Mail Sent Success!";
    }



}