package com.pidev.phset.services;

import com.pidev.phset.entities.*;

import java.util.List;

public interface IEvaluationServices {

    //Add
    public Claim addClaim (Claim claim);
    public Evaluation addEvaluation (Evaluation evaluation);
    public GridEvaluation addGridEvaluation (GridEvaluation gridEvaluation);
    public MCQ addMcq (MCQ mcq);
    public void addAndAssignDecissionToClaim(Claim claim);
    public String addQuestionAndResponseAndAssignResponsestoQuestion(Question question);
    public List<MCQ> addAutoQcm();
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
    public void deleteClaim(int idClaim);
    public void deleteEvaluattion(int idEvaluation);
    public void deleteGridEvaluation(int idGridEvaluation);
    public void deleteMCQ(int idMcq);
    public void deleteQuestion(int idQuestion);

    public void addEvaluationAndTaskEvaluationAndAssignTaskToEvaluataion(Evaluation evaluation);
    public GridEvaluation addAutoGrid(/*Interview interview*/);
    public void addClaimaAndAssignAccount(int id, Claim claim);

    public void setScoreToMCQ(int idInterview, int idMCQ, float score);
    public void setScoreToTaskEvaluation(TaskEvaluation taskEvaluation,float note);
    public void calculScoreGrid(int idGrid);
    public void assignScoreToInterview(Interview interview);
    }
