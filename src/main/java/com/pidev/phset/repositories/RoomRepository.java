package com.pidev.phset.repositories;

import com.pidev.phset.entities.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomRepository extends CrudRepository<Room, Integer> {

    List<Room> findRoomByAvailableIsTrue();
}
