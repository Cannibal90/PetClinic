package com.cannibal90.petclinic.WEB.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class PrescriptionItemDTO {

    private Long id;

    @NotNull(message = "Quantity field is required")
    @Positive(message = "Quantity must be positive value")
    private Long quantity;

    @NotNull(message = "Medicament field is required")
    private MedicamentDTO medicament;
}
