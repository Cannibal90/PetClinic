package com.cannibal90.petclinic.DAL.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Doctor extends Person {

    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(appointment, doctor.appointment);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}
