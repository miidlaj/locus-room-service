package com.midlaj.room.service;

import com.midlaj.room.dao.RoomDAO;
import com.midlaj.room.dao.RoomFacilityDAO;
import com.midlaj.room.dao.RoomImagesDOA;
import com.midlaj.room.dao.RoomTypeDAO;
import com.midlaj.room.dto.request.RoomRequestDTO;
import com.midlaj.room.dto.response.RoomImageResponseDTO;
import com.midlaj.room.dto.response.RoomListResponseDTO;
import com.midlaj.room.dto.response.RoomResponseDTO;
import com.midlaj.room.entity.*;
import com.midlaj.room.exception.EntityNotFoundException;
import com.midlaj.room.util.AmazonS3Util;
import com.midlaj.room.util.Constants;
import com.midlaj.room.util.CustomMultipartFile;
import com.midlaj.room.util.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Slf4j
public class RoomServiceImpl implements RoomService{
    
    @Autowired
    private RoomDAO roomDAO;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RoomFacilityDAO roomFacilityDAO;

    @Autowired
    private RoomImagesDOA roomImagesDOA;

    @Autowired
    private RoomTypeDAO roomTypeDAO;

    @Value("${services.resort}")
    private static String RESORT_SERVICE_URL;

    @Override
    public ResponseEntity<?> saveRoom(RoomRequestDTO roomRequestDTO) {
        log.info("Inside saveRoom of RoomService");

        Optional<Room> roomOptional = roomDAO.findByRoomCode(roomRequestDTO.getRoomCode());

        if (roomOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Room with code " + roomRequestDTO.getRoomCode() + " is Already present");
        }

//        // Check Resort is Present
//        String url = "http://localhost:9001/api/resort/roomService/" + roomRequestDTO.getResortId();
//        String response = null;
//        try {
//            response = restTemplate.getForObject(url, String.class);
//        } catch (HttpClientErrorException e) {
//            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find resort.");
//            }
//        }
//
//        System.out.println(response);


        Room newRoom = new Room();

        newRoom.setRoomCode(roomRequestDTO.getRoomCode());
        newRoom.setDescription(roomRequestDTO.getDescription());
        newRoom.setPrice(roomRequestDTO.getPrice());
        newRoom.setEnabled(false);
        newRoom.setResortId(roomRequestDTO.getResortId());
        newRoom.setCreatedTime(new Date());
        newRoom.setUpdateTime(new Date());
        newRoom.setRoomCreateStatus(RoomCreateStatus.FORM_COMPLETED);


        // Room Facilities
        try {
            newRoom.setFacilities(setRoomFacilityForNewRoom(roomRequestDTO.getFacilityIds()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Provided Facilities are not valid. Please check again.");
        }

        // Room Type
        try {
            RoomType roomType = roomTypeDAO.findById(roomRequestDTO.getRoomTypeId());
            newRoom.setRoomType(roomType);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Provided room type is not valid.");
        }

        return ResponseEntity.ok(roomDAO.save(newRoom));
    }

    @Override
    public ResponseEntity<?> addImage(MultipartFile image, Long roomId) {
        log.info("Inside addImage method of Room Service");

        Room roomInDB = roomDAO.findById(roomId);

        Set<RoomImages> images = roomInDB.getImages();
        if (images.size() > 5) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot add more than 5 Images.");
        }
        MultipartFile originalImageFile = image;
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(originalImageFile.getOriginalFilename()));

        String uuid = UUID.randomUUID().toString();
        originalFileName = uuid + "_" + originalFileName;

        String uploadDir = "roomImages/" + roomId;

        //Compress the image for thumbnails
        CustomMultipartFile compressedMultipartFile;
        String compressedFileName = "compressed_" + originalFileName ;
        try {
            String contentType = originalImageFile.getContentType();
            String[] parts = contentType.split("/");
            String formatName = parts[1];
            byte[] compressedFileByte = ImageUtils.compressImage(originalImageFile, formatName);
            compressedMultipartFile = new CustomMultipartFile(compressedFileByte, compressedFileName);
            System.out.println(compressedMultipartFile.getSize());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot compress Provided Image.");
        }

        try {
            //Uploading original file
            AmazonS3Util.uploadFile(uploadDir, originalFileName, originalImageFile.getInputStream());

            //Uploading Compressed Image
            AmazonS3Util.uploadFile(uploadDir, compressedFileName, compressedMultipartFile.getInputStream());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot upload Image.");
        } catch (S3Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot upload Image into Cloud at this moment.");
        }

        roomInDB.addImage(originalFileName, compressedFileName);

        if (roomInDB.getImages().size() >= 3) {
            roomInDB.setRoomCreateStatus(RoomCreateStatus.IMAGES_ADDED);
            roomInDB.setEnabled(true);
        } else {
            roomInDB.setEnabled(false);
        }

        Room savedRoom = saveAndUpdateRoom(roomInDB);

        List<RoomImageResponseDTO> roomImageResponses = new ArrayList<>();
        for (RoomImages savedImages: savedRoom.getImages()) {
            roomImageResponses.add(RoomImageResponseDTO.builder()
                    .id(savedImages.getId())
                    .originalImageLink(savedImages.getOriginalImageLink())
                    .resizedImageLink(savedImages.getResizedImageLink())
                    .build());
        }

        return ResponseEntity.ok().body(roomImageResponses);
    }

    @Override
    public ResponseEntity<?> getRoomsByResortId(Long id) {
        log.info("Inside getRoomsByResortId method of Room Service");
        List<Room> roomList = roomDAO.getRoomsByResortId(id);
        if (roomList.size() == 0) {
            return ResponseEntity.ok(roomList);
        }
        return ResponseEntity.ok(buildRoomListResponseDTO(roomList));
    }

    @Override
    public ResponseEntity<?> getRoomById(Long id) {
        log.info("Inside getRoomById method of Room Service");

        Room roomInDb = roomDAO.findById(id);
        return ResponseEntity.ok(buildRoomResponseDTO(roomInDb));
    }

    @Override
    public ResponseEntity<?> deleteImage(Long roomId, Long imageId) {
        Room roomInDb = roomDAO.findById(roomId);

        String roomImages = "roomImages/"+roomInDb.getId();
        List<String> listObjectKeys = AmazonS3Util.listFolder(roomImages);

        RoomImages roomImageInDb = roomImagesDOA.findById(imageId);
        for (String objectKey : listObjectKeys) {
            if (objectKey.contains(roomImageInDb.getResizedImage()) || objectKey.contains(roomImageInDb.getOriginalImage())) {
                try {
                    AmazonS3Util.deleteFile(objectKey);
                } catch (S3Exception e) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot upload Image into Cloud at this moment.");
                }

            }
        }
        roomImagesDOA.deleteImageById(imageId);
        if ((roomInDb.getImages().size()) < 3) {
            roomInDb.setEnabled(false);
            roomInDb.setRoomCreateStatus(RoomCreateStatus.FORM_COMPLETED);
            saveAndUpdateRoom(roomInDb);
        }

        return ResponseEntity.ok("Image delete successfully");
    }

    @Override
    public ResponseEntity<?> changeStatus(Long roomId) {
        Room room = roomDAO.findById(roomId);

        if (room.getRoomCreateStatus() != RoomCreateStatus.IMAGES_ADDED) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot enable Room. Images are not uploaded");
        }

        room.setEnabled(true);
        saveAndUpdateRoom(room);
        return ResponseEntity.ok("Enable status changed successfully");
    }

    public RoomResponseDTO buildRoomResponseDTO(Room room) {
        RoomResponseDTO responseDTO = RoomResponseDTO.builder()
                .roomId(room.getId())
                .roomPrice(room.getPrice())
                .roomCode(room.getRoomCode())
                .roomType(room.getRoomType())
                .updatedTime(room.getUpdateTime())
                .createdTime(room.getCreatedTime())
                .enabled(room.getEnabled())
                .description(room.getDescription())
                .facilities(room.getFacilities())
                .resortId(room.getResortId())
                .roomCreateStatus(room.getRoomCreateStatus().toString())
                .build();

        responseDTO.setRoomImageResponse(room.getImages());
        return responseDTO;
    }

    public List<RoomListResponseDTO> buildRoomListResponseDTO(List<Room> roomList) {
        List<RoomListResponseDTO> roomListResponseDTO = new ArrayList<>();
        for (Room room : roomList) {
            RoomListResponseDTO responseDTO = RoomListResponseDTO.builder()
                    .roomType(room.getRoomType().getName())
                    .roomCode(room.getRoomCode())
                    .enabled(room.getEnabled())
                    .roomPrice(room.getPrice())
                    .roomId(room.getId())
                    .updatedTime(room.getUpdateTime())
                    .build();
            roomListResponseDTO.add(responseDTO);
        }
        return roomListResponseDTO;
    }
    // Helper Method for updating Time and Save Room
    public Room saveAndUpdateRoom(Room room) {
        room.setUpdateTime(new Date());
        return roomDAO.save(room);
    }

    // Helper Method for setting Facilities
    synchronized private Set<RoomFacility> setRoomFacilityForNewRoom(Long[] facilityIds) {

        Set<RoomFacility> roomFacilities = new HashSet<>();
        for (Long facilityId: facilityIds) {
            RoomFacility roomFacility = roomFacilityDAO.findById(facilityId);
            roomFacilities.add(roomFacility);
        }

        return roomFacilities;
    }

}
