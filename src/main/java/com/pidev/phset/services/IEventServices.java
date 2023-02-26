package com.pidev.phset.services;

import com.pidev.phset.entities.*;

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

    //Add teamasandsignSubject to team

    //assignPostToteam



}
