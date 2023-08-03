package com.midlaj.room.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponseDTO {

    private Long id;

    private String originalImageLink;

    private String resizedImageLink;

}
