package com.midlaj.room.dao;

import com.midlaj.room.entity.Room;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface RoomDAO {

    List<Room> getFacilityList();

    Room findById(Long id);

    Room save(Room newRoom);

    Optional<Room> findByRoomCode(String roomCode);

    List<Room> getRoomsByResortId(Long id);

    List<Room> findRoomByResortId(Long resortId);
}
