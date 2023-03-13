package com.pidev.phset.services;

import com.itextpdf.text.DocumentException;
import com.pidev.phset.entities.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IEvaluationServices {

    //Add
    public Claim addClaim (Claim claim);
    public Evaluation addEvaluation (Evaluation evaluation);
    public GridEvaluation addGridEvaluation (GridEvaluation gridEvaluation);
    public MCQ addMcq (MCQ mcq);
    public void addAndAssignDecissionToClaim(int claim, Decission decission,EtatClaim etatClaim) throws MessagingException;
    public String addQuestionAndResponseAndAssignResponsestoQuestion(Question question);
    public List<MCQ> addAutoQcm(Interview interview);
    public List<Question> getRandomQuestions(List<Question> questions, int numQuestions);

        //Find one
    public Claim retrieveClaim(int idClaim);
    public Evaluation retrieveEvaluation(int idEvaluation);
    public GridEvaluation retrieveGridEvaluation(int idGridEvaluation);
    public MCQ retrieveMCQ (int idMCQ);
    public Question retrieveQuestion(int idQuestion);

    //Find all
    public List<Claim> retrieveAllClaims();
    public List<Evaluation> retrieveAllEvaluations();
    public List<GridEvaluation> retrieveAllGridEvaluations();
    public List<MCQ> retrieveAllMCQ();
    public List<Question> retrieveAllQuestion();

    //update
    public Claim updateClaim(Claim claim);
    public Evaluation updateEvaluation(Evaluation evaluation);
    public GridEvaluation updateGridEvaluation(GridEvaluation gridEvaluation);
    public MCQ updateMCQ(MCQ mcq);
    public Question updateQuestionAndResponse(Question question);

    //Delete
    public Object deleteClaim(int idClaim);
    public void deleteEvaluattion(int idEvaluation);
    public void deleteGridEvaluation(int idGridEvaluation);
    public void deleteMCQ(int idMcq);
    public void deleteQuestion(int idQuestion);

    public void addEvaluationAndTaskEvaluationAndAssignTaskToEvaluataion(Evaluation evaluation);
    public GridEvaluation addAutoGrid(Interview interview);
    public void addClaimaAndAssignAccount(Claim claim, MultipartFile file) throws IOException;

    public void setScoreToMCQ(int idInterview, int idMCQ, float score);
    public void setScoreToTaskEvaluation(TaskEvaluation taskEvaluation,float note,String remark);
    public void calculScoreGrid(int idGrid);
    public void assignScoreToInterview(/*Interview interview*/) throws MessagingException ;

    public void sendEmail(String to, String subject, String body) throws MessagingException ;
    public boolean checkDisponibilite(User user, Interview interview) ;
    public String assignEvaluator(Interview interview) ;
    public List<Claim> sortClaimByDate();

    public List<Claim> getClaimsByAccount(int accountId);

    public String tendanceReclamation();


    public List<Claim> getClaimsSortedByPriority();

    public List<Claim> searchClaims(Date a, String b , String c , EtatClaim d);

    public List<MCQ> searchMcq(String a, Float b, TypeTest c, Integer d);

    public Map<String, Long> countClaimsByState();

    public List<Interview> findInterviewProf(int idProf);

    public void desafecterProfInterview(int idInterview, int idProf);

    public String getAnswer(String question) throws IOException ;

    public void exportLastMonthTraitdClaimsToExcel() ;

    public void generateClaimReport() throws IOException, DocumentException ;

    public void updateOldExcelFiles ();

    public List<Claim> getAllClaimsFromExcelFiles();

    public Interview addInterview(Interview interview, int id);

    public List<Claim> findClaimsByObject(String object);

}
