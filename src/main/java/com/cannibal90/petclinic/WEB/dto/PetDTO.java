package com.cannibal90.petclinic.WEB.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class PetDTO {

    private Long id;

    @NotNull(message = "Name is required")
    @Size(min = 2, message = "Name must have min 2 characters")
    private String name;

    @NotNull(message = "Birth date field is required")
    @Past(message = "Birth date can not be in the future")
    private LocalDate birthDate;

    @NotNull(message = "Species field is required")
    private SpeciesDTO species;
}
