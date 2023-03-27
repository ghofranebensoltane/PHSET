package com.pidev.phset.services;

import com.pidev.phset.entities.*;
import com.pidev.phset.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class FaqAndTrainingServices implements IFaqAndTrainingServices {

    @Autowired CertificateRepository certificateRepository;
    @Autowired ChapterRepository chapterRepository;
    @Autowired TrainingOpinionRepository trainingOpinionRepository;
    @Autowired CourseRepository courseRepository;
    @Autowired ExamRepository examRepository;
    @Autowired QuestionExamRepository questionExamRepository;
    @Autowired QuestionFAQRepository questionFAQRepository;
    @Autowired ReponseExamRepository reponseExamRepository;
    @Autowired ReponseFAQRepository reponseFAQRepository;
    @Autowired TagRepository tagRepository;
    @Autowired TopicRepository topicRepository;
    @Autowired TrainingRepository trainingRepository;

    ///////////////////////////////// ********** CRUD ********** /////////////////////////////////

    /////////// ***** CERTIFICATE ***** ///////////

    @Override
    public Certificate addCertificate(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    @Override
    public Certificate updateCertificate(Integer idCertificate, Certificate certificate) {
        certificate.setIdCertif(idCertificate);
        return certificateRepository.save(certificate);
    }

    @Override
    public void removeCertificate(Integer idCertif) {
        certificateRepository.deleteById(idCertif);
    }

    @Override
    public Certificate retrieveCertificate(Integer idCertif) {
        return certificateRepository.findById(idCertif).orElse(null);
    }

    @Override
    public List<Certificate> retrieveAllCertificates() {
        return (List<Certificate>) certificateRepository.findAll();
    }

    /////////// ***** CHAPTER ***** ///////////

    @Override
    public Chapter addChapter(Chapter chapter) {
        return chapterRepository.save(chapter);
    }

    @Override
    public Chapter updateChapter(Integer idChapter, Chapter chapter) {
        return chapterRepository.save(chapter);
    }

    @Override
    public void removeChapter(Integer idChapter) {
        chapterRepository.deleteById(idChapter);
    }

    @Override
    public Chapter retrieveChapter(Integer idChapter) {
        return chapterRepository.findById(idChapter).orElse(null);
    }

    @Override
    public List<Chapter> retrieveAllChapters() {
        return (List<Chapter>) chapterRepository.findAll();
    }


    /////////// ***** COURSE ***** ///////////

    @Override
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Integer idCourse,Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void removeCourse(Integer idCourse) {
        courseRepository.deleteById(idCourse);
    }

    @Override
    public Course retrieveCourse(Integer idCourse) {
        return courseRepository.findById(idCourse).orElse(null);
    }

    @Override
    public List<Course> retrieveAllCourses() {
        return (List<Course>) courseRepository.findAll();
    }


    /////////// ***** EXAM ***** ///////////

    @Override
    public Exam addExam(Exam exam) {
        return examRepository.save(exam);
    }

    @Override
    public Exam updateExam(Integer idExam,Exam exam) {
        return examRepository.save(exam);
    }

    @Override
    public void removeExam(Integer idExam) {
        examRepository.deleteById(idExam);
    }

    @Override
    public Exam retrieveExam(Integer idExam) {
        return examRepository.findById(idExam).orElse(null);
    }

    @Override
    public List<Exam> retrieveAllExams() {
        return (List<Exam>) examRepository.findAll();
    }

    /////////// ***** QUESTIONEXAM ***** ///////////

    @Override
    public QuestionExam addQuestionExam(QuestionExam questionExam) {
        return questionExamRepository.save(questionExam);
    }

    @Override
    public QuestionExam updateQuestionExam(Integer idQuestionExam,QuestionExam questionExam) {
        return questionExamRepository.save(questionExam);
    }

    @Override
    public void removeQuestionExam(Integer idQuestionExam) {
        questionExamRepository.deleteById(idQuestionExam);
    }

    @Override
    public QuestionExam retrieveQuestionExam(Integer idQuestionExam) {
        return questionExamRepository.findById(idQuestionExam).orElse(null);
    }

    @Override
    public List<QuestionExam> retrieveAllQuestionExam() {
        return (List<QuestionExam>) questionExamRepository.findAll();
    }

    /////////// ***** QUESTIONFAQ ***** ///////////

    @Override
    public String addQuestionFAQ(QuestionFAQ questionFAQ) {
        List<QuestionFAQ> questionFAQS = retrieveAllQuestionFAQs();
        for (QuestionFAQ q:questionFAQS) {
            if (q.getTextQuestion().equals(questionFAQ.getTextQuestion().toUpperCase())){
                return ("Question aleady exist !!!");
            }else {
                questionFAQRepository.save(questionFAQ);
                questionFAQ.setTextQuestion(questionFAQ.getTextQuestion().toUpperCase());
                questionFAQRepository.save(questionFAQ);
            }
        }
        return (" Added Successfully !!! ");
    }

    @Override
    public QuestionFAQ updateQuestionFAQ(Integer idQuestionFAQ,QuestionFAQ questionFAQ) {
        return questionFAQRepository.save(questionFAQ);
    }

    @Override
    public void removeQuestionFAQ(Integer idQuestionFAQ) {
        questionFAQRepository.deleteById(idQuestionFAQ);
    }

    @Override
    public QuestionFAQ retrieveQuestionFAQ(Integer idFAQ) {
        return questionFAQRepository.findById(idFAQ).orElse(null);
    }

    @Override
    public List<QuestionFAQ> retrieveAllQuestionFAQs() {
        return (List<QuestionFAQ>) questionFAQRepository.findAll();
    }

    /////////// ***** REPONSEEXAM ***** ///////////

    @Override
    public ReponseExam addReponseExam(ReponseExam reponseExam) {
        return reponseExamRepository.save(reponseExam);
    }

    @Override
    public ReponseExam updateReponseExam(Integer idReponseExam,ReponseExam reponseExam) {
        return reponseExamRepository.save(reponseExam);
    }

    @Override
    public void removeReponseExam(Integer idReponseExam) {
        reponseExamRepository.deleteById(idReponseExam);
    }

    @Override
    public ReponseExam retrieveReponseExam(Integer idreponseExam) {
        return reponseExamRepository.findById(idreponseExam).orElse(null);
    }

    @Override
    public List<ReponseExam> retrieveAllReponseExam() {
        return (List<ReponseExam>) reponseExamRepository.findAll();
    }

    /////////// ***** REPONSEFAQ ***** ///////////

    @Override
    public ReponseFAQ addReponseFAQ(ReponseFAQ reponseFAQ) {
        return reponseFAQRepository.save(reponseFAQ);
    }

    @Override
    public ReponseFAQ updateReponseFAQ(Integer idReponseFAQ,ReponseFAQ reponseFAQ) {
        return reponseFAQRepository.save(reponseFAQ);
    }

    @Override
    public void removeReponseFAQ(Integer idReponseFAQ) {
        reponseFAQRepository.deleteById(idReponseFAQ);
    }

    @Override
    public ReponseFAQ retrieveReponseFAQ(Integer idReponseFAQ) {
        return reponseFAQRepository.findById(idReponseFAQ).orElse(null);
    }

    @Override
    public List<ReponseFAQ> retrieveAllReponseFAQs() {
        return (List<ReponseFAQ>) reponseFAQRepository.findAll();
    }

    /////////// ***** TAG ***** ///////////

    @Override
    public Tag addTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(Integer idTag,Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void removeTag(Integer idTag) {
        tagRepository.deleteById(idTag);
    }

    @Override
    public Tag retrieveTag(Integer idTag) {
        return tagRepository.findById(idTag).orElse(null);
    }

    @Override
    public List<Tag> retrieveAllTags() {
        return (List<Tag>) tagRepository.findAll();
    }

    /////////// ***** TOPIC ***** ///////////

    @Override
    public Topic addTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public Topic updateTopic(Integer idTopic,Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public void removeTopic(Integer idTopic) {
        topicRepository.deleteById(idTopic);
    }

    @Override
    public Topic retrieveTopic(Integer idTopic) {
        return topicRepository.findById(idTopic).orElse(null);
    }

    @Override
    public List<Topic> retrieveAllTopics() {
        return (List<Topic>) topicRepository.findAll();
    }

    /////////// ***** TRAINING ***** ///////////

    @Override
    public Training addTraining(Training training) {
        return trainingRepository.save(training);
    }

    @Override
    public Training updateTraining(Integer idTraining,Training training) {
        return trainingRepository.save(training);
    }

    @Override
    public void removeTraining(Integer idTraining) {
        trainingRepository.deleteById(idTraining);
    }

    @Override
    public Training retrieveTraining(Integer idTraining) {
        return trainingRepository.findById(idTraining).orElse(null);
    }

    @Override
    public List<Training> retrieveAllTrainings() {
        return (List<Training>) trainingRepository.findAll();
    }

    /////////// ***** TrainingOpinion ***** ///////////

    @Override
    public TrainingOpinion addTrainingOpinion(TrainingOpinion trainingOpinion) {
        return trainingOpinionRepository.save(trainingOpinion);
    }

    @Override
    public TrainingOpinion updateTrainingOpinion(Integer idTrainingOpinion,TrainingOpinion trainingOpinion) {
        return trainingOpinionRepository.save(trainingOpinion);
    }

    @Override
    public void removeTrainingOpinion(Integer idTrainingOpinion) {
        trainingOpinionRepository.deleteById(idTrainingOpinion);
    }

    @Override
    public TrainingOpinion retrieveTrainingOpinion(Integer idTrainingOpinion) {
        return trainingOpinionRepository.findById(idTrainingOpinion).orElse(null);
    }

    @Override
    public List<TrainingOpinion> retrieveAllTrainingOpinions() {
        return (List<TrainingOpinion>) trainingOpinionRepository.findAll();
    }




    ///////////////////////////// ********** AVNANCEES ********** /////////////////////////////////

    /***************************** haut **************************************/

    @Override
    public String addTrainingAndAssignCourse(Technology technology,Training training) {
        training.setSubjectTraining(technology);
        trainingRepository.save(training);
        Set<Course> courses = new HashSet<>();
        courses = courseRepository.getCoursesByTechnologyIs(technology);
        for (Course c : courses) {
            c.setTraining(training);
            courseRepository.save(c);
        }
        return "Added Successfully";
    }

    @Override
    public String addCourseAndChapters(Course course){
        courseRepository.save(course);
        for (Chapter c:course.getChapters()) {
            c.setCourse(course);
            chapterRepository.save(c);
        }
        courseRepository.save(course);
        return "Added Successfully";
    };

    @Override
    public String addExamAndAssignQuestionAndResponse(Exam exam){
        examRepository.save(exam);
        for (QuestionExam q : exam.getQuestionExams()) {
            q.setExam(exam);
                questionExamRepository.save(q);
            for (ReponseExam r : q.getReponsesExam()) {
                r.setQuestionExam(q);
                reponseExamRepository.save(r);
            }
        }
        return "Added Successfully";
    };

    @Override
    public String setTrainingStateAndAffectCertificate() {
        List<Training> trainings = retrieveAllTrainings();
        for (Training t : trainings) {
            for (Account account : t.getAccounts()){
                int state = 1;
                for (Course c : t.getCourses()) {
                    if (c.getExam().getScoreExam() < 10) {
                        state = 0;
                        break;
                    }
                }
                if (state == 1) {
                    t.setStateTraining(1);
                    trainingRepository.save(t);
                    Certificate c = new Certificate();
                    c.setTitleCertif("Certification " + t.getTypeTraining() + " Niveau " + t.getLevelTraining());
                    c.setUniqueId(UUID.randomUUID().toString());
                    c.setSignatureCertif("Certificat délivrée par ESPRIT pour Mr/Mme .....");

                    c.setAccount(account);
                    certificateRepository.save(c);
                }
            }
        }
        return "Affected Successfully";
    };

    public String addOpinionToTraining(Integer id, String avis){
        Training t = trainingRepository.findById(id).orElse(null);
        TrainingOpinion to = new TrainingOpinion();
        to.setTextTrainingOpinion(avis);
        to.setTraining(t);
        trainingOpinionRepository.save(to);
        return ("Added Successfully");
    };

/************************ bas ********************************************/

    @Override
    public String addResponseFAQToQuestionFAQAndAssignTopic(Integer idQ, ReponseFAQ r,Integer idT){
        QuestionFAQ q = questionFAQRepository.findById(idQ).orElse(null);
        Topic t = topicRepository.findById(idT).orElse(null);
        r.setQuestionFAQ(q);
        reponseFAQRepository.save(r);
        assert q != null;
        q.setTopic(t);
        questionFAQRepository.save(q);
        return ("Added Successfully");
    };

    @Override
    public String addTagsToQuestionFAQ(Integer idQ, String tagtext){
        int found = 0;
        QuestionFAQ q = questionFAQRepository.findById(idQ).orElse(null);
        if (q!=null){
            if(q.getTags()==null){
                Set<Tag> tagSet = new HashSet<>();
                q.setTags(tagSet);
                questionFAQRepository.save(q);
            }
            List<Tag> tags = retrieveAllTags();
            for (Tag t : tags) {
                if (t.getNameTag().equals(tagtext.toLowerCase())){
                    found = t.getIdTag() ;
                }
            }
            if(found==0){
                Tag tag = new Tag();
                tag.setNameTag(tagtext.toLowerCase());
                tagRepository.save(tag);
                q.getTags().add(tag);
                questionFAQRepository.save(q);
            }else{
                Tag t = tagRepository.findById(found).get();
                q.getTags().add(t);
                questionFAQRepository.save(q);
            }
        }else {
            return ("Question not found !!!");
        }
        return ("Added Successfully");
    };

//    @Override
//    public void extractWordFromQuestion(String question){
//        String[]Qwords = question.split("[ ',;.?!]+");
//        Set<String> words = new HashSet<>();
//        for (String w : Qwords) {
//            if (w.length()>3){
//                words.add(w);
//            }
//        }
////        for (String w:words) {
////            System.out.println(w);
////        }
//    };

    @Override
    public void searchPossiblesQuestionsResponses(String question){
        int nbreWord = 0;
        String[]Qwords = question.split("[ ',;.?!]+");
        List<String> words = new ArrayList<>();
        for (String w : Qwords) {
            if (w.length()>3){
                words.add(w);
                nbreWord++;
            }
        }
        List<Tag> tagList = new ArrayList<>();
        List<Integer> questionID = new ArrayList<>();
        List<Tag> tags = retrieveAllTags();
        for (int i=0; i<nbreWord; i++){
            for (Tag t : tags) {
                if (t.getNameTag().equals(words.get(i))){
                    tagList.add(t);
                    for (QuestionFAQ q:t.getQuestionFAQS()) {
                        questionID.add(q.getIdFAQ());
                    }
                }
            }
        }
        Map<Integer, Integer> questionCount = new HashMap<>();
        for (Integer id : questionID) {
            if (questionCount.containsKey(id)) {
                questionCount.put(id, questionCount.get(id) + 1);
            } else {
                questionCount.put(id, 1);
            }
        }
        List<Integer> sortedQuestionIDs = new ArrayList<>(questionCount.keySet());
        sortedQuestionIDs.sort((id1, id2) -> questionCount.get(id2) - questionCount.get(id1));
        if (sortedQuestionIDs.size()!=0){
            for (Integer id : sortedQuestionIDs) {
                System.out.println(retrieveQuestionFAQ(id).getTextQuestion());
                System.out.println(retrieveQuestionFAQ(id).getReponseFAQ().getNameReponseFAQ());
            }
        }else {
            System.out.println(" no results !!! ");
        }
    }

    ////////////////////////////////////////////////////////////////////

    ////////////// MAIL ///////////////

//    private JavaMailSender sender;

//    @Override
//    public String sendMail() {
//        MimeMessage message = sender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//
//        try {
//            helper.setTo("chalghoumikais3003@gmail.com");
//            helper.setText("Greetings :)");
//            helper.setSubject("Mail From Esprit");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            return "Error while sending mail ..";
//        }
//        sender.send(message);
//        return "Mail Sent Success!";
//    }

//    @Override
//    public void sendMailcertif(Integer id) {
//        Training t = trainingRepository.findById(id).orElse(null);
//        MimeMessage message = sender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//
//        try {
//            helper.setTo("chalghoumikais3003@gmail.com");
//            assert t != null;
//            helper.setText("Bonjour,"+
//                    "Felicitation !!! vous etes maintenant expert en : "+t.getTitleTraining());
//            helper.setSubject("Mail From Esprit");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            System.out.println( "Error while sending mail ..");
//        }
//        sender.send(message);
//    }

//    @Override
//    public String sendMailAttachment() throws MessagingException {
//        MimeMessage message = sender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message,true);
//        try {
//            helper.setTo("chourouk.zioud@esprit.tn");
//            helper.setText("Greetings :)\n Please find the attached docuemnt for your reference.");
//            helper.setSubject("Mail From Spring Boot");
//            ClassPathResource file = new ClassPathResource("document.PNG");
//            helper.addAttachment("document.PNG", file);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            return "Error while sending mail ..";
//        }
//        sender.send(message);
//        return "Mail Sent Success!";
//    }

    ////////////// TWILIO SMS ///////////////


//    @Override
//    public void sendReponseSMS(Integer id) {
//        QuestionFAQ questionFAQ = questionFAQRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("questionFAQ"));
//
//        // Construire le message à envoyer
//        String message = "Nouvelle reponse a votre question " + questionFAQ.getTextQuestion() ;
//
//        // Envoyer le message à l'étudiant
//        sendSMS("+21656181794", message);
//    }

//    @Override
//    public void sendSMS(String toPhoneNumber, String message) {
//        String AUTH_TOKEN = "39b3d3a62c66c6613edd6ef418096f20";
//        String ACCOUNT_SID = "AC220d43cc45898b4c0ac55e48a6e80797";
//        // Configurer les informations Twilio
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//
//        String FROM_PHONE_NUMBER = "+15073796261";
//        Message.creator(
//                new PhoneNumber(toPhoneNumber),
//                new PhoneNumber(FROM_PHONE_NUMBER),
//                message
//        ).create();
//    }
};
