package com.cannibal90.petclinic.WEB.mapper;

import com.cannibal90.petclinic.DAL.model.Medicament;
import com.cannibal90.petclinic.WEB.dto.MedicamentDTO;
import com.cannibal90.petclinic.WEB.dto.PageDTO;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MedicamentMapper {

  MedicamentDTO medicamentToMedicamentDTO(Medicament medicament);

  Medicament medicamentDTOToMedicament(MedicamentDTO medicamentDTO);

  default PageDTO medicamentPageToPageDTO(Page<Medicament> medicaments) {
    if (medicaments == null) {
      return null;
    }

    PageDTO pageDTO = new PageDTO();
    pageDTO.setTotalPages(medicaments.getTotalPages());
    pageDTO.setTotalItems(medicaments.getTotalElements());
    pageDTO.setCurrentPage(medicaments.getNumber());
    pageDTO.setItems(
        medicaments.get().map(this::medicamentToMedicamentDTO).collect(Collectors.toList()));

    return pageDTO;
  }
}
