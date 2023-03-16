package com.midlaj.room.dao;

import com.midlaj.room.entity.RoomType;
import com.midlaj.room.exception.EntityNotFoundException;
import com.midlaj.room.repository.RoomTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class RoomTypeDAOImpl implements RoomTypeDAO {

    @Autowired
    private RoomTypeRepository roomTypeRepository;


    @Override
    public List<RoomType> roomTypeList() {

        return roomTypeRepository.findAll();
    }

    @Override
    public RoomType findById(Long id) {
        Optional<RoomType> roomTypeOptional = roomTypeRepository.findById(id);
        if (roomTypeOptional.isEmpty()) {
            throw new EntityNotFoundException("Room Type");
        }
        return roomTypeOptional.get();
    }

    @Override
    public RoomType saveRoomType(RoomType roomType) {

        return roomTypeRepository.save(roomType);
    }

    @Override
    public void deleteById(Long id) {
        roomTypeRepository.deleteById(id);
    }

    @Override
    public Optional<RoomType> findByName(String name) {
        return roomTypeRepository.findByName(name);
    }
}
