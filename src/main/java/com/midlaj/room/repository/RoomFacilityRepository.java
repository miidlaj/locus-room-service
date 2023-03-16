package com.midlaj.room.repository;

import com.midlaj.room.entity.RoomFacility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomFacilityRepository extends JpaRepository<RoomFacility, Long> {

    Optional<RoomFacility> findByName(String name);
}
