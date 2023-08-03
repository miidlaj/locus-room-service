package com.midlaj.room.dto.response;

import com.midlaj.room.entity.RoomFacility;
import com.midlaj.room.entity.RoomImages;
import com.midlaj.room.entity.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomResponseAvailDTO {

    private Long id;

    private Float price;
    private List<ImageResponseDTO> images;

    private String roomCode;

    private String description;

    private Set<RoomFacility> roomFacilities;

    private String roomType;

    public void setImages(Set<RoomImages> imagesList) {

        List<ImageResponseDTO> imageListDTO = new ArrayList<>();
        for (RoomImages image: imagesList) {
            ImageResponseDTO imageResponseDTO = ImageResponseDTO.builder()
                    .id(image.getId())
                    .originalImageLink(image.getOriginalImageLink())
                    .resizedImageLink(image.getResizedImageLink())
                    .build();

            imageListDTO.add(imageResponseDTO);
        }

        this.images = imageListDTO;

    }
}
