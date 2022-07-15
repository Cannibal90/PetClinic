package com.cannibal90.petclinic.WEB.controller;

import com.cannibal90.petclinic.DAL.model.Owner;
import com.cannibal90.petclinic.WEB.dto.OwnerDTO;
import com.cannibal90.petclinic.WEB.dto.PersonDTO;
import com.cannibal90.petclinic.WEB.mapper.OwnerMapper;
import com.cannibal90.petclinic.WEB.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/person/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;
    private final OwnerMapper ownerMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOwnerById(@PathVariable(name = "id") Long id){
        OwnerDTO ownerDTO = ownerMapper.ownerToOwnerDTO(ownerService.getOwnerById(id));
        return new ResponseEntity<>(ownerDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> registerOwner(@Valid @RequestBody PersonDTO personDTO) {
        Owner createdOwner = ownerService.createOwner(ownerMapper.PersonDTOToOwner(personDTO));
        PersonDTO createdOwnerDTO = ownerMapper.ownerToPersonDTO(createdOwner);
        return new ResponseEntity<>(createdOwnerDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> registerOwner(@PathVariable(name = "id") Long id, @Valid @RequestBody PersonDTO personDTO) {
        Owner updatedOwner = ownerService.updateOwner(id, ownerMapper.PersonDTOToOwner(personDTO));
        OwnerDTO updatedOwnerDTO = ownerMapper.ownerToOwnerDTO(updatedOwner);
        return new ResponseEntity<>(updatedOwnerDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable(name = "id") Long id){
        ownerService.deleteOwner(id);

        return ResponseEntity.noContent().build();
    }
}
