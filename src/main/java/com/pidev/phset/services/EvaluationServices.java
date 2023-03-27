package com.pidev.phset.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pidev.phset.botpressclasses.Qna;
import com.pidev.phset.botpressclasses.Qnas;
import com.pidev.phset.entities.*;
import com.pidev.phset.repositories.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationServices implements IEvaluationServices{
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private final IClaimRepository claimRepository;
    @Autowired
    private final IEvaluationRepository evaluationRepository;
    @Autowired
    private final IGridEvaluationRepository gridEvaluationRepository;
    @Autowired
    private final IMCQRepository mcqRepository;
    @Autowired
    private final IQuestionRepository questionRepository;
    @Autowired
    private final IResponseRepository responseRepository;
    @Autowired
    private final IDecissionRepository decissionRepository;
    @Autowired
    private final ITaskEvaluationRpository taskEvaluationRpository;
    @Autowired
    private final IInterviewRepository interviewRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IAccountRepository accountRepository;

    //Claim
    @Override
    public Claim addClaim(Claim claim) {

        return claimRepository.save(claim);
    }

    @Override
    public Claim retrieveClaim(int idClaim) {
        return claimRepository.findById(idClaim).orElse(null);
    }



    @Override
    public List<Claim> retrieveAllClaims() {
        List<Claim> claims = new ArrayList<>();
        claimRepository.findAll().forEach(claims::add);
        return claims;
    }

    @Override
    public Claim updateClaim(Claim claim) {
        return claimRepository.save(claim);
    }

    @Override
    public Object deleteClaim(int idClaim) {
        claimRepository.deleteById(idClaim);
        return null;
    }


    //Evaluation////////////////////////////////////////
    @Override
    public Evaluation addEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    @Override
    public Evaluation retrieveEvaluation(int idEvaluation) {
        return evaluationRepository.findById(idEvaluation).orElse(null);
    }

    @Override
    public List<Evaluation> retrieveAllEvaluations() {
        List<Evaluation> evaluations = new ArrayList<>();
        evaluationRepository.findAll().forEach(evaluations::add);
        return evaluations;
    }

    @Override
    public Evaluation updateEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    @Override
    public void deleteEvaluattion(int idEvaluation) {
        evaluationRepository.deleteById(idEvaluation);
    }

    //Grid Evaluation/////////////////////////////////////////
    @Override
    public GridEvaluation addGridEvaluation(GridEvaluation gridEvaluation) {
        return gridEvaluationRepository.save(gridEvaluation);
    }
    @Override
    public GridEvaluation retrieveGridEvaluation(int idGridEvaluation) {
        return gridEvaluationRepository.findById(idGridEvaluation).orElse(null);
    }

    @Override
    public List<GridEvaluation> retrieveAllGridEvaluations() {
        List<GridEvaluation> gridEvaluations = new ArrayList<>();
        gridEvaluationRepository.findAll().forEach(gridEvaluations::add);
        return gridEvaluations;
    }

    @Override
    public GridEvaluation updateGridEvaluation(GridEvaluation gridEvaluation) {
        return gridEvaluationRepository.save(gridEvaluation);
    }

    @Override
    public void deleteGridEvaluation(int idGridEvaluation) {
        gridEvaluationRepository.deleteById(idGridEvaluation);
    }


  //MCQ && Response && Question

    public boolean hasCorrectResponse(Set<Response> responses) {
        int i = 0;
        for (Response response : responses) {
            if (response.getCorrect()) {
                i++;
            }
        }
        if(i<2){
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public String addQuestionAndResponseAndAssignResponsestoQuestion(Question question) {
        Question q = questionRepository.findByNameQuestion(question.getNameQuestion());
        if(q==null){
            if(question.getResponses().size()==3) {
                Set<String> responseSet = new HashSet<>();
                for(Response r : question.getResponses()) {
                    responseSet.add(r.getResponse());
                }
                if(responseSet.size() != question.getResponses().size()) {
                    return "All responses must be different";
                }
                if(hasCorrectResponse(question.getResponses())) {
                    questionRepository.save(question);
                    for (Response r : question.getResponses()) {
                        r.setQuestion(question);
                        responseRepository.save(r);
                    }
                }
                else{
                    return "1 response must be right";
                }
                return "Add successfully";
            }
            else
                return "Responses must be 3";
        }
        else {
            return "Question already exists";
        }
    }

    @Override
    public MCQ addMcq(MCQ mcq) {
        return mcqRepository.save(mcq);
    }

    @Override
    public MCQ retrieveMCQ(int idMCQ) {
        return mcqRepository.findById(idMCQ).orElse(null);
    }

    @Override
    public Question retrieveQuestion(int idQuestion) {
        return questionRepository.findById(idQuestion).orElse(null);
    }

    @Override
    public List<MCQ> retrieveAllMCQ() {
        List<MCQ> mcqs = new ArrayList<>();
        mcqRepository.findAll().forEach(mcqs::add);
        return mcqs;
    }

    @Override
    public List<Question> retrieveAllQuestion() {
        List<Question> questions = new ArrayList<>();
        questionRepository.findAll().forEach(questions::add);
        return questions;
    }

    @Override
    public MCQ updateMCQ(MCQ mcq) {
        return mcqRepository.save(mcq);
    }

    @Override
    public Question updateQuestionAndResponse(Question question) {
        questionRepository.save(question);
        for (Response r: question.getResponses()) {
            responseRepository.save(r);
        }
        return question;
    }

    @Override
    public void deleteMCQ(int idMcq) {
        mcqRepository.deleteById(idMcq);
    }

    @Override
    public void deleteQuestion(int idQuestion) {
        questionRepository.deleteById(idQuestion);
    }

    @Override
    public void addEvaluationAndTaskEvaluationAndAssignTaskToEvaluataion(Evaluation evaluation) {
        evaluationRepository.save(evaluation);
        for (TaskEvaluation t : evaluation.getTaskEvaluation()){
            t.setEvaluation(evaluation);
            taskEvaluationRpository.save(t);
        }
        System.out.println("add Succefully");
    }

    //////////////////////////////////////////////////// Auto Grid + Auto MCQ ////////////////////////////////////////////////////

    @Override
    public GridEvaluation addAutoGrid(Interview interview){
        GridEvaluation gridEvaluation = new GridEvaluation();
        List<Evaluation> evaluations = new ArrayList<>();
        evaluationRepository.findAll().forEach(evaluations::add);
        if(interview.getTypeInterview().equals(TypeGrid.Job)) {
            gridEvaluation.setTypeGrid(TypeGrid.Job);
            gridEvaluationRepository.save(gridEvaluation);
            for (Evaluation e : evaluations) {
                if (e.getTypeEvaluation().equals(TypeGrid.Job)) {
                    if (e.getGridEvaluations().size() == 0) {
                        Set<GridEvaluation> g = new HashSet<>();
                        g.add(gridEvaluation);
                        e.setGridEvaluations(g);
                    } else {
                        e.getGridEvaluations().add(gridEvaluation);
                    }
                    evaluationRepository.save(e);
                }
            }
        }
        else if(interview.getTypeInterview().equals(TypeGrid.Admission)){
            gridEvaluation.setTypeGrid(TypeGrid.Admission);
            gridEvaluationRepository.save(gridEvaluation);
            for(Evaluation e : evaluations){
                if(e.getTypeEvaluation().equals(TypeGrid.Admission)){
                    if(e.getGridEvaluations().size()==0){
                        Set<GridEvaluation> g = new HashSet<>();
                        g.add(gridEvaluation);
                        e.setGridEvaluations(g);
                    }
                    else{
                        e.getGridEvaluations().add(gridEvaluation);
                    }
                    evaluationRepository.save(e);
                }
            }
        }
        interview.setGridEvaluation(gridEvaluation);
        interviewRepository.save(interview);

        for(User u : userRepository.findByRole(Role.ROLE_Professor)){
            if(u.getInterviewJury().contains(interview)){
                String to = u.getEmail();
                String object = "Grid evaluation interview";
                String body = "Hello," + u.getFirstName() + " " + u.getLastName() + "\n"+
                        "The evaluation grid attached to interview " + interview.getRefInterview() + " is successfully generated.\n" +
                        "Consult your interview list for further information ";
                try {
                    sendEmail(to,object,body);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return gridEvaluation;
    }

    @Override
    public List<Question> getRandomQuestions(List<Question> questions, int numQuestions) {
        List<Question> randomQuestions = new ArrayList<>();
        Random rand = new Random();
        int listSize = questions.size();
        if (numQuestions > listSize) {
            return questions;
        }
        Set<Integer> selectedIndices = new HashSet<>();
        while (selectedIndices.size() < numQuestions) {
            int randomIndex = rand.nextInt(listSize);
            if (!selectedIndices.contains(randomIndex)) {
                selectedIndices.add(randomIndex);
                randomQuestions.add(questions.get(randomIndex));
            }
        }
        return randomQuestions;
    }


    @Override
    public List<MCQ> addAutoQcm(Interview interview) {
        if(interview.getTypeInterview().equals(TypeGrid.Admission)){
            List<MCQ> mcqs = new ArrayList<>();
            MCQ mcq1 = new MCQ();
            mcq1.setTitle("MCQ of language skills");
            mcq1.setTypeTest(TypeTest.Language_Skills);
            MCQ mcq2 = new MCQ();
            mcq2.setTitle("MCQ of general culture");
            mcq2.setTypeTest(TypeTest.General_Culture);
            mcq1.setInterview(interview);
            mcq2.setInterview(interview);

            mcqRepository.save(mcq1);
            mcqRepository.save(mcq2);
            /*------------- */

            List<Question> LanguageSkillsEasy = questionRepository.findByTypeTestAndQuestionLevel(TypeTest.Language_Skills, QuestionLevel.Easy);
            getRandomQuestions(LanguageSkillsEasy, 4).forEach(question -> {
                question.getMcqs().add(mcq1);
                questionRepository.save(question);
            });
            List<Question> LanguageSkillsHard = questionRepository.findByTypeTestAndQuestionLevel(TypeTest.Language_Skills, QuestionLevel.Hard);
            getRandomQuestions(LanguageSkillsHard, 4).forEach(question -> {
                question.getMcqs().add(mcq1);
                questionRepository.save(question);
            });
            List<Question> LanguageSkillsMeduim = questionRepository.findByTypeTestAndQuestionLevel(TypeTest.Language_Skills, QuestionLevel.Medium);
            getRandomQuestions(LanguageSkillsMeduim, 4).forEach(question -> {
                question.getMcqs().add(mcq1);
                questionRepository.save(question);
            });
            /*------------- */
            List<Question> GeneralCulture = questionRepository.findByTypeTest(TypeTest.General_Culture);
            getRandomQuestions(GeneralCulture, 12).forEach(question -> {
                question.getMcqs().add(mcq2);
                questionRepository.save(question);
            });
            mcqs.add(mcq1);
            mcqs.add(mcq2);
            String to = interview.getCondidat().getEmail();
            String object = "Interview Admission";
            String body = "Hello," + interview.getCondidat().getFirstName() + " " + interview.getCondidat().getLastName() +"\n" +
                    "Thank you for your interest in ESPRIT.\n" +
                    "We have received your pre-registration request which has been processed by our information system and registered under :" + interview.getCondidat().getFirstName() + " " + interview.getCondidat().getLastName()+"\n"+
                    "Conduct of the Online Interview :\n" +
                    "\n" +
                    "two tests: one MCQ of language skills and one MCQ of general culture \n" +
                    "\n" +
                    "Your interview date is :" + interview.getDateInterview() +"\n"+
                    "N.B: Ceci est un mail automatique Merci de ne pas répondre.\n"+
                    "© ESPRIT - Ecole Supérieure Privée d'Ingénierie et de Technologies - \n" +
                    "Zone Industrielle Chotrana II - BP 160 - Pôle Technologique EL GHAZALA - 2083 - Ariana ";
            try {
                sendEmail(to,object,body);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            return mcqs;
        }else{
            return null;
        }
    }

    //////////////////////////////////////////////////// Add Claim + Add Decission ////////////////////////////////////////////////////
    @Override
    public void addAndAssignDecissionToClaim(int idclaim, Decission decission,EtatClaim etatClaim) {
        Claim claim = claimRepository.findById(idclaim).orElse(null);
        decissionRepository.save(decission);
        claim.setDecission(decission);
        claim.setEtat(etatClaim);
        claimRepository.save(claim);
        User user = claim.getAccount().getInscription().getUser();

        emailClaimChanged(user, claim);
     }


    @Override
    public void addClaimaAndAssignAccount(Claim claim,MultipartFile file) throws IOException{
        if(file!=null){
            byte[] bytes = file.getBytes();
            claim.setImage(bytes);
        }
        claimRepository.save(claim);
    }

    //////////////////////////////////////////////////// Calculate Score Interview ////////////////////////////////////////////////////

    public int calculateScore(Set<Response> responses) {
        int score = 0;
        for (Response response : responses) {
            if (response.getCorrect()) {
                score++;
            }
        }
        return score;
    }


    // A vérifier
    @Override
    public void setScoreToMCQ(int idInterview, int idMCQ, float score){
        Interview interview = interviewRepository.findById(idInterview).orElse(null);
        MCQ mcq = mcqRepository.findById(idMCQ).orElse(null);
        if(mcq != null && interview != null){
            for(MCQ m : interview.getMcqs()){
                if(m.getTypeTest().equals(mcq.getTypeTest())){
                    m.setScore(score);
                    mcqRepository.save(m);
                }
            }
        }
    }

    // Score correct
    public void scoreqcm(int idMcq, float score){
        MCQ mcq1 =  mcqRepository.findById(idMcq).orElse(null);
        if(mcq1.getInterview().getDateInterview().isAfter(LocalDateTime.now())){
            mcq1.setScore(score);
            mcqRepository.save(mcq1);
        }
    }

    @Override
    public void setScoreToTaskEvaluation(TaskEvaluation taskEvaluation,float note, String remark){
        taskEvaluation.setNote(note);
        taskEvaluation.setRemark(remark);
        taskEvaluationRpository.save(taskEvaluation);
    }

    @Override
    public void calculScoreGrid(int idGrid){
        float note = 0;
        GridEvaluation gridEvaluation = gridEvaluationRepository.findById(idGrid).orElse(null);
        for(Evaluation evaluation : gridEvaluation.getEvaluations()){
            for(TaskEvaluation taskEvaluation : evaluation.getTaskEvaluation()){
                note =+ taskEvaluation.getNote();
            }
        }
        gridEvaluation.setScoreGrid(note);
    }


    @Override
    public void assignScoreToInterview() throws MessagingException {
        for(Interview interview : interviewRepository.findAll()){
            float score=0;
            for (MCQ mcq : interview.getMcqs()){
                score =+ mcq.getScore();
            }
            if(interview.getGridEvaluation()!=null){
                score =+ interview.getGridEvaluation().getScoreGrid();
            }
            interview.setNoteFinal(score);
            if(interview.getNoteFinal()>=12){
                interview.setEtatInterview(EtatInterview.Accepted);
                emailPayement(interview);
            }
            else if( interview.getNoteFinal() < 12 && interview.getNoteFinal() >= 8 ) /*&& interview.getNoteFinal()>=8*/{
                interview.setEtatInterview(EtatInterview.Pending);
                emailPending(interview);
            }
            else{
                interview.setEtatInterview(EtatInterview.Rejected);
                emailRefused(interview);
            }
            interviewRepository.save(interview);
        }

    }

    //////////////////////////////////////////////////// Email ////////////////////////////////////////////////////

    @Override
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);

        mailSender.send(message);
    }

    public void emailPayement(Interview interview){

        int annee = LocalDateTime.now().getYear();
        int fraisInscription = 7700 + (annee - 2021) * 200;
        int tva = (int) Math.round(fraisInscription * 0.07);

        String to = interview.getCondidat().getEmail();
        String object = "ESPRIT Entrance exam results\n";
        StringBuilder message = new StringBuilder();
        message.append(interview.getCondidat().getFirstName() + " " + interview.getCondidat().getLastName());
        message.append("We are pleased to inform you that you have been accepted at ESPRIT.\n\n");
        message.append("Here is the procedure to follow to confirm your registration:\n\n");
        message.append("1- Paying tuition fees :\n\n");
        message.append("a. Amount :\n");
        message.append("The tuition fees are").append(fraisInscription).append(" Dinars per year of which ").append(tva).append(" of VAT. They are payable in two installments.\n\n");
        message.append("b. Timeline :\n");
        message.append("   - A 1st tranche of ").append(fraisInscription / 2).append(" DT payable before 09/08/").append(annee).append(" immediately and required at registration,\n");
        message.append("   - A 2nd tranch of ").append(fraisInscription / 2).append(" DT payable before January 15 ").append(annee + 1).append(".\n\n");
        message.append("c. Means of payment :\n\n");
        message.append("-Payment by credit card via the link https://esprit-tn.com/ESPOnline/Online/Login.aspx\n\n");
        /*
        message.append("- Deposit of cash in the bank: this operation can be carried out at all AMEN BANK branches by presenting this letter and indicating your name and your identifier ").append(identifiant).append("on the payment order\n\n");
        message.append("- Payment by bank or postal check to be deposited with the school's financial department.\n\n");
        message.append("NB : You must take care to indicate the name and the identifier ").append(identifiant).append(" on the payment order.\n\n");
        message.append("2-Registration information to be filled in:\n\n");
        message.append("Step 1:\n\n");
        message.append("Access your account (via your computer) through the link depot.esprit-tn.com to file your application online using the data below:\n\n");
        message.append("Identifier :          ").append(identifiant).append("\n");
        message.append("Your password :     07240679\n\n");
        */

        message.append("Step 2:\n\n");
        message.append("1. Fill in the missing information using this link https://esprit-tn.com/admission/ACCES_CONF.aspx <br>");
        message.append("2. Read and accept the school rules https://esprit.tn/formation/esprit-ingenieur/reglement-scolaire<br>");
        message.append("3. Confirm everything by saving your information (button 'save')<br><br>\"");

        try {
            sendEmail(to,object,message.toString());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void emailPending(Interview interview){
        String to = interview.getCondidat().getEmail();
        String object = "ESPRIT Entrance Exam Results - Placement on Waitlist\n";
        StringBuilder message = new StringBuilder();
        message.append("Dear ").append(interview.getCondidat().getFirstName()).append(" ").append(interview.getCondidat().getLastName()).append(",\n\n");
        message.append("We would like to inform you that we have completed the review of your application for admission to ESPRIT, and we regret to inform you that we are unable to offer you a place in our program at this time.\n\n");
        message.append("However, we were impressed by your application and believe that you would make an excellent addition to our institution. As such, we would like to offer you a place on our waitlist. This means that you are still being considered for admission and we will contact you as soon as a space becomes available.\n\n");
        message.append("We understand that this may be disappointing news, but we encourage you to remain positive and to continue pursuing your academic and professional goals. We wish you all the best in your future endeavors.\n\n");
        message.append("Sincerely,\n\n");
        message.append("The ESPRIT Admissions Committee");

        try {
            sendEmail(to, object, message.toString());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void emailRefused(Interview interview){
        String to = interview.getCondidat().getEmail();
        String object = "ESPRIT Entrance Exam Results - Admission Refusal\n";
        StringBuilder message = new StringBuilder();
        message.append("Dear ").append(interview.getCondidat().getFirstName()).append(" ").append(interview.getCondidat().getLastName()).append(",\n\n");
        message.append("We regret to inform you that your application for admission to ESPRIT has been unsuccessful.\n\n");
        message.append("We appreciate the time and effort you put into your application, and we understand that this news may be disappointing. Please know that our decision was made after careful consideration of your application and the other candidates we received.\n\n");
        message.append("We encourage you to continue pursuing your academic and professional goals, and we wish you all the best in your future endeavors.\n\n");
        message.append("Thank you for considering ESPRIT as a potential institution for your studies.\n\n");
        message.append("Sincerely,\n\n");
        message.append("The ESPRIT Admissions Committee");

        try {
            sendEmail(to, object, message.toString());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void emailEvaluator(Interview interview, User user){
        String to = user.getEmail();
        String object = "ESPRIT Entrance Exam Interview Invitation\n";
        StringBuilder message = new StringBuilder();
        message.append("Dear ").append(user.getFirstName()).append(" ").append(user.getLastName()).append(",\n\n");
        message.append("We would like to invite you to participate in an interview for the ESPRIT entrance exam.\n\n");
        message.append("The interview is scheduled at ").append(interview.getDateInterview()).append(",\n\n");
        message.append("The candidate you will be evaluating is ").append(interview.getCondidat().getFirstName()).append(" ").append(interview.getCondidat().getLastName()).append(".\n\n");
        message.append("Please confirm your availability for this interview via the forum\n\n");
        message.append("Thank you for your time and for your contribution to the ESPRIT admissions process.\n\n");
        message.append("Sincerely,\n\n");
        message.append("The ESPRIT Admissions Committee");
        try {
            sendEmail(to, object, message.toString());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public void emailClaimChanged(User user, Claim claim){
        String body = "Good morining," + user.getFirstName() + " " + user.getLastName() +".\n"
                + " Your claim about : '" + claim.getObject() + "' is already modified.\n"+
                "Please check your claims to more informations";
        try {
            sendEmail(user.getEmail(),"Important: your claim decision has been changed",body);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    //////////////////////////////////////////////////// Assign evaluator to interview ////////////////////////////////////////////////////
    @Override
    public boolean checkDisponibilite(User user, Interview interview) {
        LocalDateTime startInterview = interview.getDateInterview();
        LocalDateTime endInterview = interview.getFinInterview();
        for (Interview listInterviews : user.getInterviewJury()) {
            LocalDateTime startUserInterview = listInterviews.getDateInterview();
            LocalDateTime endUserInterview = listInterviews.getFinInterview();
            if ((startInterview.isAfter(startUserInterview) && startInterview.isBefore(endUserInterview))
                    || (endInterview.isAfter(startUserInterview) && endInterview.isBefore(endUserInterview))
                    || startInterview.isEqual(startUserInterview)
                    || endInterview.isEqual(endUserInterview)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String assignEvaluator(Interview interview) {
        Set<User> evaluateursDisponibles = new HashSet<>();
        List<User> professors = userRepository.findByRole(Role.ROLE_Professor);
        if(interview.getDateInterview().isAfter(LocalDateTime.now())){
            if(interview.getJury()==null || interview.getJury().size()<3 && interview.getJury().size()!=2){
                for (User e : professors) {
                    if (checkDisponibilite(e, interview)) {
                        evaluateursDisponibles.add(e);
                        if (evaluateursDisponibles.size() == 3) {
                            break;
                        }
                    }
                }
                for(User u : evaluateursDisponibles){
                    if(u.getInterviewJury().size()==0){
                        Set<Interview> newinterview = new HashSet<>();
                        newinterview.add(interview);
                        u.setInterviewJury(newinterview);
                    }
                    else {
                        u.getInterviewJury().add(interview);
                    }
                    userRepository.save(u);
                }
            }
            if (interview.getJury() != null) {
                if(interview.getJury().size()==2){
                    for (User e : professors) {
                        if (checkDisponibilite(e, interview)) {
                            e.getInterviewJury().add(interview);
                            emailEvaluator(interview, e);
                            userRepository.save(e);
                            break;
                        }
                    }
                }
            }
            addAutoGrid(interview);
        }
        else {
            System.out.println("Date interview not correct");
        }
        return null;
    }

    //////////////////////////////////////////////////// Tri search claim by date ////////////////////////////////////////////////////
    @Override
    public List<Claim> sortClaimByDate() {
        List<Claim> claims = new ArrayList<>();
        claimRepository.findAll().forEach(claims::add);
        Collections.sort(claims, Comparator.comparing(Claim::getDateClaim));
        return claims;
    }

    //////////////////////////////////////////////////// Search claims by account ////////////////////////////////////////////////////
    @Override
    public List<Claim> getClaimsByAccount(int accountId) {
        Account a = accountRepository.findById(accountId).orElse(null);
        return claimRepository.findByAccount(a);
    }

    //////////////////////////////////////////////////// Tandance reclamation ////////////////////////////////////////////////////
    @Override
    public String tendanceReclamation() {
        List<Claim> claims = new ArrayList<>();
        claimRepository.findClaimsByEtatNotAndEtatNot(EtatClaim.Refused,EtatClaim.Traited).forEach(claims::add);
        if (claims == null || claims.isEmpty()) {
            return "";
        }
        Map<String, Integer> claimsObjet = new HashMap<>();

        for (Claim claim : claims) {
            String objet = claim.getObject();
            if (claimsObjet.containsKey(objet)) {
                claimsObjet.put(objet, claimsObjet.get(objet) + 1);
            } else {
                claimsObjet.put(objet, 1);
            }
        }

        String objetMax = null;
        int nombreClaimsMax = 0;

        for (Map.Entry<String, Integer> entry : claimsObjet.entrySet()) {
            String objet = entry.getKey();
            int nombreReclamations = entry.getValue();
            if (nombreReclamations > nombreClaimsMax) {
                objetMax = objet;
                nombreClaimsMax = nombreReclamations;
            }
        }

        return "Tandance of claims is : " + objetMax;
    }

    //Priorité reclamation
    @Override
    public List<Claim> getClaimsSortedByPriority() {
        List<Claim> claims = new ArrayList<>();
        claimRepository.findAll().forEach(claims::add);

        Map<String, Integer> priorities = new HashMap<>();

        priorities.put("Email", 1);
        priorities.put("APP0 Video", 2);
        priorities.put("Note", 3);
        priorities.put("Post FAQ", 4);
        priorities.put("Certificat", 5);
        priorities.put("Other complaint",6);

        Collections.sort(claims, new Comparator<Claim>() {
            @Override
            public int compare(Claim c1, Claim c2) {
                int a = priorities.get(c1.getObject());
                int b = priorities.get(c2.getObject());

                return Integer.compare(a, b);
            }
        });
        return claims;
    }

    //////////////////////////////////////////////////// Advanced search ////////////////////////////////////////////////////
    @Override
    public List<Claim> searchClaims(Date a, String b, String c , EtatClaim d){
        return claimRepository.findClaimsByDateClaimOrAccount_Inscription_User_FirstNameOrObjectOrEtat(a, b, c, d);
    }


    @Override
    public List<MCQ> searchMcq(String a, Float b, TypeTest c, Integer d){
        Interview e = null;
        if(d!=null){
            e=interviewRepository.findById(d).orElse(null);
        }
        return mcqRepository.findByTitleOrScoreOrTypeTestOrInterview(a,b,c,e);
    }

    //////////////////////////////////////////////////// Statisitique Simple claim ////////////////////////////////////////////////////
    @Override
    public Map<String, Long> countClaimsByState() {
        Map<String, Long> result = new HashMap<>();
        result.put("Traited", claimRepository.countByEtat(EtatClaim.Traited));
        result.put("No_Traited", claimRepository.countByEtat(EtatClaim.No_Traited));
        result.put("Refused", claimRepository.countByEtat(EtatClaim.Refused));
        return result;
    }

    @Override
    public List<Interview> findInterviewProf(int idProf){
        User prof = userRepository.findById(idProf).orElse(null);
        List<Interview> interviews = new ArrayList<>();
        for(Interview i : interviewRepository.findAll()){
            for(User user: i.getJury()){
                if(user==prof){
                    interviews.add(i);
                }
            }
        }
        return interviews;
    }

    @Override
    public void desafecterProfInterview(int idInterview, int idProf){
        User prof = userRepository.findById(idProf).orElse(null);
        Interview interview = interviewRepository.findById(idInterview).orElse(null);

        prof.getInterviewJury().remove(interview);
        userRepository.save(prof);
        List<User> professors = userRepository.findByRole(Role.ROLE_Professor);
        professors.remove(prof);
        for (User e : professors) {
            if (checkDisponibilite(e, interview)) {
                emailEvaluator(interview, e);
                e.getInterviewJury().add(interview);
                userRepository.save(e);
                break;
            }
        }

    }

    //////////////////////////////////////////////////// ChatBot ////////////////////////////////////////////////////

    @Override
    public String getAnswer(String question) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Qnas qnas = objectMapper.readValue(new File("src/main/resources/botpress/qna_06-03-2023.json"), Qnas.class);
        int maxDistance = 3;
        for (Qna qna : qnas.getQnas()) {
            List<String> questions = qna.getData().getQuestions().getEn();
            for (String q : questions) {
                int distance = LevenshteinDistance.getDefaultInstance().apply(q, question);
                if (distance <= maxDistance) {
                    return qna.getData().getAnswers().getEn().get(0);
                }
            }
        }
        return "Could you please provide more context or information so I can better understand and provide a response?";
    }



    //////////////////////////////////////////////////// EXCEL ////////////////////////////////////////////////////

    @Override
    //@Scheduled(cron = "* * * 1 * *")
    public void exportLastMonthTraitdClaimsToExcel() {
        LocalDateTime start = LocalDateTime.now().minusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<Claim> claims = claimRepository.findProcessedClaimsForLastMonth(start, end);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Traited claims"+ LocalDate.now().minusMonths(1).format(formatter));

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Account");
        headerRow.createCell(2).setCellValue("Classroom");
        headerRow.createCell(3).setCellValue("Objet");
        headerRow.createCell(4).setCellValue("content");
        headerRow.createCell(5).setCellValue("Claim date");
        headerRow.createCell(6).setCellValue("Etat claim");
        headerRow.createCell(7).setCellValue("Decision date");
        headerRow.createCell(8).setCellValue("Decision");

        int rowNum = 1;
        for (Claim claim : claims) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(claim.getIdClaim());
            if (claim.getAccount() != null) {
                row.createCell(1).setCellValue(claim.getAccount().getInscription().getUser().getFirstName()+" " + claim.getAccount().getInscription().getUser().getLastName());
                row.createCell(2).setCellValue("  ");
            }
            row.createCell(3).setCellValue(claim.getObject());
            row.createCell(4).setCellValue(claim.getContent());
            row.createCell(5).setCellValue(claim.getDateClaim().toString());
            row.createCell(6).setCellValue(claim.getEtat().toString());
            if (claim.getDecission() != null) {
                row.createCell(7).setCellValue(claim.getDecission().getDateDecission().toString());
                row.createCell(8).setCellValue(claim.getDecission().getDecission());
            }
            claimRepository.delete(claim);
        }
        String fileName = "D:\\4eme_sem_2\\PIDev Dev\\PHSET\\src\\main\\java\\com\\pidev\\phset\\services\\TraitedClaims" + LocalDate.now().minusMonths(1).format(formatter) + ".xlsx";
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    //@Scheduled(cron = "* * * 1 * *")
    public void updateOldExcelFiles (){
        LocalDateTime date = LocalDateTime.now();//.minusMonths(2).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<Claim> claims = claimRepository.findClaimsByDateClaimIsBeforeAndEtatOrEtat(date, EtatClaim.Traited, EtatClaim.Refused);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        String fileName = "D:\\4eme_sem_2\\PIDev Dev\\PHSET\\src\\main\\java\\com\\pidev\\phset\\services\\TraitedClaims" + LocalDate.now().minusMonths(1).format(formatter) + ".xlsx";
        try (FileInputStream inputStream = new FileInputStream(fileName); Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheet("Traited claims"+ LocalDate.now().minusMonths(1).format(formatter));

            int rowNum = sheet.createRow(sheet.getLastRowNum() + 1).getRowNum();
            for (Claim claim : claims) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(claim.getIdClaim());
                if (claim.getAccount() != null) {
                    row.createCell(1).setCellValue(claim.getAccount().getInscription().getUser().getFirstName()+" " + claim.getAccount().getInscription().getUser().getLastName());
                    row.createCell(2).setCellValue("  ");
                }
                row.createCell(3).setCellValue(claim.getObject());
                row.createCell(4).setCellValue(claim.getContent());
                row.createCell(5).setCellValue(claim.getDateClaim().toString());
                row.createCell(6).setCellValue(claim.getEtat().toString());
                if (claim.getDecission() != null) {
                    row.createCell(7).setCellValue(claim.getDecission().getDateDecission().toString());
                    row.createCell(8).setCellValue(claim.getDecission().getDecission());
                }
                claimRepository.delete(claim);
            }
            try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //////////////////////////////////////////////////// PDF ////////////////////////////////////////////////////

    @Override
    public List<Claim> getAllClaimsFromExcelFiles(){
        List<Claim> claims = new ArrayList<>();
        for(int i=1 ; i<13 ; i++){
            String fileName = null;
            if(i>=1 && i<=9){
                fileName = "D:\\4eme_sem_2\\PIDev Dev\\PHSET\\src\\main\\java\\com\\pidev\\phset\\services\\TraitedClaims"+LocalDate.now().getYear()+"-0"+i+ ".xlsx";
            }
            else if(i>9){
                fileName = "D:\\4eme_sem_2\\PIDev Dev\\PHSET\\src\\main\\java\\com\\pidev\\phset\\services\\TraitedClaims"+LocalDate.now().getYear()+"-"+i+ ".xlsx";
            }
            if(fileName!=null){
                try (FileInputStream inputStream = new FileInputStream(fileName); Workbook workbook = WorkbookFactory.create(inputStream)) {
                    Sheet sheet = workbook.getSheetAt(0);
                    Iterator<Row> iterator = sheet.iterator();
                    while (iterator.hasNext()) {
                        Row row = iterator.next();
                        if (row.getRowNum() == 0) {
                            continue;
                        }
                        if(row.getCell(0)!=null){
                            int id=0;
                            if (row.getCell(0).getCellType() == CellType.NUMERIC) {
                                id = (int) row.getCell(0).getNumericCellValue();
                            } else if (row.getCell(0).getCellType() == CellType.STRING) {
                                id = Integer.parseInt(row.getCell(0).getStringCellValue());
                            }
                            LocalDateTime dateClaim = null;
                            if (row.getCell(5).getCellType() == CellType.NUMERIC) {
                                dateClaim = row.getCell(5).getLocalDateTimeCellValue();
                            } else if (row.getCell(5).getCellType() == CellType.STRING) {
                                dateClaim = LocalDateTime.parse(row.getCell(5).getStringCellValue());
                            }
                            String etat = row.getCell(6).getStringCellValue();
                            LocalDateTime dateDecission = null;
                            if (row.getCell(7).getCellType() == CellType.NUMERIC) {
                                dateDecission = row.getCell(7).getLocalDateTimeCellValue();
                            } else if (row.getCell(7).getCellType() == CellType.STRING) {
                                dateDecission = LocalDateTime.parse(row.getCell(7).getStringCellValue());
                            }
                            Claim claim = new Claim();
                            claim.setIdClaim(id);
                            claim.setDateClaim(dateClaim);
                            Decission decission = new Decission();
                            decission.setDateDecission(dateDecission);
                            claim.setDecission(decission);
                            if(etat.equals(EtatClaim.Traited.toString())){
                                claim.setEtat(EtatClaim.Traited);
                            }
                            else if(etat.equals(EtatClaim.No_Traited.toString())){
                                claim.setEtat(EtatClaim.No_Traited);
                            }
                            else {
                                claim.setEtat(EtatClaim.Refused);
                            }
                            claims.add(claim);
                        }
                    }
                    workbook.close();
                    return claims;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return claims;
    }
    @Override
   // @Scheduled(cron = "0 58 23 12 31 *")
    public void generateClaimReport() throws IOException, DocumentException {
        List<Claim> claims =  getAllClaimsFromExcelFiles();
        int year = LocalDateTime.now().getYear();
        int traited = 0;
        int refused =0;
        long totalResponseTime = 0;
        int numClaims = 0;

        for (Claim claim : claims) {
            if (claim.getDecission() != null && claim.getDateClaim().getYear() == year) {
                if (claim.getEtat().equals(EtatClaim.Traited)) {
                    traited++;
                } else if (claim.getEtat().equals(EtatClaim.Refused)) {
                    refused++;
                }
                LocalDateTime dateDecision = claim.getDecission().getDateDecission();
                if (dateDecision != null) {
                    LocalDateTime dateClaim = claim.getDateClaim();
                    if (dateClaim != null) {
                        Duration responseTime = Duration.between(dateClaim, dateDecision);
                        totalResponseTime += responseTime.toMillis();
                        numClaims++;
                    }
                }
            }
        }

        Duration duration = Duration.parse(Duration.ofMillis(totalResponseTime / numClaims).toString());
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("D:\\4eme_sem_2\\PIDev Dev\\PHSET\\src\\main\\java\\com\\pidev\\phset\\services\\claim_report"+ year +".pdf"));
        document.open();

        Paragraph title = new Paragraph("Claim performance report", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph sectionTitle = new Paragraph("Performance claim response in " + year + "\n\n");
        sectionTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(sectionTitle);
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);

        //Table
        table.addCell("Traited");
        table.addCell("Refused");
        table.addCell("Response duration average");
        table.addCell(String.valueOf(traited));
        table.addCell(String.valueOf(refused));
        table.addCell(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        document.add(table);

        Paragraph line = new Paragraph("\n\n\n\n");
        document.add(line);

        //Chart
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Traited", traited);
        dataset.setValue("Refused", refused);
        JFreeChart chart = ChartFactory.createPieChart("Claim performance statique", dataset);
        BufferedImage bufferedImage = chart.createBufferedImage(500, 500);
        Image chartImage = Image.getInstance(bufferedImage, null);
        chartImage.setAlignment(Element.ALIGN_CENTER);
        document.add(chartImage);

        document.close();
    }

    @Override
    public Interview addInterview(Interview interview, int id){
        User user = userRepository.findByIdUser(id);
        interview.setCondidat(user);
        interviewRepository.save(interview);
        addAutoQcm(interview);
        assignEvaluator(interview);
        return interview;
    }

    @Override
    public List<Claim> findClaimsByObject(String object){
        List<Claim> claims = findClaimsByObject(object);
        return claims;
    }

}