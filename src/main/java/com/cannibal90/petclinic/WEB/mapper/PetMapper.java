package com.cannibal90.petclinic.WEB.mapper;

import com.cannibal90.petclinic.DAL.model.Pet;
import com.cannibal90.petclinic.WEB.dto.PetDTO;
import com.cannibal90.petclinic.WEB.dto.PetWithPersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SpeciesMapper.class})
public interface PetMapper {


    PetDTO petToPetDTO(Pet pet);

    Pet petDTOToPet(PetDTO petDTO);

    List<PetDTO> petListToPetDTOList(List<Pet> pets);

    List<Pet> petDTOListToPetList(List<PetDTO> petDTOList);

    @Named("petWithPerson")
    PetWithPersonDTO petToPetWithPersonDTO(Pet pet);


    Pet petWithPersonDTOToPet(PetWithPersonDTO petWithPersonDTO);

    List<PetWithPersonDTO> petListToPetWithPersonDTOList(List<Pet> pets);

    List<Pet> petWithPersonDTOListToPetList(List<PetWithPersonDTO> petWithPersonDTOList);

}
