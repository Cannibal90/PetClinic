package com.cannibal90.petclinic.WEB.service;

import com.cannibal90.petclinic.DAL.model.Prescription;
import com.cannibal90.petclinic.DAL.model.PrescriptionItem;
import com.cannibal90.petclinic.DAL.repository.PrescriptionRepository;
import com.cannibal90.petclinic.WEB.exception.ExceptionConst;
import com.cannibal90.petclinic.WEB.exception.NoDataFoundException;
import com.cannibal90.petclinic.WEB.exception.WrongParameterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

  private final PrescriptionRepository prescriptionRepository;
  private final PrescriptionItemService prescriptionItemService;

  public Prescription getPrescriptionById(Long id) {
    if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    return prescriptionRepository
        .findById(id)
        .orElseThrow(
            () -> {
              throw new NoDataFoundException(ExceptionConst.PRESCRIPTION_NOT_FOUND);
            });
  }

  public Prescription createPrescription(Prescription prescription) {
    if (prescription == null) {
      throw new WrongParameterException(ExceptionConst.WRONG_PRESCRIPTION_OBJECT);
    }

    return prescriptionRepository.save(prescription);
  }

  public Prescription updatePrescription(Long id, Prescription prescription) {
    if (prescription == null) {
      throw new WrongParameterException(ExceptionConst.WRONG_PRESCRIPTION_OBJECT);
    } else if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    return prescriptionRepository
        .findById(id)
        .map(
            foundPrescription -> {
              // TODO usunac roznice w prescriptionItems?
              Set<Long> foundPrescriptionItems =
                  foundPrescription.getPrescriptionItems().stream()
                      .map(PrescriptionItem::getId)
                      .collect(Collectors.toSet());
              Set<Long> prescriptionItems =
                  prescription.getPrescriptionItems().stream()
                      .map(PrescriptionItem::getId)
                      .collect(Collectors.toSet());
              foundPrescriptionItems.removeAll(prescriptionItems);
              foundPrescriptionItems.forEach(prescriptionItemService::deletePrescriptionItem);

              foundPrescription.setNote(prescription.getNote());
              foundPrescription.setPrescriptionItems(prescription.getPrescriptionItems());
              return prescriptionRepository.save(foundPrescription);
            })
        .orElseThrow(
            () -> {
              throw new NoDataFoundException(ExceptionConst.PRESCRIPTION_NOT_FOUND);
            });
  }

  public void deletePrescription(Long id) {
    if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    Prescription prescription =
        prescriptionRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  throw new NoDataFoundException(ExceptionConst.PRESCRIPTION_NOT_FOUND);
                });

    prescriptionRepository.delete(prescription);
  }
}
