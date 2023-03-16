package com.midlaj.room.controller;

import com.midlaj.room.dao.RoomTypeDAO;
import com.midlaj.room.dto.request.RoomTypeRequestDTO;
import com.midlaj.room.entity.RoomType;
import com.midlaj.room.exception.AlreadyPresentWithNameException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/rooms/type")
@Slf4j
public class RoomTypeController {

    @Autowired
    private RoomTypeDAO roomTypeDAO;

    @GetMapping()
    public ResponseEntity<?> roomTypeList() {
        log.info("Inside the roomTypeList in RoomTypeController");

        return ResponseEntity.ok(roomTypeDAO.roomTypeList());
    }

    @PostMapping()
    public ResponseEntity<?> saveRoomType(@Valid @RequestBody RoomTypeRequestDTO roomTypeRequestDTO) {
        log.info("Inside the saveRoomType in RoomTypeController");

        Optional<RoomType> roomTypeOptional = roomTypeDAO.findByName(roomTypeRequestDTO.getName());

        if (roomTypeOptional.isPresent()){
            log.warn("Room Type with " + roomTypeRequestDTO.getName() + " already exist!");
            throw new AlreadyPresentWithNameException("Room Type", roomTypeRequestDTO.getName());
        }
        RoomType roomType = RoomType.builder()
                .name(roomTypeRequestDTO.getName())
                .build();

        return ResponseEntity.ok(roomTypeDAO.saveRoomType(roomType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoomType(@PathVariable Long id) {
        log.info("Inside the deleteRoomType in RoomTypeController");

        roomTypeDAO.findById(id);
        roomTypeDAO.deleteById(id);

        return ResponseEntity.ok("Room Type Delete Successfully");
    }
}
