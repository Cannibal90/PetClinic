package com.cannibal90.petclinic.WEB.mapper;
import com.cannibal90.petclinic.DAL.model.Medicament;
import com.cannibal90.petclinic.WEB.dto.MedicamentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicamentMapper {

    MedicamentDTO medicamentToMedicamentDTO(Medicament medicament);


    Medicament medicamentDTOToMedicament(MedicamentDTO medicamentDTO);
}
