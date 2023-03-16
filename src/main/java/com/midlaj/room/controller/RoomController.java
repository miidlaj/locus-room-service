package com.midlaj.room.controller;

import com.midlaj.room.dto.request.RoomRequestDTO;
import com.midlaj.room.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/rooms")
@Slf4j
public class RoomController {

    @Autowired
    private RoomService roomService;


    @PostMapping()
    public ResponseEntity<?> saveRoom(@RequestBody RoomRequestDTO roomRequestDTO) {
        log.info("Inside saveRoom of RoomController");
        return roomService.saveRoom(roomRequestDTO);
    }

    @PostMapping(value = "/image",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> setImage(@RequestParam("image") MultipartFile image, @RequestParam("roomId") Long roomId) {
        log.info("Inside setImage of RoomController");

        if (image.getSize() <= 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Provide Valid Image!");
        }
        return roomService.addImage(image, roomId);
    }

    @DeleteMapping("/{roomId}/image/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable Long roomId, @PathVariable Long imageId) {
        log.info("Inside deleteImage of RoomController");
        return roomService.deleteImage(roomId, imageId);
    }

    @PutMapping("/status/{roomId}")
    public ResponseEntity<?> changeEnabledStatus(@PathVariable Long roomId) {
        log.info("Inside changeEnabledStatus of RoomController");

        return roomService.changeStatus(roomId);
    }

    @GetMapping("/resort/{id}")
    public ResponseEntity<?> getRoomsByResortId(@PathVariable Long id) {
        log.info("Inside getRoomByResortId of RoomController");
        return roomService.getRoomsByResortId(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable Long id) {
        log.info("Inside getRoomById of RoomController");
        return roomService.getRoomById(id);
    }

}
