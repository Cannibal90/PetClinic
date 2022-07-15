package com.cannibal90.petclinic.WEB.service;

import com.cannibal90.petclinic.DAL.model.Doctor;
import com.cannibal90.petclinic.DAL.repository.DoctorRepository;
import com.cannibal90.petclinic.WEB.exception.ExceptionConst;
import com.cannibal90.petclinic.WEB.exception.NoDataFoundException;
import com.cannibal90.petclinic.WEB.exception.WrongParameterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorService {

    public final DoctorRepository doctorRepository;

    public Doctor getDoctorById(Long id) {
        if (id == null || id <= 0) {
            throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
        }

        return doctorRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            throw new NoDataFoundException(ExceptionConst.DOCTOR_NOT_FOUND);
                        });
    }

    public Doctor createDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new WrongParameterException(ExceptionConst.WRONG_DOCTOR_OBJECT);
        }

        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Long id, Doctor doctor) {
        if (doctor == null) {
            throw new WrongParameterException(ExceptionConst.WRONG_DOCTOR_OBJECT);
        } else if (id == null || id <= 0) {
            throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
        }

        return doctorRepository
                .findById(id)
                .map(
                        foundDoctor -> {
                            foundDoctor.setFirstName(doctor.getFirstName());
                            foundDoctor.setLastName(doctor.getLastName());
                            foundDoctor.setPhone(doctor.getPhone());
                            foundDoctor.setEmail(doctor.getEmail());

                            return doctorRepository.save(foundDoctor);
                        })
                .orElseThrow(
                        () -> {
                            throw new NoDataFoundException(ExceptionConst.DOCTOR_NOT_FOUND);
                        });
    }

    public void deleteDoctor(Long id) {
        if (id == null || id <= 0) {
            throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
        }
        Doctor doctor =
                doctorRepository
                        .findById(id)
                        .orElseThrow(
                                () -> {
                                    throw new NoDataFoundException(ExceptionConst.DOCTOR_NOT_FOUND);
                                });

        doctorRepository.delete(doctor);
    }
}
