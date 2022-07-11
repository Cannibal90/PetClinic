package com.cannibal90.petclinic.WEB.service;

import com.cannibal90.petclinic.DAL.model.Medicament;
import com.cannibal90.petclinic.DAL.repository.MedicamentRepository;
import com.cannibal90.petclinic.WEB.exception.ExceptionConst;
import com.cannibal90.petclinic.WEB.exception.NoDataFoundException;
import com.cannibal90.petclinic.WEB.exception.WrongParameterException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicamentService {

  private final MedicamentRepository medicamentRepository;

  public Medicament getMedicamentById(Long id) {
    if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    return medicamentRepository
        .findById(id)
        .orElseThrow(
            () -> {
              throw new NoDataFoundException(ExceptionConst.MEDICAMENT_NOT_FOUND);
            });
  }

    public Page<Medicament> getAllMedicaments(Pageable page) {
        return medicamentRepository.findAll(page);
    }

  public Medicament createMedicament(Medicament newMedicament) {
    if (newMedicament == null) {
      throw new WrongParameterException(ExceptionConst.WRONG_MEDICAMENT_OBJECT);
    }

    return medicamentRepository.save(newMedicament);
  }

  public Medicament updateMedicament(Long id, Medicament medicament) {
    if (medicament == null) {
      throw new WrongParameterException(ExceptionConst.WRONG_MEDICAMENT_OBJECT);
    } else if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    return medicamentRepository
        .findById(id)
        .map(
            foundMedicament -> {
              foundMedicament.setName(medicament.getName());
              foundMedicament.setPrice(medicament.getPrice());
              return medicamentRepository.save(foundMedicament);
            })
        .orElseThrow(
            () -> {
              throw new NoDataFoundException(ExceptionConst.MEDICAMENT_NOT_FOUND);
            });
  }

  public void deleteMedicament(Long id) {
    if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    Medicament medicament =
        medicamentRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  throw new NoDataFoundException(ExceptionConst.MEDICAMENT_NOT_FOUND);
                });

    medicamentRepository.delete(medicament);
  }
}
