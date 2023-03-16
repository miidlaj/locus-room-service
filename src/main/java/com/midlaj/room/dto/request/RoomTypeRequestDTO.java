package com.midlaj.room.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data
public class RoomTypeRequestDTO {

    @NotNull(message = "The Room Type name must not be empty")
    @Size(max = 40, message = "Room Type name too long. Please enter maximum number of 40 characters.")
    @Size(min = 3, message = "Room Type name too short. Please enter minimum number of 3 characters..")
    private String name;

    public String getName() {
        return name.toUpperCase();
    }

}
