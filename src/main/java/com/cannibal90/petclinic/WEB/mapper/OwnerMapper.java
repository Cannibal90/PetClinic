package com.cannibal90.petclinic.WEB.mapper;

import com.cannibal90.petclinic.DAL.model.Owner;
import com.cannibal90.petclinic.WEB.dto.OwnerDTO;
import com.cannibal90.petclinic.WEB.dto.PersonDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PetMapper.class})
public interface OwnerMapper {

    OwnerDTO ownerToOwnerDTO(Owner owner);

    Owner ownerDTOToOwner(OwnerDTO ownerDTO);

    PersonDTO ownerToPersonDTO(Owner owner);

    Owner PersonDTOToOwner(PersonDTO personDTO);

}
