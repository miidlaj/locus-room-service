package com.midlaj.room.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomImageResponseDTO {

    private Long id;

    private String originalImageLink;

    private String resizedImageLink;
}
