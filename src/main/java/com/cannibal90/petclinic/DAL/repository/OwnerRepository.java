package com.cannibal90.petclinic.DAL.repository;

import com.cannibal90.petclinic.DAL.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner,Long> {
}
