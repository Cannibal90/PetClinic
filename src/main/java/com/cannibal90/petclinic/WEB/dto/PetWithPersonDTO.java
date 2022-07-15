package com.cannibal90.petclinic.WEB.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class PetWithPersonDTO extends PetDTO{

    @NotNull(message = "Owners field is required")
    private Set<PersonDTO> owners;
}
