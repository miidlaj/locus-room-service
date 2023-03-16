package com.midlaj.room.dao;

import com.midlaj.room.entity.Room;
import com.midlaj.room.exception.EntityNotFoundException;
import com.midlaj.room.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoomDAOImpl implements RoomDAO {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Room> getFacilityList() {
        return roomRepository.findAll();
    }

    @Override
    public Room findById(Long id) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if (roomOptional.isEmpty()) {
            throw new EntityNotFoundException("Room");
        }
        return roomOptional.get();
    }

    @Override
    public Room save(Room newRoom) {
        return roomRepository.save(newRoom);
    }

    @Override
    public Optional<Room> findByRoomCode(String roomCode) {
        return roomRepository.findByRoomCode(roomCode);
    }

    @Override
    public List<Room> getRoomsByResortId(Long id) {
        return roomRepository.getRoomByResortId(id);
    }

}
