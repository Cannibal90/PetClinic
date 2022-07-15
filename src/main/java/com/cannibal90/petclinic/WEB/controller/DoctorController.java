package com.cannibal90.petclinic.WEB.controller;

import com.cannibal90.petclinic.DAL.model.Doctor;
import com.cannibal90.petclinic.WEB.dto.DoctorDTO;
import com.cannibal90.petclinic.WEB.dto.PersonDTO;
import com.cannibal90.petclinic.WEB.mapper.DoctorMapper;
import com.cannibal90.petclinic.WEB.service.DoctorService;
import com.cannibal90.petclinic.WEB.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/person/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable(name = "id") Long id){
        DoctorDTO doctorDTO = doctorMapper.doctorToDoctorDTO(doctorService.getDoctorById(id));
        return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> registerDoctor(@Valid @RequestBody PersonDTO personDTO) {
        Doctor createdDoctor = doctorService.createDoctor(doctorMapper.PersonDTOToDoctor(personDTO));
        PersonDTO createdDoctorDTO = doctorMapper.doctorToPersonDTO(createdDoctor);
        return new ResponseEntity<>(createdDoctorDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> registerDoctor(@PathVariable(name = "id") Long id, @Valid @RequestBody PersonDTO personDTO) {
        Doctor updatedDoctor = doctorService.updateDoctor(id, doctorMapper.PersonDTOToDoctor(personDTO));
        DoctorDTO updatedDoctorDTO = doctorMapper.doctorToDoctorDTO(updatedDoctor);
        return new ResponseEntity<>(updatedDoctorDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable(name = "id") Long id){
        doctorService.deleteDoctor(id);

        return ResponseEntity.noContent().build();
    }
}
