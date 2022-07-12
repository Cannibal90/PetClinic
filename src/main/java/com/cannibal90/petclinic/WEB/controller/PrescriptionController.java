package com.cannibal90.petclinic.WEB.controller;

import com.cannibal90.petclinic.DAL.model.Prescription;
import com.cannibal90.petclinic.WEB.dto.PrescriptionDTO;
import com.cannibal90.petclinic.WEB.mapper.PrescriptionMapper;
import com.cannibal90.petclinic.WEB.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/prescription")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final PrescriptionMapper prescriptionMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPrescriptionById(@PathVariable(name = "id") Long id) {
        PrescriptionDTO prescriptionDTO =
                prescriptionMapper.prescriptionToPrescriptionDTO(
                        prescriptionService.getPrescriptionById(id));
        return new ResponseEntity<>(prescriptionDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createPrescription(
            @Valid @RequestBody PrescriptionDTO prescriptionDTO) {
        Prescription createdPrescription =
                prescriptionService.createPrescription(
                        prescriptionMapper.prescriptionDTOToPrescription(prescriptionDTO));
        PrescriptionDTO createdPrescriptionDTO =
                prescriptionMapper.prescriptionToPrescriptionDTO(createdPrescription);
        return new ResponseEntity<>(createdPrescriptionDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrescription(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody PrescriptionDTO prescriptionDTO) {
        Prescription updatedPrescription =
                prescriptionService.updatePrescription(
                        id, prescriptionMapper.prescriptionDTOToPrescription(prescriptionDTO));
        PrescriptionDTO updatedPrescriptionDTO =
                prescriptionMapper.prescriptionToPrescriptionDTO(updatedPrescription);
        return new ResponseEntity<>(updatedPrescriptionDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrescription(@PathVariable(name = "id") Long id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntity.noContent().build();
    }
}
