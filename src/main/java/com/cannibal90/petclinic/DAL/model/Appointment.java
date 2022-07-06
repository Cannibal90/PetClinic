package com.cannibal90.petclinic.DAL.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String note;

    @OneToOne
    private Prescription prescription;

    @ManyToOne
    private Room room;

    @ManyToOne
    private Pet pet;

    @ManyToOne
    private Doctor doctor;
}
