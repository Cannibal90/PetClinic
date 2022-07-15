package com.cannibal90.petclinic.WEB.exception;

public interface ExceptionConst {
    String WRONG_ID_PARAMETER = "Wrong id parameter";

    String MEDICAMENT_NOT_FOUND = "Medicament was not found";
    String WRONG_MEDICAMENT_OBJECT = "Wrong medicament object was passed";

    String ROOM_NOT_FOUND = "Room was not found";
    String WRONG_ROOM_OBJECT = "Wrong room object was passed";

    String SPECIES_NOT_FOUND = "Species was not found";
    String WRONG_SPECIES_OBJECT = "Wrong species object was passed";
    String WRONG_SPECIES_NAME = "Wrong species name was passed";

    String PRESCRIPTION_ITEM_NOT_FOUND = "Prescription item was not found";
    String WRONG_PRESCRIPTION_ITEM_OBJECT = "Wrong Prescription item object was passed";

    String PRESCRIPTION_NOT_FOUND = "Prescription was not found";
    String WRONG_PRESCRIPTION_OBJECT = "Wrong Prescription object was passed";

    String OWNER_NOT_FOUND = "Owner was not found";
    String WRONG_OWNER_OBJECT = "Wrong Owner object was passed";

    String DOCTOR_NOT_FOUND = "Doctor was not found";
    String WRONG_DOCTOR_OBJECT = "Wrong Doctor object was passed";

    String APPOINTMENT_NOT_FOUND = "Appointment was not found";
    String WRONG_APPOINTMENT_OBJECT = "Wrong Appointment object was passed";
    String PET_NOT_FOUND = "Pet was not found";
    String WRONG_PET_OBJECT = "Wrong Pet object was passed";
    String NO_PET_OWNER = "Pet must contain at least one owner";
}
