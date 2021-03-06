package com.cannibal90.petclinic.WEB.mapper;

import com.cannibal90.petclinic.DAL.model.PrescriptionItem;
import com.cannibal90.petclinic.WEB.dto.PrescriptionItemDTO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {MedicamentMapper.class})
public interface PrescriptionItemMapper {

    PrescriptionItemDTO prescriptionItemToPrescriptionItemDTO(PrescriptionItem prescriptionItem);

    PrescriptionItem prescriptionItemDTOToPrescriptionItem(PrescriptionItemDTO prescriptionItemDTO);

    Set<PrescriptionItemDTO> prescriptionItemSetToPrescriptionItemDTOSet(Set<PrescriptionItem> prescriptionItems);
    Set<PrescriptionItem> prescriptionItemDTOSetToPrescriptionItemSet(Set<PrescriptionItemDTO> prescriptionItemDTOSet);
}
