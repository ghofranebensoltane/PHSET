package com.pidev.phset.services;

import com.pidev.phset.entities.*;
import java.util.List;

public interface IAdmissionServices {

    //////// ****** CANDIDACY Services ****** ////////

    Candidacy addCandidacy(Candidacy candidacy);
    Candidacy updateCandidacy(Candidacy candidacy);
    void removeCandidacy(Integer idcandidacy);
    Candidacy retrieveCandidacy(Integer idcandidacy);
    List<Candidacy> retrieveAllCandidacy();

    //////// ****** CLASSROOM Services ****** ////////

    Classroom addClassroom(Classroom classroom);
    Classroom updateClassroom(Classroom classroom);
    void removeClassroom(Integer idClassroom);
    Classroom retrieveClassroom(Integer idClassroom);
    List<Classroom> retrieveAllClassroom();


    //////// ****** CRITERIA Services ****** ////////

    Criteria addCriteria(Criteria criteria);
    Criteria updateCriteria(Criteria criteria);
    void removeCriteria(Integer idCriteria);
    Criteria retrieveCriteria(Integer idCriteria);
    List<Criteria> retrieveAllCriteria();

    //////// ****** INSCRIPTION Services ****** ////////

    Inscription addInscription(Inscription inscription);
    Inscription updateInscription(Inscription inscription);
    void removeInscription(Integer idInscription);
    Inscription retrieveInscription(Integer idInscription);
    List<Inscription> retrieveAllInscription();


    //////// ****** INTERVIEW Services ****** ////////

    Interview addInterview(Interview interview);
    Interview updateInterview(Interview interview);
    void removeInterview(Integer idInterview);
    Interview retrieveInterview(Integer idInterview);
    List<Interview> retrieveAllInterview();

    //////// ****** JURYAPPRECIATION Services ****** ////////

    JuryAppreciation addJuryAppreciation(JuryAppreciation juryAppreciation);
    JuryAppreciation updateJuryAppreciation(JuryAppreciation juryAppreciation);
    void removeJuryAppreciation(Integer idJuryAppreciation);
    JuryAppreciation retrieveJuryAppreciation(Integer idJuryAppreciation);
    List<JuryAppreciation> retrieveAllJuryAppreciation();

    //////// ****** OFFER Services ****** ////////

    Offer addOffer(Offer offer);
    Offer updateOffer(Offer offer);
    void removeOffer(Integer idOffer);
    Offer retrieveOffer(Integer idOffer);
    List<Offer> retrieveAllOffer();

}
