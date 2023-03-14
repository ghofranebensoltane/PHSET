package com.pidev.phset.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.pidev.phset.entities.*;
import com.pidev.phset.repositories.IAccountRepository;
import com.pidev.phset.repositories.IClaimRepository;
import com.pidev.phset.services.IEvaluationServices;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/evaluation")
public class EvaluationRestController {
    private final IEvaluationServices evaluationServices;
    private IClaimRepository claimRepository;

    private IAccountRepository accountRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    //Add
    @PostMapping("/addClaim")
    Claim addClaim (@RequestBody Claim claim){
        return evaluationServices.addClaim(claim);
    }

    @PutMapping("/update")
    Claim update (@RequestBody Claim claim){
        return evaluationServices.updateClaim(claim);
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

    @PutMapping("/addAndAssignDecissionToClaim/{idclaim}/{ff}")
    public void addAndAssignDecissionToClaim(@PathVariable("idclaim") int idclaim , @RequestBody Decission decission, @PathVariable("ff") EtatClaim etatClaim) throws MessagingException{
        evaluationServices.addAndAssignDecissionToClaim(idclaim, decission, etatClaim);
    }

    @PutMapping("/addQuestionAndResponse")
    public String addQuestionAndResponseAndAssignResponsestoQuestion(@RequestBody Question question){
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

    @PutMapping("/EvaluationaAndTasks")
    public void addEvaluationAndTaskEvaluationAndAssignTaskToEvaluataion(@RequestBody Evaluation evaluation) {
        evaluationServices.addEvaluationAndTaskEvaluationAndAssignTaskToEvaluataion(evaluation);
    }


    @PostMapping("/sendEmail")
    public void sendEmail(@RequestBody String to, @RequestBody String subject, @RequestBody String body) throws MessagingException {
        evaluationServices.sendEmail(to, subject, body);
    }
/*

    @PutMapping("/affecterEvaluateurs/{id}")
    public String affecterEvaluateurs(@PathVariable("id") int id) {
        return evaluationServices.assignEvaluator(id);
    }
*/
/*
    // Tableau de bord en temps réel
    @MessageMapping("/dashboard/{accountId}")
    @SendTo("/dashboard/{accountId}")
    public void getClaimsForAccount(@PathVariable("accountId") int accountId) {
        evaluationServices.getClaimsByAccount(accountId);
        List<Claim> claims = evaluationServices.getClaimsByAccount(accountId);
        messagingTemplate.convertAndSend("/dashboard/" + accountId, claims);
    }
*/

    @GetMapping("/sortClaimByDate")
    public List<Claim> sortClaimByDate() {
        return evaluationServices.sortClaimByDate();
    }

    @GetMapping("/getClaimsByAccount/{id}")
    public List<Claim> getClaimsByAccount(@PathVariable("id") int accountId) {
        return  evaluationServices.getClaimsByAccount(accountId);
    }

    @GetMapping("/tendanceReclamation")
    public String tendanceReclamation() {
        return evaluationServices.tendanceReclamation();
    }


    @PostMapping("/addClaimaAndAssignAccount")
    public void addClaimaAndAssignAccount(@ModelAttribute Claim claim, @RequestParam("file") MultipartFile file) throws IOException {
        evaluationServices.addClaimaAndAssignAccount(claim, file);
    }

    @GetMapping("/getClaimsSortedByPriority")
    public List<Claim> getClaimsSortedByPriority() {
        return evaluationServices.getClaimsSortedByPriority();
    }

    @GetMapping("/searchClaims")
    public List<Claim> searchClaims(
            @RequestParam(name = "a", required = false) Date a,
            @RequestParam(name = "b", required = false) String b,
            @RequestParam(name = "c", required = false) String c,
            @RequestParam(name = "d", required = false) EtatClaim d
            ) {
        return evaluationServices.searchClaims(a,b,c,d);
    }

    @GetMapping("/searchMcq")
    public List<MCQ> searchMcq(
            @RequestParam(name = "a", required = false) String a,
            @RequestParam(name = "b", required = false) Float b,
            @RequestParam(name = "c", required = false) TypeTest c,
            @RequestParam(name = "d", required = false) Integer d
    ) {
        return evaluationServices.searchMcq(a,b,c,d);
    }

    @GetMapping("/ClaimStatistique")
    public Map<String, Long> countClaimsByState() {
        return evaluationServices.countClaimsByState();
    }


    @GetMapping("/findInterviewProf/{id}")
    public List<Interview> findInterviewProf(@PathVariable("id") int idProf){
        return evaluationServices.findInterviewProf(idProf);
    }

    @PutMapping("/desafecterProfInterview/{idint}/{idprof}")
    public void desafecterProfInterview(@PathVariable("idint") int idInterview, @PathVariable("idprof") int idProf){
        evaluationServices.desafecterProfInterview(idInterview,idProf);
    }

    @PostMapping("/answer")
    public String getAnswer(@RequestBody String question) throws IOException {
        return evaluationServices.getAnswer(question);
    }

    @Autowired
    private RestHighLevelClient elasticsearchClient;

    @GetMapping("/sea")
    public List<Claim> searchClaims(@RequestParam("query") String query) throws IOException {
        SearchRequest searchRequest = new SearchRequest("claims");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("content", query));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
        ObjectMapper objectMapper= new ObjectMapper();
        SearchHits hits = searchResponse.getHits();
        List<Claim> results = Arrays.stream(hits.getHits())
                .map(hit -> {
                    String source = hit.getSourceAsString();
                    Claim claim = null;
                    try {
                        claim = objectMapper.readValue(source, Claim.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return claim;
                })
                .collect(Collectors.toList());

        return results;
    }

    @GetMapping("/excel")
    public void exportLastMonthTraitdClaimsToExcel() {
        evaluationServices.exportLastMonthTraitdClaimsToExcel();
    }

    @GetMapping("/pdf")
    public void generateClaimReport() throws IOException, DocumentException {
        evaluationServices.generateClaimReport();
    }

    @GetMapping("/updateexcel")
    public void updateOldExcelFiles (){
        evaluationServices.updateOldExcelFiles();
    }


    @GetMapping("/getAllClaimsFromExcelFiles")
    public List<Claim> getAllClaimsFromExcelFiles(){
        return evaluationServices.getAllClaimsFromExcelFiles();
    }

    @PutMapping("/addInterview/{id}")
    public Interview addInterview(@RequestBody Interview interview, @PathVariable("id") int id){
        return evaluationServices.addInterview(interview, id);
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
