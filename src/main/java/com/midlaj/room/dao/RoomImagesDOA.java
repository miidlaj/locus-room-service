package com.midlaj.room.dao;

import com.midlaj.room.entity.RoomImages;

public interface RoomImagesDOA {
    void deleteImageById(Long id);

    RoomImages findById(Long id);
}
