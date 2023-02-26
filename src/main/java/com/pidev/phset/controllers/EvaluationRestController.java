package com.pidev.phset.controllers;

import com.pidev.phset.entities.*;
import com.pidev.phset.services.IEvaluationServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation")
public class EvaluationRestController {
    private final IEvaluationServices evaluationServices;

    //Add
    @PostMapping("/addClaim")
    Claim addClaim (@RequestBody Claim claim){
        return evaluationServices.addClaim(claim);
    }

    @PostMapping("/addEvaluation")
    Evaluation addEvaluation (@RequestBody Evaluation evaluation){
        return evaluationServices.addEvaluation(evaluation);
    }

    @PostMapping("/addGridEvaluation")
    GridEvaluation addGridEvaluation (@RequestBody GridEvaluation gridEvaluation){
        return evaluationServices.addGridEvaluation(gridEvaluation);
    }

    @PostMapping("/addMcq")
    MCQ addMcq (@RequestBody MCQ mcq){
        return evaluationServices.addMcq(mcq);
    }

    public void addAndAssignDecissionToClaim(Claim claim){
        evaluationServices.addAndAssignDecissionToClaim(claim);
    }

    @PostMapping("/addQuestionAndResponse")
    String addQuestionAndResponseAndAssignResponsestoQuestion(@RequestBody Question question){
        return evaluationServices.addQuestionAndResponseAndAssignResponsestoQuestion(question);
    }

    //Find one
    @GetMapping("/retrieveClaim/{idClaim}")
    Claim retrieveClaim(@PathVariable int idClaim){
        return evaluationServices.retrieveClaim(idClaim);
    }

    @GetMapping("/retrieveEvaluation/{idEvaluation}")
    Evaluation retrieveEvaluation(@PathVariable int idEvaluation){
        return evaluationServices.retrieveEvaluation(idEvaluation);
    }

    @GetMapping("/retrieveGridEvaluation/{idGridEvaluation}")
    GridEvaluation retrieveGridEvaluation(@PathVariable int idGridEvaluation){
        return evaluationServices.retrieveGridEvaluation(idGridEvaluation);
    }

    @GetMapping("/retrieveMCQ/{idMCQ}")
    MCQ retrieveMCQ (@PathVariable int idMCQ){
        return evaluationServices.retrieveMCQ(idMCQ);
    }

    @GetMapping("/retrieveQuestion/{idQuestion}")
    Question retrieveQuestion(@PathVariable int idQuestion){
        return evaluationServices.retrieveQuestion(idQuestion);
    }


    //Find all
    @GetMapping("/retrieveAllClaims")
    List<Claim> retrieveAllClaims(){
        return evaluationServices.retrieveAllClaims();
    }

    @GetMapping("/retrieveAllEvaluations")
    List<Evaluation> retrieveAllEvaluations(){
        return evaluationServices.retrieveAllEvaluations();
    }

    @GetMapping("/retrieveAllGridEvaluations")
    List<GridEvaluation> retrieveAllGridEvaluations(){
        return evaluationServices.retrieveAllGridEvaluations();
    }

    @GetMapping("/retrieveAllMCQ")
    List<MCQ> retrieveAllMCQ(){
        return evaluationServices.retrieveAllMCQ();
    }

    @GetMapping("/retrieveAllQuestion")
    List<Question> retrieveAllQuestion(){
        return evaluationServices.retrieveAllQuestion();
    }

/*
    //update
    Claim updateClaim(Claim claim){
        return evaluationServices.updateClaim(claim);
    }

    Evaluation updateEvaluation(Evaluation evaluation){
        return evaluationServices.updateEvaluation(evaluation);
    }

    GridEvaluation updateGridEvaluation(GridEvaluation gridEvaluation){
        return evaluationServices.updateGridEvaluation(gridEvaluation);
    }

    MCQ updateMCQ(MCQ mcq){
        return evaluationServices.updateMCQ(mcq);
    }

    Question updateQuestionAndResponse(Question question){
        return evaluationServices.updateQuestionAndResponse(question);
    }


    //Delete
    void deleteClaim(int idClaim){
        evaluationServices.deleteClaim(idClaim);
    }

    void deleteEvaluattion(int idEvaluation){
        evaluationServices.deleteEvaluattion(idEvaluation);
    }

    void deleteGridEvaluation(int idGridEvaluation){
        evaluationServices.deleteGridEvaluation(idGridEvaluation);
    }

    void deleteMCQ(int idMcq){
        evaluationServices.deleteMCQ(idMcq);
    }

    void deleteQuestion(int idQuestion){
        evaluationServices.deleteQuestion(idQuestion);
    }*/
}
