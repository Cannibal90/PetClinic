package com.cannibal90.petclinic.WEB.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {

    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String note;

    private PrescriptionDTO prescription;

    private RoomDTO room;

    private PetDTO pet;
}
