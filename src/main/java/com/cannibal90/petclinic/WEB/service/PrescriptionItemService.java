package com.cannibal90.petclinic.WEB.service;

import com.cannibal90.petclinic.DAL.model.Medicament;
import com.cannibal90.petclinic.DAL.model.PrescriptionItem;
import com.cannibal90.petclinic.DAL.repository.PrescriptionItemRepository;
import com.cannibal90.petclinic.WEB.exception.ExceptionConst;
import com.cannibal90.petclinic.WEB.exception.NoDataFoundException;
import com.cannibal90.petclinic.WEB.exception.WrongParameterException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PrescriptionItemService {

  private final PrescriptionItemRepository prescriptionItemRepository;
  private final MedicamentService medicamentService;

  public PrescriptionItem getPrescriptionItemById(Long id) {
    if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    return prescriptionItemRepository
        .findById(id)
        .orElseThrow(
            () -> {
              throw new NoDataFoundException(ExceptionConst.PRESCRIPTION_ITEM_NOT_FOUND);
            });
  }

  public PrescriptionItem createPrescriptionItem(PrescriptionItem prescriptionItem) {
    if (prescriptionItem == null) {
      throw new WrongParameterException(ExceptionConst.WRONG_PRESCRIPTION_ITEM_OBJECT);
    }

      Medicament medicament =
              medicamentService.getMedicamentById(prescriptionItem.getMedicament().getId());
    prescriptionItem.setMedicament(medicament);//TODO check if we need to set variable or only check if medicament exists

    return prescriptionItemRepository.save(prescriptionItem);
  }

  public PrescriptionItem updatePrescriptionItem(Long id, PrescriptionItem prescriptionItem) {
    if (prescriptionItem == null) {
      throw new WrongParameterException(ExceptionConst.WRONG_PRESCRIPTION_ITEM_OBJECT);
    } else if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    return prescriptionItemRepository
        .findById(id)
        .map(
            foundPrescriptionItem -> {
              Medicament medicament =
                  medicamentService.getMedicamentById(prescriptionItem.getMedicament().getId());

              foundPrescriptionItem.setQuantity(prescriptionItem.getQuantity());
              foundPrescriptionItem.setMedicament(medicament);

              return prescriptionItemRepository.save(foundPrescriptionItem);
            })
        .orElseThrow(
            () -> {
              throw new NoDataFoundException(ExceptionConst.PRESCRIPTION_ITEM_NOT_FOUND);
            });
  }

  public void deletePrescriptionItem(Long id) {
    if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    PrescriptionItem prescriptionItem =
        prescriptionItemRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  throw new NoDataFoundException(ExceptionConst.PRESCRIPTION_ITEM_NOT_FOUND);
                });

    prescriptionItemRepository.delete(prescriptionItem);
  }
}
