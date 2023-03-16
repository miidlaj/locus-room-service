package com.midlaj.room.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
public class RoomRequestDTO {

    private Long resortId;

    private String roomCode;

    private String description;

    private Float price;

    private Long[] facilityIds;

    private Long roomTypeId;
}
