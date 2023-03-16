package com.midlaj.room.dao;

import com.midlaj.room.entity.RoomFacility;
import com.midlaj.room.exception.EntityNotFoundException;
import com.midlaj.room.repository.RoomFacilityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class RoomFacilityDOAImpl implements RoomFacilityDAO {

    @Autowired
    private RoomFacilityRepository roomFacilityRepository;


    @Override
    public List<RoomFacility> getFacilityList() {

        return roomFacilityRepository.findAll();
    }

    @Override
    public RoomFacility saveFacility(RoomFacility roomFacility) {
        return roomFacilityRepository.save(roomFacility);
    }

    @Override
    public void deleteFacilityById(Long id) {
        roomFacilityRepository.deleteById(id);
    }

    @Override
    public RoomFacility findById(Long id) {
        Optional<RoomFacility> roomFacilityOptional = roomFacilityRepository.findById(id);
        if (roomFacilityOptional.isEmpty()) {
            throw new EntityNotFoundException("Room Facility");
        }
        return roomFacilityOptional.get();
    }

    @Override
    public Optional<RoomFacility> findByName(String name) {
        return roomFacilityRepository.findByName(name);
    }


}
