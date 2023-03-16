package com.midlaj.room.dao;

import com.midlaj.room.entity.RoomImages;
import com.midlaj.room.exception.EntityNotFoundException;
import com.midlaj.room.repository.RoomImagesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
public class RoomImagesDAOImpl implements RoomImagesDOA{

    @Autowired
    private RoomImagesRepository roomImagesRepository;

    @Override
    public void deleteImageById(Long id) {
        log.info("Inside the deleteImageById method in RoomImagesDAOImpl");

        roomImagesRepository.deleteById(id);
    }

    @Override
    public RoomImages findById(Long id) {
        Optional<RoomImages> imagesOptional = roomImagesRepository.findById(id);

        if (imagesOptional.isEmpty()) {
            throw new EntityNotFoundException("Image");
        }
        return imagesOptional.get();
    }
}
