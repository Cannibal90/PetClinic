package com.cannibal90.petclinic.WEB.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SpeciesDTO {

    private Long id;

    @NotNull(message = "Species name field is required")
    @Size(min = 3, message = "Species name must have min 3 characters")
    private String speciesName;
}
