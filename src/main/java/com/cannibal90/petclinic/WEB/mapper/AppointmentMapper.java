package com.cannibal90.petclinic.WEB.mapper;

import com.cannibal90.petclinic.DAL.model.Appointment;
import com.cannibal90.petclinic.WEB.dto.AppointmentDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {PrescriptionMapper.class, RoomMapper.class, PetMapper.class})
public interface AppointmentMapper {

    AppointmentDTO appointmentToAppointmentDTO(Appointment appointment);

    Set<AppointmentDTO> appointmentSetToAppointmentDTOSet(Set<Appointment> appointments);
}
