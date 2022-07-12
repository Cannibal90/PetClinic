package com.cannibal90.petclinic.WEB.mapper;

import com.cannibal90.petclinic.DAL.model.Prescription;
import com.cannibal90.petclinic.WEB.dto.PrescriptionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PrescriptionItemMapper.class})
public interface PrescriptionMapper {

    PrescriptionDTO prescriptionToPrescriptionDTO(Prescription prescription);

    Prescription prescriptionDTOToPrescription(PrescriptionDTO prescriptionDTO);
}
