package com.midlaj.room.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RoomListResponseDTO {

    private Long roomId;
    private String roomCode;
    private Boolean enabled;
    private Float roomPrice;
    private Date updatedTime;
    private String roomType;
}
