package com.pidev.phset.services;

import com.pidev.phset.entities.*;
import com.pidev.phset.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdmissionServicesImpl implements IAdmissionServices {
    @Autowired
    ICandidacyRepository candidacyRepository;
    @Autowired
    IClassroomRepository classroomRepository;
    @Autowired
    IInscriptionRepository inscriptionRepository;
    @Autowired
    IInterviewRepository interviewRepository;
    @Autowired
    IJuryAppreciationRepository juryAppreciationRepository;
    @Autowired
    IOfferRepository offerRepository;

    //////// ****** CANDIDACY Services ****** ////////

    @Override
    public Candidacy addCandidacy(Candidacy candidacy) {
        return candidacyRepository.save(candidacy);
    }

    @Override
    public Candidacy updateCandidacy(Candidacy candidacy) {
        return candidacyRepository.save(candidacy);
    }

    @Override
    public void removeCandidacy(Integer idCandidacy) {
        candidacyRepository.deleteById(idCandidacy);
    }

    @Override
    public Candidacy retrieveCandidacy(Integer idCandidacy) {
        return candidacyRepository.findById(idCandidacy).orElse(null);
    }

    @Override
    public List<Candidacy> retrieveAllCandidacy() {
        return (List<Candidacy>) candidacyRepository.findAll();
    }

    //////// ****** CLASSROOM Services ****** ////////

    @Override
    public Classroom addClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    @Override
    public Classroom updateClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    @Override
    public void removeClassroom(Integer idClassroom) {
        classroomRepository.deleteById(idClassroom);
    }

    @Override
    public Classroom retrieveClassroom(Integer idClassroom) {
        return classroomRepository.findById(idClassroom).orElse(null);
    }

    @Override
    public List<Classroom> retrieveAllClassroom() {
        return (List<Classroom>) classroomRepository.findAll();
    }

    //////// ****** CRITERIA Services ****** ////////

    //////// ****** INSCRIPTION Services ****** ////////

    @Override
    public Inscription addInscription(Inscription inscription) {
        return inscriptionRepository.save(inscription);
    }

    @Override
    public Inscription updateInscription(Inscription inscription) {
        return inscriptionRepository.save(inscription);
    }

    @Override
    public void removeInscription(Integer idInscription) {
        inscriptionRepository.deleteById(idInscription);
    }

    @Override
    public Inscription retrieveInscription(Integer idInscription) {
        return inscriptionRepository.findById(idInscription).orElse(null);
    }

    @Override
    public List<Inscription> retrieveAllInscription() {
        return (List<Inscription>) inscriptionRepository.findAll();
    }

    //////// ****** INTERVIEW Services ****** ////////

    @Override
    public Interview addInterview(Interview interview) {
        return interviewRepository.save(interview);
    }

    @Override
    public Interview updateInterview(Interview interview) {
        return interviewRepository.save(interview);
    }

    @Override
    public void removeInterview(Integer idInterview) {
        interviewRepository.deleteById(idInterview);
    }

    @Override
    public Interview retrieveInterview(Integer idInterview) {
        return interviewRepository.findById(idInterview).orElse(null);
    }

    @Override
    public List<Interview> retrieveAllInterview() {
        return (List<Interview>) interviewRepository.findAll();
    }

    //////// ****** JURYAPPRECIATION Services ****** ////////

    @Override
    public JuryAppreciation addJuryAppreciation(JuryAppreciation juryAppreciation) {
        return juryAppreciationRepository.save(juryAppreciation);
    }

    @Override
    public JuryAppreciation updateJuryAppreciation(JuryAppreciation juryAppreciation) {
        return juryAppreciationRepository.save(juryAppreciation);
    }

    @Override
    public void removeJuryAppreciation(Integer idJuryAppreciation) {
        juryAppreciationRepository.deleteById(idJuryAppreciation);
    }

    @Override
    public JuryAppreciation retrieveJuryAppreciation(Integer idJuryAppreciation) {
        return juryAppreciationRepository.findById(idJuryAppreciation).orElse(null);
    }

    @Override
    public List<JuryAppreciation> retrieveAllJuryAppreciation() {
        return (List<JuryAppreciation>) juryAppreciationRepository.findAll();
    }

    //////// ****** OFFER Services ****** ////////

    @Override
    public Offer addOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public Offer updateOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public void removeOffer(Integer idOffer) {
        offerRepository.deleteById(idOffer);
    }

    @Override
    public Offer retrieveOffer(Integer idOffer) {
        return offerRepository.findById(idOffer).orElse(null);
    }

    @Override
    public List<Offer> retrieveAllOffer() {
        return (List<Offer>) offerRepository.findAll();
    }
}
