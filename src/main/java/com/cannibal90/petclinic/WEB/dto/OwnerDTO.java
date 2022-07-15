package com.cannibal90.petclinic.WEB.dto;

import lombok.Data;

import java.util.List;

@Data
public class OwnerDTO extends PersonDTO {

    private List<PetDTO> pets;
}
