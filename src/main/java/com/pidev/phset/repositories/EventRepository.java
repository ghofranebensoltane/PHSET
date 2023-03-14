package com.pidev.phset.repositories;

import com.pidev.phset.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends CrudRepository<Event, Integer> {



    Event findDistinctFirstByTypeEvent(TypeEvent e);


    @Query("select e from Event e " +
            "where e.titleEvent = ?1 or e.priceEvent = ?2 or e.nbPartEvent = ?3 or e.descriptionEvent = ?4 or e.dateS = ?5 or e.dateF = ?6 or e.modeEvent = ?7 or e.modePay = ?8 or e.typeEvent = ?9 or e.room = ?10")
    List<Event> findByTitleEventOrPriceEventOrNbPartEventOrDescriptionEventOrDateSOrDateFOrModeEventOrModePayOrTypeEventOrRoom(String a, Float b, Integer c, String d, LocalDateTime e, LocalDateTime f, Mode g, ModePay h, TypeEvent i, Room j);

    Long countByTypeEvent(TypeEvent typeEvent);

    Long countByModePay(ModePay modePay);

    Event findEventByActivationCode(String activationCode);

    @Query("select e from Event e where e.modePay='Paid'")
    Event findEventByModePay() ;

}