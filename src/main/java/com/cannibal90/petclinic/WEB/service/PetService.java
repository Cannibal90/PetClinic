package com.cannibal90.petclinic.WEB.service;

import com.cannibal90.petclinic.DAL.model.Owner;
import com.cannibal90.petclinic.DAL.model.Pet;
import com.cannibal90.petclinic.DAL.model.Species;
import com.cannibal90.petclinic.DAL.repository.PetRepository;
import com.cannibal90.petclinic.WEB.exception.ExceptionConst;
import com.cannibal90.petclinic.WEB.exception.NoDataFoundException;
import com.cannibal90.petclinic.WEB.exception.WrongParameterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {

  private final PetRepository petRepository;
  private final OwnerService ownerService;

  private final SpeciesService speciesService;

  public Pet getPetById(Long id) {
    if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    return petRepository
        .findById(id)
        .orElseThrow(
            () -> {
              throw new NoDataFoundException(ExceptionConst.PET_NOT_FOUND);
            });
  }

  public List<Pet> getAllPetsByOwnerId(Long id) {
    if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    Owner petOwner = ownerService.getOwnerById(id);

    return petRepository.findAllByOwnersIsContaining(petOwner);
  }

  public List<Pet> getAllPetsBySpeciesName(String speciesName) {
    Species species = speciesService.getSpeciesByName(speciesName);

    return petRepository.findAllBySpecies(species);
  }

  public Pet registerPet(Pet pet) {
    if (pet == null) {
      throw new WrongParameterException(ExceptionConst.WRONG_PET_OBJECT);
    } else if (pet.getOwners() == null || pet.getOwners().size() == 0) {
      throw new WrongParameterException(ExceptionConst.NO_PET_OWNER);
    }

    checkOwnersAndSpecie(pet);

    return petRepository.save(pet);
  }

  public Pet updatePetData(Long id, Pet pet) {
    if (pet == null) {
      throw new WrongParameterException(ExceptionConst.WRONG_PET_OBJECT);
    } else if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    } else if (pet.getOwners() == null || pet.getOwners().size() == 0) {
      throw new WrongParameterException(ExceptionConst.NO_PET_OWNER);
    }

    checkOwnersAndSpecie(pet);

    return petRepository
        .findById(id)
        .map(
            foundPet -> {
              foundPet.setName(pet.getName());
              foundPet.setBirthDate(pet.getBirthDate());
              foundPet.setSpecies(pet.getSpecies());
              foundPet.setOwners(pet.getOwners());

              return petRepository.save(foundPet);
            })
        .orElseThrow(
            () -> {
              throw new NoDataFoundException(ExceptionConst.PET_NOT_FOUND);
            });
  }

  public void deletePetData(Long id) {
    if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    Pet pet =
        petRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  throw new NoDataFoundException(ExceptionConst.PET_NOT_FOUND);
                });

    petRepository.delete(pet);
  }

  protected void checkOwnersAndSpecie(Pet pet) {
    List<Long> ownersId = pet.getOwners().stream().map(Owner::getId).collect(Collectors.toList());
    ownersId.forEach(ownerService::getOwnerById);

    speciesService.getSpeciesById(pet.getSpecies().getId());
  }
}
