package com.cannibal90.petclinic.DAL.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Doctor extends Person {

    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointment;
}
