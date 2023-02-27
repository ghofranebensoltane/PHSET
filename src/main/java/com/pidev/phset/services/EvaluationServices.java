package com.pidev.phset.services;

import com.pidev.phset.entities.*;
import com.pidev.phset.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluationServices implements IEvaluationServices{
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
    public void deleteClaim(int idClaim) {
        claimRepository.deleteById(idClaim);
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



    @Override
    @Transactional
    public String addQuestionAndResponseAndAssignResponsestoQuestion(Question question) {
        Question q = questionRepository.findByNameQuestion(question.getNameQuestion());
        if(q==null){
            if(question.getResponses().size()==3) {
                questionRepository.save(question);
                for (Response r : question.getResponses()) {
                    r.setQuestion(question);
                    responseRepository.save(r);
                }
                return "Add succesfully";
            }
            else
                return "Responses must be 3";
        }
        else {
            return "Question already exist";
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

    ///////////////////////////// AUTOMATIQUE ////////////////////////////////

    @Override
    public GridEvaluation addAutoGrid(/*Interview interview*/){
        GridEvaluation gridEvaluation = new GridEvaluation();
        List<Evaluation> evaluations = new ArrayList<>();
        evaluationRepository.findAll().forEach(evaluations::add);
        //if(interview.getTypeInterview().equals(TypeGrid.Job)){
            gridEvaluation.setTypeGrid(TypeGrid.Job);
            gridEvaluationRepository.save(gridEvaluation);
            for(Evaluation e : evaluations){
                if(e.getTypeEvaluation().equals(TypeGrid.Job)){
                    e.setGridEvaluation(gridEvaluation);
                    evaluationRepository.save(e);
                }
            }
        //}
        //else if(interview.getTypeInterview().equals(TypeGrid.Admission)){
            gridEvaluation.setTypeGrid(TypeGrid.Admission);
            gridEvaluationRepository.save(gridEvaluation);
            for(Evaluation e : evaluations){
                if(e.getTypeEvaluation().equals(TypeGrid.Admission)){
                    e.setGridEvaluation(gridEvaluation);
                    evaluationRepository.save(e);
                }
            }
        //}
        //interview.setGridEvaluation(gridEvaluation);
        //interviewRepository.save(interview);
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
    @Transactional
    public List<MCQ> addAutoQcm(/*Interview interview*/) {
        //if(interview.getTypeInterview().equals(TypeGrid.Admission)){
        List<MCQ> mcqs = new ArrayList<>();
        MCQ mcq1 = new MCQ();
        mcq1.setTitle("MCQ of language skills");
        mcq1.setTypeTest(TypeTest.Language_Skills);
        MCQ mcq2 = new MCQ();
        mcq2.setTitle("MCQ of general culture");
        mcq2.setTypeTest(TypeTest.General_Culture);
        //mcq1.setInterview(interview);
        //mcq2.setInterview(interview);
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
        return mcqs;
        //}else{
        //return null;
        //}
    }

    ///////////////////////////// FIN AUTOMATIQUE ////////////////////////////////
    /////////////////////////////// A Utiliser ///////////////////////////////////

    @Override
    public void addAndAssignDecissionToClaim(Claim claim){
        decissionRepository.save(claim.getDecission());
        claimRepository.save(claim);
        // Informer l'utilisateur notif / email
    }

    @Override
    public void addClaimaAndAssignAccount(int id, Claim claim){
        //Account account = accountRepository.findById(id).orElse(null);
        //claim.setAccount(account);
        claimRepository.save(claim);
    }

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

    @Override
    public void setScoreToTaskEvaluation(TaskEvaluation taskEvaluation,float note){
        taskEvaluation.setNote(note);
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
    public void assignScoreToInterview(Interview interview){
        float score=0;
        for (MCQ mcq : interview.getMcqs()){
            score =+ mcq.getScore();
        }
        score =+ interview.getGridEvaluation().getScoreGrid();
    }

}
