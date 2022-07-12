package com.cannibal90.petclinic.WEB.dto;

import com.cannibal90.petclinic.DAL.model.PrescriptionItem;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class PrescriptionDTO {

    private Long id;

    @NotNull(message = "Note field is required")
    private String note;

    private Set<PrescriptionItemDTO> prescriptionItems;
}
