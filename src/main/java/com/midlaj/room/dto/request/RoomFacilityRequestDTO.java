package com.midlaj.room.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RoomFacilityRequestDTO {

    @NotNull(message = "Facility name must not be empty")
    @Size(max = 40, message = "Facility name too long. Please enter maximum number of 50 characters.")
    @Size(min = 3, message = "Facility name too short. Please enter minimum number of 3 characters..")
    private String name;

    @NotNull(message = "Facility description must not be empty")
    @Size(max = 150, message = "Facility description too long. Please enter maximum number of 50 characters.")
    @Size(min = 3, message = "Facility description too short. Please enter minimum number of 3 characters..")
    private String description;

    public String getName() {
        return name.toUpperCase();
    }
}
