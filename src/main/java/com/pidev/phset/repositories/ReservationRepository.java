package com.pidev.phset.repositories;

import com.pidev.phset.entities.Reservation;
import com.pidev.phset.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

    List<Reservation> findReservationsByAccount_User(User u);
}