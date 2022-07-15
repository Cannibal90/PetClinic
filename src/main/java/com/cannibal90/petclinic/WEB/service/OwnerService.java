package com.cannibal90.petclinic.WEB.service;

import com.cannibal90.petclinic.DAL.model.Owner;
import com.cannibal90.petclinic.DAL.repository.OwnerRepository;
import com.cannibal90.petclinic.WEB.exception.ExceptionConst;
import com.cannibal90.petclinic.WEB.exception.NoDataFoundException;
import com.cannibal90.petclinic.WEB.exception.WrongParameterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerService {

  public final OwnerRepository ownerRepository;

  public Owner getOwnerById(Long id) {
    if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    return ownerRepository
        .findById(id)
        .orElseThrow(
            () -> {
              throw new NoDataFoundException(ExceptionConst.OWNER_NOT_FOUND);
            });
  }

  public Owner createOwner(Owner owner) {
    if (owner == null) {
      throw new WrongParameterException(ExceptionConst.WRONG_OWNER_OBJECT);
    }

    return ownerRepository.save(owner);
  }

  public Owner updateOwner(Long id, Owner owner) {
    if (owner == null) {
      throw new WrongParameterException(ExceptionConst.WRONG_OWNER_OBJECT);
    } else if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    return ownerRepository
        .findById(id)
        .map(
            foundOwner -> {
              foundOwner.setFirstName(owner.getFirstName());
              foundOwner.setLastName(owner.getLastName());
              foundOwner.setPhone(owner.getPhone());
              foundOwner.setEmail(owner.getEmail());

              return ownerRepository.save(foundOwner);
            })
        .orElseThrow(
            () -> {
              throw new NoDataFoundException(ExceptionConst.OWNER_NOT_FOUND);
            });
  }

  public void deleteOwner(Long id) {
    if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    //TODO trigger na bazie danych wszystkie pety bez ownerow do usuniecia
    Owner owner =
        ownerRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  throw new NoDataFoundException(ExceptionConst.OWNER_NOT_FOUND);
                });

    ownerRepository.delete(owner);
  }
}
