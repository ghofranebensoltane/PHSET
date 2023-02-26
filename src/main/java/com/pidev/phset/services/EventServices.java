package com.pidev.phset.services;

import com.pidev.phset.entities.*;
import com.pidev.phset.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EventServices implements IEventServices{

    private final EventRepository eventRepository;

    private final PostVideoRepository postVideoRepository;

    private final SubjectRepository subjectRepository;

    private final TeamRepository teamRepository;


    //Event
    @Override
    public List<Event> retrieveAllEvents() {
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().forEach(events::add);
        return events;
    }

    @Override
    public Event addEvent(Event e) {
        return eventRepository.save(e);
    }

    @Override
    public Event updateEvent(Event e) {
        return eventRepository.save(e);
    }

    @Override
    public Event retrieveEvent(Integer idEvent) {
        return eventRepository.findById(idEvent).orElse(null);
    }

    @Override
    public void removeEvent(Integer idEvent) {
        eventRepository.deleteById(idEvent);

    }



    //PostVideo
    @Override
    public List<PostVideo> retrieveAllPostsVideo() {
        List<PostVideo> posts = new ArrayList<>();
        postVideoRepository.findAll().forEach(posts::add);
        return posts;
    }

    @Override
    public PostVideo addPostVideo(PostVideo p) {
        return postVideoRepository.save(p);
    }

    @Override
    public PostVideo updatePostVideo(PostVideo p) {
        return postVideoRepository.save(p);
    }

    @Override
    public PostVideo retrievePostVideo(Integer idPostV) {
        return postVideoRepository.findById(idPostV).orElse(null);
    }

    @Override
    public void removePostVideo(Integer idPostV) {
        postVideoRepository.deleteById(idPostV);

    }


    //Subject
    @Override
    public List<Subject> retrieveAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        subjectRepository.findAll().forEach(subjects::add);
        return subjects;
    }

    @Override
    public Subject addSubject(Subject sub) {
        return subjectRepository.save(sub);
    }

    @Override
    public Subject updateSubject(Subject sub) {
        return subjectRepository.save(sub);
    }

    @Override
    public Subject retrieveSubject(Integer idSub) {
        return subjectRepository.findById(idSub).orElse(null);
    }

    @Override
    public void removeSubject(Integer idSub) {
        subjectRepository.deleteById(idSub);
    }


    //Team
    @Override
    public List<Team> retrieveAllTeams() {
        List<Team> teams = new ArrayList<>();
        teamRepository.findAll().forEach(teams::add);
        return teams;
    }

    @Override
    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Team updateTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Team retrieveTeam(Integer idTeam) {
        return teamRepository.findById(idTeam).orElse(null);
    }

    @Override
    public void removeSTeam(Integer idTeam) {
        teamRepository.deleteById(idTeam);
    }


}
