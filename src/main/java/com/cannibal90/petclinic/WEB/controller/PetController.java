package com.cannibal90.petclinic.WEB.controller;

import com.cannibal90.petclinic.DAL.model.Pet;
import com.cannibal90.petclinic.WEB.dto.PetDTO;
import com.cannibal90.petclinic.WEB.dto.PetWithPersonDTO;
import com.cannibal90.petclinic.WEB.mapper.PetMapper;
import com.cannibal90.petclinic.WEB.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    private final PetMapper petMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPetById(@PathVariable(name = "id") Long id){
        PetWithPersonDTO petDTO = petMapper.petToPetWithPersonDTO(petService.getPetById(id));
        return new ResponseEntity<>(petDTO, HttpStatus.OK);
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<?> getPetsByOwnerId(@PathVariable(name = "id") Long id){
        List<PetDTO> petDTOList = petMapper.petListToPetDTOList(petService.getAllPetsByOwnerId(id));
        return new ResponseEntity<>(petDTOList, HttpStatus.OK);
    }

    @GetMapping("/species")
    public ResponseEntity<?> getPetsBySpeciesName(@RequestParam(name = "speciesName", required = true) String name){
        List<PetDTO> petDTOList = petMapper.petListToPetDTOList(petService.getAllPetsBySpeciesName(name));
        return new ResponseEntity<>(petDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> registerNewPet(@Valid @RequestBody PetWithPersonDTO petDTO) { //TODO create new dto with Owners
        Pet registeredPet = petService.registerPet(petMapper.petWithPersonDTOToPet(petDTO));
        PetWithPersonDTO registeredPetDTO = petMapper.petToPetWithPersonDTO(registeredPet);
        return new ResponseEntity<>(registeredPetDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePetData(@Valid @RequestBody PetWithPersonDTO petDTO, @PathVariable(name = "id") Long id){ //TODO create new dto with Owners
        Pet updatedPet = petService.updatePetData(id, petMapper.petWithPersonDTOToPet(petDTO));
        PetWithPersonDTO updatedPetDTO = petMapper.petToPetWithPersonDTO(updatedPet);
        return new ResponseEntity<>(updatedPetDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePetDataById(@PathVariable(name = "id") Long id){
        petService.deletePetData(id);

        return ResponseEntity.noContent().build();
    }
}
