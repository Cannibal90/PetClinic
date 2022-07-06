package com.cannibal90.petclinic.DAL.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Species {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String spieceName;

    @OneToMany(mappedBy = "species")
    private List<Pet> pets;
}
