package com.cannibal90.petclinic.WEB.mapper;

import com.cannibal90.petclinic.DAL.model.Species;
import com.cannibal90.petclinic.WEB.dto.SpeciesDTO;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface SpeciesMapper {

    SpeciesDTO speciesToSpeciesDTO(Species species);

    Species speciesDTOToSpecies(SpeciesDTO speciesDTO);

    List<SpeciesDTO> speciesListToSpeciesListDTO(List<Species> species);
}
