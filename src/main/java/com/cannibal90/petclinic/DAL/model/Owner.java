package com.cannibal90.petclinic.DAL.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Owner extends Person {

    @ManyToMany(mappedBy = "owners")
    private List<Pet> pets;
}
