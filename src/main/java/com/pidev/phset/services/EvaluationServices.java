package com.pidev.phset.services;

import com.pidev.phset.entities.*;
import com.pidev.phset.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
    public void addAndAssignDecissionToClaim(Claim claim){
        decissionRepository.save(claim.getDecission());
        claimRepository.save(claim);
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
        System.out.println(question.getResponses());
        if(question.getResponses().size()<3) {
            questionRepository.save(question);
            for (Response r : question.getResponses()) {
                r.setQuestion(question);
                responseRepository.save(r);
            }
            return "Add succesfully";
        }
        else
            return "Responses must be 3 or more";
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

}
