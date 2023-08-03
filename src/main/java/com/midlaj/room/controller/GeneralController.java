package com.midlaj.room.controller;

import com.midlaj.room.dto.request.RoomAvailRequestDTO;
import com.midlaj.room.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/rooms")
@Slf4j
public class GeneralController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/availability/{resortId}/{checkIn}/{checkOut}")
    public ResponseEntity<?> getRoomAvailability(@PathVariable Long resortId, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkIn, @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable LocalDate checkOut){
        log.info("Inside the getRoomAvailability method in General Controller");

        System.out.println(checkIn + " - " + checkOut);
        return roomService.getRoomByAvail(resortId, checkIn, checkIn);
    }
}
