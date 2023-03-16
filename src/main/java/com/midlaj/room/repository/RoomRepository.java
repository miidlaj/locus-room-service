package com.midlaj.room.repository;

import com.midlaj.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {


    Optional<Room> findByRoomCode(String roomCode);

    List<Room> getRoomByResortId(Long id);
}
