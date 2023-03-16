package com.midlaj.room.dto.response;

import com.midlaj.room.entity.RoomFacility;
import com.midlaj.room.entity.RoomImages;
import com.midlaj.room.entity.RoomType;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Builder
@Data
public class RoomResponseDTO {

    private Long roomId;

    private String roomCode;

    private Long resortId;

    private String description;

    private Boolean enabled;

    private Float roomPrice;

    private Date updatedTime;

    private Date createdTime;

    private Set<RoomFacility> facilities;

    private RoomType roomType;

    private String roomCreateStatus;

    private List<RoomImageResponseDTO> roomImages;

    public void setRoomImageResponse(Set<RoomImages> roomImages) {
        List<RoomImageResponseDTO> roomImageResponses = new ArrayList<>();
        for (RoomImages images: roomImages) {
            roomImageResponses.add(RoomImageResponseDTO.builder()
                    .id(images.getId())
                    .originalImageLink(images.getOriginalImageLink())
                    .resizedImageLink(images.getResizedImageLink())
                    .build());
        }
        this.roomImages = roomImageResponses;
    }
}

