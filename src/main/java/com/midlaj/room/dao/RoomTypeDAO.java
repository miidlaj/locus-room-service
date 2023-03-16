package com.midlaj.room.dao;

import com.midlaj.room.entity.RoomType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface RoomTypeDAO {

    List<RoomType> roomTypeList();

    RoomType findById(Long id);

    RoomType saveRoomType(RoomType roomType);

    void deleteById(Long id);

    Optional<RoomType> findByName(String name);
}
