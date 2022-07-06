package com.cannibal90.petclinic;

import com.cannibal90.petclinic.DAL.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Runner {

  private final MedicamentRepository medicamentRepository;
  private final PrescriptionItemRepository prescriptionItemRepository;
  private final PrescriptionRepository prescriptionRepository;
  private final OwnerRepository ownerRepository;
  private final PetRepository petRepository;
  private final SpeciesRepository speciesRepository;
  private final AppointmentRepository appointmentRepository;
  private final RoomRepository roomRepository;
  private final DoctorRepository doctorRepository;

//  @EventListener(ApplicationReadyEvent.class)
//  private void run() {
//    //        Dzialajace zapisywanie ale meczace podejscie bo wszystko trzeba robic recznie
//    //        PrescriptionItem prescriptionItem1 = new PrescriptionItem(10L);
//    //        PrescriptionItem prescriptionItem2 = new PrescriptionItem(20L);
//    //        prescriptionItemRepository.save(prescriptionItem1);
//    //        prescriptionItemRepository.save(prescriptionItem2);
//    //
//    //        Prescription prescription1 = new Prescription("Comeback after 2 weeks");
//    //        prescription1.setPrescriptionItems(Set.of(prescriptionItem1, prescriptionItem2));
//    //        prescriptionRepository.save(prescription1);
//    //
//    //        Medicament medicament1 = new Medicament("test1", 11.2);
//    //        Medicament medicament2 = new Medicament("test2", 12.2);
//    //        medicament1.setPrescriptionItem(List.of(prescriptionItem1));
//    //        medicament2.setPrescriptionItem(List.of(prescriptionItem2));
//    //        medicamentRepository.save(medicament1);
//    //        medicamentRepository.save(medicament2);
//
//
//    PrescriptionItem prescriptionItem1 = new PrescriptionItem(10L);
//    PrescriptionItem prescriptionItem2 = new PrescriptionItem(20L);
//    Medicament medicament1 = new Medicament("test1", 11.2);
//    Medicament medicament2 = new Medicament("test2", 12.2);
//    Medicament medicament3 = new Medicament("test23", 13.2);
//    medicamentRepository.save(medicament1);
//    medicamentRepository.save(medicament2);
//    medicamentRepository.save(medicament3);
//
//    Medicament f1 = medicamentRepository.findById(1L).get();
//    Medicament f2 = medicamentRepository.findById(2L).get();
//    prescriptionItem1.setMedicament(f1);
//    prescriptionItem2.setMedicament(f2);
//    Prescription prescription1 = new Prescription("Comeback after 2 weeks");
//    Set<PrescriptionItem> items = new HashSet<>();
//    items.addAll(Set.of(prescriptionItem1, prescriptionItem2));
//    prescription1.setPrescriptionItems(items);
//    prescriptionRepository.save(prescription1);
//
////    PrescriptionItem test = new PrescriptionItem(2L);
////    test.setMedicament(medicament3);
////    items = prescription1.getPrescriptionItems();
////    items.add(test);
////    prescription1.setPrescriptionItems(items);
////    prescriptionRepository.save(prescription1);
//
//
//    Owner owner1 = new Owner("Jan");
//    ownerRepository.save(owner1);
//
//    Specie specie = new Specie("Husky");
//    specieRepository.save(specie);
//
//    Pet pet1 = new Pet("Filemon");
//    pet1.setSpecie(specie);
//    Pet pet2 = new Pet("Gacek");
//    pet1.setOwners(Set.of(owner1));
//    pet2.setOwners(Set.of(owner1));
//    petRepository.saveAll(List.of(pet1,pet2));
//
//    Room room = new Room("421");
//    roomRepository.save(room);
//
//    Doctor doctor = new Doctor("Kowalski");
//    doctorRepository.save(doctor);
//
//    Appointment appointment1 = new Appointment("testowa wizyta");
//    Prescription prescriptionTest = prescriptionRepository.findById(1L).get();
//    appointment1.setPrescription(prescriptionTest);
//    Room room1 = roomRepository.findById(1L).get();
//    appointment1.setRoom(room1);
//    appointment1.setPet(pet1);
//    appointment1.setDoctor(doctor);
//    appointmentRepository.save(appointment1);
//  }
}
