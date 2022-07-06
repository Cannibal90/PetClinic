package com.cannibal90.petclinic.DAL.repository;

import com.cannibal90.petclinic.DAL.model.Owner;
import com.cannibal90.petclinic.DAL.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Owner> {
}
