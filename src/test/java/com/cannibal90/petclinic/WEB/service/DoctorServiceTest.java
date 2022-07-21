package com.cannibal90.petclinic.WEB.service;

import com.cannibal90.petclinic.DAL.model.Doctor;
import com.cannibal90.petclinic.DAL.model.Doctor;
import com.cannibal90.petclinic.DAL.model.Doctor;
import com.cannibal90.petclinic.DAL.model.Doctor;
import com.cannibal90.petclinic.DAL.repository.DoctorRepository;
import com.cannibal90.petclinic.WEB.exception.ExceptionConst;
import com.cannibal90.petclinic.WEB.exception.NoDataFoundException;
import com.cannibal90.petclinic.WEB.exception.WrongParameterException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    DoctorRepository doctorRepository;

    @InjectMocks
    DoctorService doctorService;

    @Test
    void shouldReturnDoctorByIdCorrect() {
        // given
        given(doctorRepository.findById(anyLong()))
                .willReturn(Optional.of(new Doctor()));

        // when
        Doctor result = doctorService.getDoctorById(1L);

        // then
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenGetDoctorIdIsNull() {
        Assertions.assertThatThrownBy(() -> doctorService.getDoctorById(null))
                .isInstanceOf(WrongParameterException.class)
                .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenGetDoctorIdIsWrong() {
        Assertions.assertThatThrownBy(() -> doctorService.getDoctorById(-1L))
                .isInstanceOf(WrongParameterException.class)
                .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenGetDoctorIdNotFound() {
        // given
        given(doctorRepository.findById(anyLong())).willReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> doctorService.getDoctorById(1L))
                .isInstanceOf(NoDataFoundException.class)
                .hasMessage(ExceptionConst.DOCTOR_NOT_FOUND);
    }

    @Test
    void shouldReturnCreateDoctorCorrect(){
        //given
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        given(doctorRepository.save(any(Doctor.class))).willReturn(doctor);

        //when
        Doctor result = doctorService.createDoctor(doctor);

        //then
        verify(doctorRepository,times(1)).save(any(Doctor.class));
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowExceptionWhenCreateDoctorIsNull(){
        Assertions.assertThatThrownBy(() -> doctorService.createDoctor(null)).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_DOCTOR_OBJECT);
    }

    @Test
    void shouldReturnUpdateDoctorCorrect() {
        //given
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setFirstName("Kowalski");
        given(doctorRepository.findById(anyLong())).willReturn(Optional.of(doctor));
        given(doctorRepository.save(any(Doctor.class))).willReturn(doctor);

        //when
        Doctor result = doctorService.updateDoctor(1L, doctor);

        //then
        verify(doctorRepository, times(1)).findById(anyLong());
        verify(doctorRepository, times(1)).save(any(Doctor.class));
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(1L);
        Assertions.assertThat(result.getFirstName()).isEqualTo("Kowalski");
    }

    @Test
    void shouldThrowExceptionWhenUpdateDoctorIsNull(){
        Assertions.assertThatThrownBy(() -> doctorService.updateDoctor(1L, null)).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_DOCTOR_OBJECT);
    }

    @Test
    void shouldThrowExceptionWhenUpdateDoctorIdIsNull(){
        Assertions.assertThatThrownBy(() -> doctorService.updateDoctor(null, new Doctor())).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenUpdateDoctorIdIsWrong(){
        Assertions.assertThatThrownBy(() -> doctorService.updateDoctor(-1L, new Doctor())).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenUpdateDoctorIsNotFound(){
        //given
        given(doctorRepository.findById(anyLong())).willReturn(Optional.empty());
        //then
        Assertions.assertThatThrownBy(() -> doctorService.updateDoctor(1L, new Doctor())).isInstanceOf(NoDataFoundException.class).hasMessage(ExceptionConst.DOCTOR_NOT_FOUND);
    }

    @Test
    void shouldReturnDeleteDoctorCorrect() {
        // given
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        given(doctorRepository.findById(anyLong())).willReturn(Optional.of(doctor));

        doAnswer(
                invocation -> {
                    Doctor result = invocation.getArgument(0);
                    Assertions.assertThat(result).isNotNull();
                    Assertions.assertThat(result.getId()).isEqualTo(1L);
                    return null;
                })
                .when(doctorRepository)
                .delete(any(Doctor.class));

        // when
        doctorService.deleteDoctor(1L);

        // then
        verify(doctorRepository, times(1)).findById(anyLong());
        verify(doctorRepository, times(1)).delete(any(Doctor.class));
    }

    @Test
    void shouldThrowExceptionWhenDeleteDoctorIdIsNull() {
        Assertions.assertThatThrownBy(() -> doctorService.deleteDoctor(null))
                .isInstanceOf(WrongParameterException.class)
                .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenDeleteDoctorIdIsWrong() {
        Assertions.assertThatThrownBy(() -> doctorService.deleteDoctor(-1L))
                .isInstanceOf(WrongParameterException.class)
                .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenDeleteDoctorIsNotFound() {
        // given
        given(doctorRepository.findById(anyLong())).willReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> doctorService.deleteDoctor(1L))
                .isInstanceOf(NoDataFoundException.class)
                .hasMessage(ExceptionConst.DOCTOR_NOT_FOUND);
    }

}