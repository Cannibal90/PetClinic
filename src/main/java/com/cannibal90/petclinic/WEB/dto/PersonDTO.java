package com.cannibal90.petclinic.WEB.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PersonDTO {

    private Long id;

    @NotNull(message = "First name field is required")
    @Size(min = 2, message = "First name must have min 2 characters")
    private String firstName;

    @NotNull(message = "Last name field is required")
    @Size(min = 3, message = "Last name must have min 3 characters")
    private String lastName;

    @NotNull(message = "Phone field is required")
    @Size(min = 9, max = 13, message = "Phone must have min 9 and max 13 characters")
    private String phone;

    @NotNull(message = "Email field is required")
    @Size(min = 3, message = "Email must have min 3 characters")
    private String email;

}
