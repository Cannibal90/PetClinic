package com.cannibal90.petclinic.DAL.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long floor;
    private String roomDescription;

    @OneToMany(mappedBy = "room")
    private List<Appointment> appointments;
}
