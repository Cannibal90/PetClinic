package com.cannibal90.petclinic.WEB.dto;


import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MedicamentDTO {

    private Long id;

    @NotNull(message = "Name field is required")
    private String name;

    @NotNull(message = "Price field is required")
    private Double price;
}
