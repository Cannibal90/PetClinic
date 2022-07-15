package com.cannibal90.petclinic.WEB.mapper;

import com.cannibal90.petclinic.DAL.model.Appointment;
import com.cannibal90.petclinic.DAL.model.Doctor;
import com.cannibal90.petclinic.DAL.model.Owner;
import com.cannibal90.petclinic.DAL.model.Person;
import com.cannibal90.petclinic.WEB.dto.AppointmentDTO;
import com.cannibal90.petclinic.WEB.dto.DoctorDTO;
import com.cannibal90.petclinic.WEB.dto.OwnerDTO;
import com.cannibal90.petclinic.WEB.dto.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AppointmentMapper.class})
public interface DoctorMapper {

    DoctorDTO doctorToDoctorDTO(Doctor doctor);

    Doctor PersonDTOToDoctor(PersonDTO personDTO);

    PersonDTO doctorToPersonDTO(Doctor doctor);
}
