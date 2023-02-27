package com.pidev.phset.controllers;

import com.pidev.phset.entities.*;
import com.pidev.phset.repositories.EventRepository;
import com.pidev.phset.services.IEventServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventRestController {

    public final IEventServices eventServices;
    private final EventRepository eventRepository;

    //Event
    @PostMapping("/addEvent")
    Event addEvent(@RequestBody Event event){
        return eventServices.addEvent(event);
    }

    @PutMapping("/updateEvent")
    Event updateEvent(@RequestBody Event event){
        return eventServices.updateEvent(event);
    }

    @GetMapping("/getEvent/{id}")
    Event getEvent(@PathVariable("id") Integer idEvent){
        return eventServices.retrieveEvent(idEvent);
    }

    @DeleteMapping("/deleteEvent/{id}")
    void deleteEvent(@PathVariable("id") Integer idEvent){
        eventServices.removeEvent(idEvent);
    }

    @GetMapping("/getEvent")
    List<Event> getAllEvents(){
        return eventServices.retrieveAllEvents();
    }



    //Subject
    @PostMapping("/addSubject")
    Subject addSubject(@RequestBody Subject subject){
        return eventServices.addSubject(subject);
    }

    @PutMapping("/updateSubject")
    Subject updateSubject(@RequestBody Subject subject){
        return eventServices.updateSubject(subject);
    }

    @GetMapping("/getSubject/{id}")
    Subject getSubject(@PathVariable("id") Integer idSubject){
        return eventServices.retrieveSubject(idSubject);
    }

    @DeleteMapping("/deleteSubject/{id}")
    void deleteSubject(@PathVariable("id") Integer idSubject){
        eventServices.removeSubject(idSubject);
    }

    @GetMapping("/getSubject")
    List<Subject> getAllSubjects(){
        return eventServices.retrieveAllSubjects();
    }


    //Team
    @PostMapping("/addTeam")
    Team addTeam(@RequestBody Team team){
        return eventServices.addTeam(team);
    }

    @PutMapping("/updateTeam")
    Team updateTeam(@RequestBody Team team){
        return eventServices.updateTeam(team);
    }

    @GetMapping("/getTeam/{id}")
    Team getTeam(@PathVariable("id") Integer idTeam){
        return eventServices.retrieveTeam(idTeam);
    }

    @DeleteMapping("/deleteTeam/{id}")
    void deleteTeam(@PathVariable("id") Integer idTeam){
        eventServices.removeSTeam(idTeam);
    }

    @GetMapping("/getTeam")
    List<Team> getAllTeam(){
        return eventServices.retrieveAllTeams();
    }


    //PostVideo
    @PostMapping("/addPostVid")
    PostVideo addPostVideo(@RequestBody PostVideo postVideo){
        return eventServices.addPostVideo(postVideo);
    }

    @PutMapping("/updatePostVid")
    PostVideo updatePostVideo(@RequestBody PostVideo postVideo){
        return eventServices.updatePostVideo(postVideo);
    }

    @GetMapping("/getPostVid/{id}")
    PostVideo getPostVideo(@PathVariable("id") Integer idPostVid){
        return eventServices.retrievePostVideo(idPostVid);
    }

    @DeleteMapping("/deletePostVid/{id}")
    void deletePostVideo(@PathVariable("id") Integer idPostVid){
        eventServices.removePostVideo(idPostVid);
    }

    @GetMapping("/getPostVid")
    List<PostVideo> getPostsVideo(){
        return eventServices.retrieveAllPostsVideo();
    }


    //Speaker

    @PostMapping("/addSpeaker")
    Speaker addSpeaker(@RequestBody Speaker speaker){
        return eventServices.addSpeaker(speaker);
    }

    @PutMapping("/updateSpeaker")
    Speaker updateSpeaker(@RequestBody Speaker speaker){
        return eventServices.updateSpeaker(speaker);
    }

    @GetMapping("/getSpeaker/{id}")
    Speaker getSpeaker(@PathVariable("id") Integer idSpeaker){
        return eventServices.retrieveSpeaker(idSpeaker);
    }

    @DeleteMapping("/deleteSpeaker/{id}")
    void deleteSpeaker(@PathVariable("id") Integer idSpeaker){
        eventServices.removeSpeaker(idSpeaker);
    }

    @GetMapping("/getSpeaker")
    List<Speaker> getSpeaker(){
        return eventServices.retrieveAllSpeakers();
    }


    @PutMapping ("/affecter/{id}")
    public void affecterIntervenants(@PathVariable("id") int id) {

        eventServices.AssignSpeakerToEvent(id);
    }

    @PutMapping("/affectConf/{idsp}/{idevent}/")
    public void AssignSpeakerToConfirmed(@PathVariable ("idsp") int idsp, @PathVariable ("idevent") int idevent){
        eventServices.AssignSpeakerToConfirmed(idsp,idevent);
    }

    @PutMapping("/Assign")
    public void assignStudentToTeam(){
        eventServices.assignStudentToTeam();
    }

    @PostMapping("/addAcoun")
    public Account addAccount(@RequestBody Account account){
        return eventServices.addAccount(account);
    }

    @PostMapping("/addInsc")
    public Inscription addInsc(@RequestBody Inscription inscription){
        return eventServices.addIns(inscription);
    }






}
