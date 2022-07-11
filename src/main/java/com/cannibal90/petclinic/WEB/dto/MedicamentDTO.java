package com.cannibal90.petclinic.WEB.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class MedicamentDTO {

    private Long id;

    @NotNull(message = "Name field is required")
    @Size(min = 3, message = "Name must have min 3 characters")
    private String name;

    @NotNull(message = "Price field is required")
    @Positive(message = "Price must be positive value")
    private Double price;
}
