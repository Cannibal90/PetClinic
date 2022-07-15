package com.cannibal90.petclinic.DAL.repository;

import com.cannibal90.petclinic.DAL.model.Owner;
import com.cannibal90.petclinic.DAL.model.Pet;
import com.cannibal90.petclinic.DAL.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findAllByOwnersIsContaining(Owner owner);

    List<Pet> findAllBySpecies(Species species);
}
