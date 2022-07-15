package com.cannibal90.petclinic.WEB.service;

import com.cannibal90.petclinic.DAL.model.Species;
import com.cannibal90.petclinic.DAL.repository.SpeciesRepository;
import com.cannibal90.petclinic.WEB.exception.ExceptionConst;
import com.cannibal90.petclinic.WEB.exception.NoDataFoundException;
import com.cannibal90.petclinic.WEB.exception.WrongParameterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeciesService {

  private final SpeciesRepository speciesRepository;

  public Species getSpeciesById(Long id) {
    if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    return speciesRepository
        .findById(id)
        .orElseThrow(
            () -> {
              throw new NoDataFoundException(ExceptionConst.SPECIES_NOT_FOUND);
            });
  }

  public Species getSpeciesByName(String name) {
    if (name == null || name.length() == 0) {
      throw new NoDataFoundException(ExceptionConst.WRONG_SPECIES_NAME);
    }

    return speciesRepository
        .findSpeciesBySpeciesNameIgnoreCase(name)
        .orElseThrow(
            () -> {
              throw new NoDataFoundException(ExceptionConst.SPECIES_NOT_FOUND);
            });
  }

  public List<Species> getAllSpecies() {
    return speciesRepository.findAll();
  }

  public Species createSpecies(Species newSpecies) {
    if (newSpecies == null) {
      throw new WrongParameterException(ExceptionConst.WRONG_SPECIES_OBJECT);
    }

    return speciesRepository.save(newSpecies);
  }

  public Species updateSpecies(Long id, Species species) {
    if (species == null) {
      throw new WrongParameterException(ExceptionConst.WRONG_SPECIES_OBJECT);
    } else if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    return speciesRepository
        .findById(id)
        .map(
            foundSpecies -> {
              foundSpecies.setSpeciesName(species.getSpeciesName());
              return speciesRepository.save(foundSpecies);
            })
        .orElseThrow(
            () -> {
              throw new NoDataFoundException(ExceptionConst.SPECIES_NOT_FOUND);
            });
  }

  public void deleteSpecies(Long id) {
    if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    Species species =
        speciesRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  throw new NoDataFoundException(ExceptionConst.SPECIES_NOT_FOUND);
                });

    speciesRepository.delete(species);
  }
}
