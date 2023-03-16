package com.midlaj.room.controller;

import com.midlaj.room.dao.RoomFacilityDAO;
import com.midlaj.room.dto.request.RoomFacilityRequestDTO;
import com.midlaj.room.entity.RoomFacility;
import com.midlaj.room.exception.AlreadyPresentWithNameException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/rooms/facility")
@Slf4j
public class RoomFacilityController {

    @Autowired
    private RoomFacilityDAO roomFacilityDAO;

    @GetMapping()
    public ResponseEntity<?> getFacilityList() {
        log.info("Inside the getFacilityList inside the RoomFacilityController");

        return ResponseEntity.ok(roomFacilityDAO.getFacilityList());
    }

    @PostMapping()
    public ResponseEntity<?> saveFacility(@Valid @RequestBody RoomFacilityRequestDTO roomFacilityRequestDTO) {
        log.info("Inside the saveFacility inside the RoomFacilityController");


        Optional<RoomFacility> roomFacilityCheck = roomFacilityDAO.findByName(roomFacilityRequestDTO.getName());

        if (roomFacilityCheck.isPresent()){
            log.warn("Facility with " + roomFacilityRequestDTO.getName() + " already exist!");
            throw new AlreadyPresentWithNameException("Facility", roomFacilityRequestDTO.getName());
        }

        RoomFacility roomFacility = RoomFacility.builder()
                .name(roomFacilityRequestDTO.getName())
                .description(roomFacilityRequestDTO.getDescription())
                .build();

        return ResponseEntity.ok(roomFacilityDAO.saveFacility(roomFacility));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFacility(@PathVariable Long id) {
        log.info("Inside the deleteFacility inside the RoomFacilityController");

        roomFacilityDAO.findById(id);
        roomFacilityDAO.deleteFacilityById(id);
        return ResponseEntity.ok().body("Facility deleted successfully");
    }
}
