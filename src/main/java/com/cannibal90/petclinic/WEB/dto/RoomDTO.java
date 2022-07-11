package com.cannibal90.petclinic.WEB.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class RoomDTO {

    private Long id;

    @NotNull(message = "Floor field is required")
    @PositiveOrZero(message = "Floor field must be positive value or zero")
    private Long floor;

    @NotNull(message = "Room description field is required")
    private String roomDescription;
}
