package com.cannibal90.petclinic.DAL.repository;

import com.cannibal90.petclinic.DAL.model.PrescriptionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, Long> {

}
