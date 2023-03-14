package com.pidev.phset.controllers;

import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;
import com.pidev.phset.entities.*;
import com.pidev.phset.repositories.EventRepository;
import com.pidev.phset.repositories.IDecissionRepository;
import com.pidev.phset.services.IEventServices;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventRestController {

    public final IEventServices eventServices;
    private final EventRepository eventRepository;
    private final IDecissionRepository iDecissionRepository;

    //Event
    @PostMapping("/addEvent")
    Event addEvent(@ModelAttribute Event event, @RequestParam("file") MultipartFile file, @RequestParam("s") String s , @RequestParam("f") String f ) throws MessagingException, IOException  {
        // Définir le format de la chaîne de caractères
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Convertir la chaîne de caractères en LocalDateTime
        LocalDateTime localDateTimes = LocalDateTime.parse(s, formatter);
        LocalDateTime localDateTimef = LocalDateTime.parse(f, formatter);
        event.setDateS(localDateTimes);
        event.setDateF(localDateTimef);
        return eventServices.addEvent(event,file);
    }

    @PutMapping("/updateEvent")
    Event updateEvent(@RequestBody Event event) {
        return eventServices.updateEvent(event);
    }

    @GetMapping("/getEvent/{id}")
    Event getEvent(@PathVariable("id") Integer idEvent) {
        return eventServices.retrieveEvent(idEvent);
    }

    @DeleteMapping("/deleteEvent/{id}")
    void deleteEvent(@PathVariable("id") Integer idEvent) {
        eventServices.removeEvent(idEvent);
    }

    @GetMapping("/getEvent")
    List<Event> getAllEvents() {
        return eventServices.retrieveAllEvents();
    }


    //Subject
    @PostMapping("/addSubject")
    Subject addSubject(@RequestBody Subject subject) {
        return eventServices.addSubject(subject);
    }

    @PutMapping("/updateSubject")
    Subject updateSubject(@RequestBody Subject subject) {
        return eventServices.updateSubject(subject);
    }

    @GetMapping("/getSubject/{id}")
    Subject getSubject(@PathVariable("id") Integer idSubject) {
        return eventServices.retrieveSubject(idSubject);
    }

    @DeleteMapping("/deleteSubject/{id}")
    void deleteSubject(@PathVariable("id") Integer idSubject) {
        eventServices.removeSubject(idSubject);
    }

    @GetMapping("/getSubject")
    List<Subject> getAllSubjects() {
        return eventServices.retrieveAllSubjects();
    }


    //Team
    @PostMapping("/addTeam")
    Team addTeam(@RequestBody Team team) {
        return eventServices.addTeam(team);
    }

    @PutMapping("/updateTeam")
    Team updateTeam(@RequestBody Team team) {
        return eventServices.updateTeam(team);
    }

    @GetMapping("/getTeam/{id}")
    Team getTeam(@PathVariable("id") Integer idTeam) {
        return eventServices.retrieveTeam(idTeam);
    }

    @DeleteMapping("/deleteTeam/{id}")
    void deleteTeam(@PathVariable("id") Integer idTeam) {
        eventServices.removeSTeam(idTeam);
    }

    @GetMapping("/getTeam")
    List<Team> getAllTeam() {
        return eventServices.retrieveAllTeams();
    }


    //PostVideo
    @PostMapping("/addPostVid")
    PostVideo addPostVideo(@RequestBody PostVideo postVideo) {
        return eventServices.addPostVideo(postVideo);
    }

    @PutMapping("/updatePostVid")
    PostVideo updatePostVideo(@RequestBody PostVideo postVideo) {
        return eventServices.updatePostVideo(postVideo);
    }

    @GetMapping("/getPostVid/{id}")
    PostVideo getPostVideo(@PathVariable("id") Integer idPostVid) {
        return eventServices.retrievePostVideo(idPostVid);
    }

    @DeleteMapping("/deletePostVid/{id}")
    void deletePostVideo(@PathVariable("id") Integer idPostVid) {
        eventServices.removePostVideo(idPostVid);
    }

    @GetMapping("/getPostVid")
    List<PostVideo> getPostsVideo() {
        return eventServices.retrieveAllPostsVideo();
    }


    //Speaker

    @PostMapping("/addSpeaker")
    Speaker addSpeaker(@RequestBody Speaker speaker) {
        return eventServices.addSpeaker(speaker);
    }

    @PutMapping("/updateSpeaker")
    Speaker updateSpeaker(@RequestBody Speaker speaker) {
        return eventServices.updateSpeaker(speaker);
    }

    @GetMapping("/getSpeaker/{id}")
    Speaker getSpeaker(@PathVariable("id") Integer idSpeaker) {
        return eventServices.retrieveSpeaker(idSpeaker);
    }

    @DeleteMapping("/deleteSpeaker/{id}")
    void deleteSpeaker(@PathVariable("id") Integer idSpeaker) {
        eventServices.removeSpeaker(idSpeaker);
    }

    @GetMapping("/getSpeaker")
    List<Speaker> getSpeaker() {
        return eventServices.retrieveAllSpeakers();
    }


    //thematic
    @PostMapping("/addThem")
    Thematic addThem(@RequestBody Thematic thematic)  {
        return eventServices.addThematic(thematic);
    }

    @PutMapping("/addandAssSubject/{idteam}/{idthem}")
    public void addSubjectAndAssignThematicAndTeam(@RequestBody Subject s,@PathVariable("idteam") Integer idteam,@PathVariable("idthem") Integer idthem){
        eventServices.addSubjectAndAssignThematicAndTeam(s,idteam,idthem);
    }


    @PutMapping("/affecter/{id}")
    public void affecterIntervenants(@PathVariable("id") int id) throws MessagingException {

        eventServices.AssignSpeakerToEvent(id);
    }

    @PutMapping("/affectConf/{idsp}/{idevent}/")
    public void AssignSpeakerToConfirmed(@PathVariable("idsp") int idsp, @PathVariable("idevent") int idevent) {
        eventServices.AssignSpeakerToConfirmed(idsp, idevent);
    }

    @PutMapping("/Assign")
    public void assignStudentToTeam() throws MessagingException {
        eventServices.assignStudentToTeam();
    }


    @PutMapping("/Reserv/{idevent}/{idacc}/")
    public void AddAndAssignResevToEventAndAcc(@RequestBody Reservation reservation, @PathVariable("idevent") Integer idevent, @PathVariable("idacc") Integer idacc) {
        eventServices.AddAndAssignResevToEventAndAcc(reservation, idevent, idacc);
    }

    @PostMapping("/addRoom")
    public Room addRoom(@RequestBody Room room) {
        return eventServices.AddRoom(room);
    }


    @PutMapping("/delete/{id}")
    public void unassignRoom(@PathVariable("id") int id) {
        eventServices.unassignRoom(id);
    }


    @PutMapping("/unassign")
    public void unassignRoomIfEventEnded() {
        eventServices.unassignRoomIfEventEnded();
    }


    @GetMapping("/sort")
    public List<Event> sortByStartDate() {
        return eventServices.sort();
    }

    @GetMapping("/getWinner")
    public Team CalculLikes() {
        return eventServices.CalculLikes();
    }

    @PutMapping("Presence/{idacc}")
    public void SetPresence(@PathVariable("idacc") int idacc){
        eventServices.SetPresence(idacc);

    }

    @PutMapping("/getCertif/{idevent}")
    public Certificate CreateCertificate(@PathVariable("idevent") int idevent) throws DocumentException, IOException, WriterException, MessagingException {
        return eventServices.createCertificates(idevent);
    }


    @GetMapping("/searchEvents")
    public List<Event> searchEvents(
            @RequestParam(name = "a", required = false) String a,
            @RequestParam(name = "b", required = false) Float b,
            @RequestParam(name = "c", required = false) Integer c,
            @RequestParam(name = "d", required = false) String d,
            @RequestParam(name = "e", required = false) LocalDateTime e,
            @RequestParam(name = "f", required = false) LocalDateTime f,
            @RequestParam(name = "g", required = false) Mode g,
            @RequestParam(name = "h", required = false) ModePay h,
            @RequestParam(name = "i", required = false) TypeEvent i,
            @RequestParam(name = "j", required = false) Integer j
    ) {
        return eventServices.searchEvents(a, b, c, d, e, f, g, h, i, j);
    }


    @PostMapping("/payer/{id}")
    public String payer(@ModelAttribute Payement paiement, Model model ,@PathVariable("id") int id) {
        try {
             return eventServices.payer(paiement, model,id);
        } catch (StripeException e) {
            model.addAttribute("message", "Une erreur est survenue lors du paiement : " + e.getMessage());
            return "erreur";
        }
    }
    @GetMapping("/countEventsByType")
    public Map<String, Long> countEventsByType() {
        return eventServices.countEventsByType();
    }

    @GetMapping("/countEventsByModePay")
    public Map<String, Long> countEventsByModePay() {
        return eventServices.countEventsByModePay();
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmRegistration(@RequestParam("code") String activationCode ,  @RequestParam("action") String action ) {
        // Vérifier le code d'activation

            return eventServices.confirmRegistration(activationCode,action);
    }


    @PutMapping("CreatePost/{idacc}")
    public PostVideo CreatePost(@ModelAttribute PostVideo postVideo, @PathVariable("idacc") int a, @RequestParam("video") MultipartFile video) {
        return eventServices.createPostVid(postVideo, a, video);
    }

    @GetMapping("/planEvent/{id}")
    public void planEvent(@PathVariable("id") int id) throws DocumentException, IOException {
        eventServices.planEvent(id);
    }


    }


