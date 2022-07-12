package com.cannibal90.petclinic.WEB.mapper;

import com.cannibal90.petclinic.DAL.model.Medicament;
import com.cannibal90.petclinic.WEB.dto.MedicamentDTO;
import com.cannibal90.petclinic.WEB.dto.PageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MedicamentMapper {

  MedicamentDTO medicamentToMedicamentDTO(Medicament medicament);

  Medicament medicamentDTOToMedicament(MedicamentDTO medicamentDTO);

  @Named("pageToList")
  default List<Object> pageToList(Page<Medicament> medicaments) {
    return medicaments.get().map(this::medicamentToMedicamentDTO).collect(Collectors.toList());
  }

  @Mapping(source = "page", target = "items", qualifiedByName = "pageToList")
  PageDTO medicamentPageToPageDTO(Page<Medicament> page);

}
