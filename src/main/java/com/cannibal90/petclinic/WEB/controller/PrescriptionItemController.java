package com.cannibal90.petclinic.WEB.controller;

import com.cannibal90.petclinic.DAL.model.PrescriptionItem;
import com.cannibal90.petclinic.WEB.dto.PrescriptionItemDTO;
import com.cannibal90.petclinic.WEB.mapper.PrescriptionItemMapper;
import com.cannibal90.petclinic.WEB.service.PrescriptionItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/prescriptionItem")
@RequiredArgsConstructor
public class PrescriptionItemController {

  private final PrescriptionItemService prescriptionItemService;
  private final PrescriptionItemMapper prescriptionItemMapper;

  @GetMapping("/{id}")
  public ResponseEntity<?> getPrescriptionItemById(@PathVariable(name = "id") Long id) {
    PrescriptionItemDTO prescriptionItemDTO =
        prescriptionItemMapper.prescriptionItemToPrescriptionItemDTO(
            prescriptionItemService.getPrescriptionItemById(id));
    return new ResponseEntity<>(prescriptionItemDTO, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> createPrescriptionItem(
      @Valid @RequestBody PrescriptionItemDTO prescriptionItemDTO) {
    PrescriptionItem createdPrescriptionItem =
        prescriptionItemService.createPrescriptionItem(
            prescriptionItemMapper.prescriptionItemDTOToPrescriptionItem(prescriptionItemDTO));
    PrescriptionItemDTO createdPrescriptionItemDTO =
        prescriptionItemMapper.prescriptionItemToPrescriptionItemDTO(createdPrescriptionItem);
    return new ResponseEntity<>(createdPrescriptionItemDTO, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updatePrescriptionItem(
      @PathVariable(name = "id") Long id,
      @Valid @RequestBody PrescriptionItemDTO prescriptionItemDTO) {
    PrescriptionItem updatedPrescriptionItem =
        prescriptionItemService.updatePrescriptionItem(
            id, prescriptionItemMapper.prescriptionItemDTOToPrescriptionItem(prescriptionItemDTO));
    PrescriptionItemDTO updatedPrescriptionItemDTO =
        prescriptionItemMapper.prescriptionItemToPrescriptionItemDTO(updatedPrescriptionItem);
    return new ResponseEntity<>(updatedPrescriptionItemDTO, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deletePrescriptionItem(@PathVariable(name = "id") Long id) {
    prescriptionItemService.deletePrescriptionItem(id);
    return ResponseEntity.noContent().build();
  }
}
