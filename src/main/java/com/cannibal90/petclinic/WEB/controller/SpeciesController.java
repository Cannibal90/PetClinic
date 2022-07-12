package com.cannibal90.petclinic.WEB.controller;

import com.cannibal90.petclinic.DAL.model.Medicament;
import com.cannibal90.petclinic.DAL.model.Species;
import com.cannibal90.petclinic.WEB.dto.MedicamentDTO;
import com.cannibal90.petclinic.WEB.dto.PageDTO;
import com.cannibal90.petclinic.WEB.dto.SpeciesDTO;
import com.cannibal90.petclinic.WEB.mapper.SpeciesMapper;
import com.cannibal90.petclinic.WEB.service.SpeciesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/species")
@RequiredArgsConstructor
public class SpeciesController {

  private final SpeciesService speciesService;
  private final SpeciesMapper speciesMapper;

  @GetMapping("/{id}")
  public ResponseEntity<?> getSpeciesById(@PathVariable(name = "id") Long id) {
    SpeciesDTO speciesDTO = speciesMapper.speciesToSpeciesDTO(speciesService.getSpeciesById(id));
    return new ResponseEntity<>(speciesDTO, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<?> getAllSpecies() {
    List<SpeciesDTO> speciesListDTO =
        speciesMapper.speciesListToSpeciesListDTO(speciesService.getAllSpecies());
    return new ResponseEntity<>(speciesListDTO, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> createSpecies(@Valid @RequestBody SpeciesDTO speciesDTO) {
    Species createdSpecies =
        speciesService.createSpecies(speciesMapper.speciesDTOToSpecies(speciesDTO));
    SpeciesDTO createdSpeciesDTO = speciesMapper.speciesToSpeciesDTO(createdSpecies);
    return new ResponseEntity<>(createdSpeciesDTO, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateSpecies(
       @PathVariable(name = "id") Long id, @Valid @RequestBody SpeciesDTO speciesDTO) {
    Species updatedSpecies =
        speciesService.updateSpecies(id, speciesMapper.speciesDTOToSpecies(speciesDTO));
    SpeciesDTO updatedSpeciesDTO = speciesMapper.speciesToSpeciesDTO(updatedSpecies);
    return new ResponseEntity<>(updatedSpeciesDTO, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSpecies(@PathVariable(name = "id") Long id){
      speciesService.deleteSpecies(id);
      return ResponseEntity.noContent().build();
  }
}
