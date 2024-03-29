package com.midlaj.room.service;

import com.midlaj.room.VO.ResponseTemplateVO;
import com.midlaj.room.dto.request.RoomAvailRequestDTO;
import com.midlaj.room.dto.request.RoomRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public interface RoomService {
    ResponseEntity<?> saveRoom(RoomRequestDTO roomRequestDTO);

    ResponseEntity<?> addImage(MultipartFile image, Long roomId);

    ResponseEntity<?> getRoomsByResortId(Long id);

    ResponseEntity<?> getRoomById(Long id);

    ResponseEntity<?> deleteImage(Long roomId, Long imageId);

    ResponseEntity<?> changeStatus(Long roomId);

    ResponseEntity<?> getRoomByAvail(Long resortId, LocalDate checkIn, LocalDate checkOut);

    ResponseEntity<?> getRoomDetailsForBookings(Long roomId);
}
