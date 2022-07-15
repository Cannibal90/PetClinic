package com.cannibal90.petclinic.WEB.dto;

import lombok.Data;

import java.util.Set;

@Data
public class DoctorDTO extends PersonDTO{

    Set<AppointmentDTO> appointment;
}
