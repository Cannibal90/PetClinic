package com.cannibal90.petclinic.WEB.controller;

import com.cannibal90.petclinic.DAL.model.Medicament;
import com.cannibal90.petclinic.WEB.dto.MedicamentDTO;
import com.cannibal90.petclinic.WEB.dto.PageDTO;
import com.cannibal90.petclinic.WEB.mapper.MedicamentMapper;
import com.cannibal90.petclinic.WEB.service.MedicamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/medicament")
@RequiredArgsConstructor
public class MedicamentController {

  private final MedicamentService medicamentService;
  private final MedicamentMapper medicamentMapper;

  @GetMapping("/{id}")
  public ResponseEntity<?> getMedicamentById(@PathVariable(name = "id") Long id) {
    MedicamentDTO medicamentDTO =
        medicamentMapper.medicamentToMedicamentDTO(medicamentService.getMedicamentById(id));
    return new ResponseEntity<>(medicamentDTO, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<?> getAllMedicaments(Pageable page) {
    PageDTO pageDTO =
        medicamentMapper.medicamentPageToPageDTO(medicamentService.getAllMedicaments(page));
    return new ResponseEntity<>(pageDTO, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> createMedicament(@Valid @RequestBody MedicamentDTO medicamentDTO) {
    Medicament createdMedicament =
        medicamentService.createMedicament(
            medicamentMapper.medicamentDTOToMedicament(medicamentDTO));
    MedicamentDTO createdMedicamentDTO =
        medicamentMapper.medicamentToMedicamentDTO(createdMedicament);
    return new ResponseEntity<>(createdMedicamentDTO, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateMedicament(
      @Valid @PathVariable(name = "id") Long id, @RequestBody MedicamentDTO medicamentDTO) {
    Medicament updatedMedicament =
        medicamentService.updateMedicament(
            id, medicamentMapper.medicamentDTOToMedicament(medicamentDTO));
    MedicamentDTO updatedMedicamentDTO =
        medicamentMapper.medicamentToMedicamentDTO(updatedMedicament);
    return new ResponseEntity<>(updatedMedicamentDTO, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteMedicament(@PathVariable(name = "id") Long id) {
    medicamentService.deleteMedicament(id);
    return ResponseEntity.noContent().build();
  }
}
