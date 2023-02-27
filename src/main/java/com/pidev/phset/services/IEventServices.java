package com.pidev.phset.services;

import com.pidev.phset.entities.*;
import com.pidev.phset.repositories.UserRepository;

import java.util.List;

public interface IEventServices {

    //Event
    List<Event> retrieveAllEvents();

    Event addEvent (Event e);

    Event updateEvent (Event e);

    Event retrieveEvent(Integer idEvent);

    void removeEvent(Integer idEvent);




    //PostVideo
    List<PostVideo> retrieveAllPostsVideo();

    PostVideo addPostVideo (PostVideo p);

    PostVideo updatePostVideo (PostVideo p);

    PostVideo retrievePostVideo(Integer idPostV);

    void removePostVideo(Integer idPostV);


    //Subject
    List<Subject> retrieveAllSubjects();

    Subject addSubject (Subject sub);

    Subject updateSubject (Subject sub);

    Subject retrieveSubject (Integer idSub);

    void removeSubject(Integer idSub);


    //Team
    List<Team> retrieveAllTeams();

    Team addTeam (Team team);

    Team updateTeam (Team team);

    Team retrieveTeam (Integer idTeam);

    void removeSTeam(Integer idTeam);


    //Speaker
    List<Speaker> retrieveAllSpeakers();

    Speaker addSpeaker (Speaker speaker);

    Speaker updateSpeaker (Speaker speaker);

    Speaker retrieveSpeaker (Integer idSpeaker);

    void removeSpeaker(Integer idSpeaker);

    void AssignSpeakerToEvent(int event);
    public double getScore(Speaker speaker, Event event);

    public void AssignSpeakerToConfirmed(int idsp, int idevent);

    public void assignStudentToTeam();
/*
    public Account addAccount (Account account);

    public Inscription addIns(Inscription inscription);
*/



}
