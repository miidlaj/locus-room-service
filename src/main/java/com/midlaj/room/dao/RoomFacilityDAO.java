package com.midlaj.room.dao;

import com.midlaj.room.entity.RoomFacility;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface RoomFacilityDAO {

    List<RoomFacility> getFacilityList();

    RoomFacility saveFacility(RoomFacility roomFacility);

    void deleteFacilityById(Long id);

    RoomFacility findById(Long id);

    Optional<RoomFacility> findByName(String name);

}
