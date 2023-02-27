package com.pidev.phset.services;

import com.pidev.phset.entities.*;
import com.pidev.phset.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventServices implements IEventServices{

    private final EventRepository eventRepository;

    private final PostVideoRepository postVideoRepository;

    private final SubjectRepository subjectRepository;

    private final TeamRepository teamRepository;

    private final SpeakerRepository speakerRepository;

    private final IAccountRepository accountRepository;

    private final IInscriptionRepository inscriptionRepository;


    //Event
    @Override
    public List<Event> retrieveAllEvents() {
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().forEach(events::add);
        return events;
    }

    @Override
    public Event addEvent(Event e) {
        eventRepository.save(e);
        AssignSpeakerToEvent(e.getIdEvent());
        return e;
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



    //Speaker
    @Override
    public List<Speaker> retrieveAllSpeakers() {
        List<Speaker> speakers = new ArrayList<>();
        speakerRepository.findAll().forEach(speakers::add);
        return speakers;
    }

    @Override
    public Speaker addSpeaker(Speaker speaker) {
        return speakerRepository.save(speaker);
    }

    @Override
    public Speaker updateSpeaker(Speaker speaker) {
        return speakerRepository.save(speaker);
    }

    @Override
    public Speaker retrieveSpeaker(Integer idSpeaker) {
        return speakerRepository.findById(idSpeaker).orElse(null);
    }

    @Override
    public void removeSpeaker(Integer idSpeaker) {
        speakerRepository.deleteById(idSpeaker);

    }

    @Override
    public double getScore(Speaker speaker, Event event) {
        double score = 0;
        String bio = speaker.getBiography();
        if (event.getTypeEvent().equals(TypeEvent.APP0)) {
            if (bio.equals("ancien étudiant")) {
                score += 5; // Ajoute 5 points pour les anciens étudiants
            }
        }
        else if (event.getTypeEvent().equals(TypeEvent.Educational_Event)) {
            if (bio.equals("formateur")|| bio.equals("professeur")) {
                score += 8; // Ajoute 8 points pour les formateurs et professeurs
            }
        }
        else if (event.getTypeEvent().equals(TypeEvent.Leisure_Event)) {
            if (bio.equals("influenceur")) {
                score += 6; // Ajoute 6 points pour les influenceurs
            } else if (bio.equals("animateur")) {
                score += 4; // Ajoute 4 points pour les animateurs
            }
        }
        return score;
    }

    /* public void desaffectation(int idsp, int ide){
        Event e = eventRepository.findById(ide).orElse(null);
        Speaker sp = speakerRepository.findById(idsp).orElse(null);

        e.getSpeakers().remove(sp);
        eventRepository.save(e);
    } */

    @Override
    public void AssignSpeakerToEvent(int h) {

        List<Speaker> speakers = new ArrayList<>();
        speakerRepository.findAll().forEach(speakers::add);
        Event event = eventRepository.findById(h).orElse(null);
        double i = 0;
        Set<Speaker> affectes = new HashSet<>();
        for (Speaker speaker : speakers) {
            i = getScore(speaker,event);
            if(i>0){
                speaker.getEvents().add(event);
                affectes.add(speaker);
            }
            //Mailling (id event / id speaker)
            //speaker.getIdSpeaker(); event.getIdEvent();
            i =0;
        }
        event.setSpeakers(affectes);
        eventRepository.save(event);
    }

    @Override
    public void AssignSpeakerToConfirmed(int idsp, int idevent){
        Speaker speaker = speakerRepository.findById(idsp).orElse(null);
        Event event = eventRepository.findById(idevent).orElse(null);

        event.getSpeakers().remove(speaker);
        if(event.getSpeakersconf().size()==0){
            Set<Speaker> speakers = new HashSet<>();
            speakers.add(speaker);
            event.setSpeakersconf(speakers);
        }else{
            event.getSpeakersconf().add(speaker);
        }
        eventRepository.save(event);
    }


        @Override
        @Scheduled(cron = "0 0 0 1 9 *")
        @Transactional
        public void assignStudentToTeam() {
            List<Account> accounts = accountRepository.retrieveAccount();
            List<Team> teams = new ArrayList<>();

            for(int a = 0 ; a < accounts.size() ; a=a+5){
                Team t = new Team();
                teamRepository.save(t);
            }

            teamRepository.findAll().forEach(teams::add);

            for(Team team : teams){
                for(Account account : accounts){
                    if(team.getAccounts()==null || team.getAccounts().size()<5){
                        account.setTeam(team);
                        accountRepository.save(account);
                    }
                }
                for(int y = 0 ; y < 5 && y < accounts.size() ; y++){
                    accounts.remove(y);
                }
            }

            // System.out.println("ok");
            /*
            for (Account a : accounts){
                a.setTeam(null);
                accountRepository.save(a);
            } */
        }
        private boolean teamHasStudent(Team team, Account account) {
            for (Account s : team.getAccounts()) {
                    if (s.getIdAccount().equals(account.getIdAccount()))
                    {
                    return true;
                }
            }
            return false;
        }

       /* @Override
         public Account addAccount (Account account){
         return  accountRepository.save(account);
        }

    public Inscription addIns(Inscription inscription){
        return inscriptionRepository.save(inscription);
    }*/



}
