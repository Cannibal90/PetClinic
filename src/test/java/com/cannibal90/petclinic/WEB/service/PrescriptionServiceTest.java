package com.cannibal90.petclinic.WEB.service;

import com.cannibal90.petclinic.DAL.model.Prescription;
import com.cannibal90.petclinic.DAL.model.PrescriptionItem;
import com.cannibal90.petclinic.DAL.repository.PrescriptionRepository;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrescriptionServiceTest {

  @Mock PrescriptionRepository prescriptionRepository;

  @Mock PrescriptionItemService prescriptionItemService;

  @InjectMocks PrescriptionService prescriptionService;

  @Test
  void shouldReturnPrescriptionByIdCorrect() {
    // given
    given(prescriptionRepository.findById(anyLong())).willReturn(Optional.of(new Prescription()));
    // when
    Prescription result = prescriptionService.getPrescriptionById(1L);
    // then
    Assertions.assertThat(result).isNotNull();
  }

  @Test
  void shouldThrowExceptionWhenGetPrescriptionIdIsNull() {
    Assertions.assertThatThrownBy(() -> prescriptionService.getPrescriptionById(null))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenGetPrescriptionIdIsWrong() {
    Assertions.assertThatThrownBy(() -> prescriptionService.getPrescriptionById(-1L))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenGetPrescriptionIsNotFound() {
    // given
    given(prescriptionRepository.findById(anyLong())).willReturn(Optional.empty());
    // then
    Assertions.assertThatThrownBy(() -> prescriptionService.getPrescriptionById(1L))
        .isInstanceOf(NoDataFoundException.class)
        .hasMessage(ExceptionConst.PRESCRIPTION_NOT_FOUND);
  }

  @Test
  void shouldReturnCreatePrescriptionCorrect() {
    // given
    Prescription prescription = new Prescription();
    prescription.setId(1L);
    given(prescriptionRepository.save(any(Prescription.class))).willReturn(prescription);

    // when
    Prescription result = prescriptionService.createPrescription(prescription);

    // then
    verify(prescriptionRepository, times(1)).save(any(Prescription.class));
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getId()).isEqualTo(1L);
  }

  @Test
  void shouldThrowExceptionWhenCreatePrescriptionIsNull() {
    Assertions.assertThatThrownBy(() -> prescriptionService.createPrescription(null))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_PRESCRIPTION_OBJECT);
  }

  @Test
  void shouldReturnUpdatePrescriptionCorrect() {
    // given
    PrescriptionItem prescriptionItem1 = new PrescriptionItem();
    PrescriptionItem prescriptionItem2 = new PrescriptionItem();
    prescriptionItem1.setId(1L);
    prescriptionItem2.setId(2L);
    Prescription prescription = new Prescription();
    prescription.setId(1L);
    prescription.setPrescriptionItems(Set.of(prescriptionItem1, prescriptionItem2));
    given(prescriptionRepository.findById(anyLong())).willReturn(Optional.of(prescription));
    given(prescriptionRepository.save(any(Prescription.class))).willReturn(prescription);

    // when
    Prescription result = prescriptionService.updatePrescription(1L, prescription);

    // then
    verify(prescriptionRepository, times(1)).findById(anyLong());
    verify(prescriptionRepository, times(1)).save(any(Prescription.class));
    verify(prescriptionItemService, times(2)).deletePrescriptionItem(anyLong());
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getId()).isEqualTo(1L);
    Assertions.assertThat(result.getPrescriptionItems()).hasSize(2);
  }

  @Test
  void shouldThrowExceptionWhenUpdatePrescriptionIsNull() {
    Assertions.assertThatThrownBy(() -> prescriptionService.updatePrescription(1L, null))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_PRESCRIPTION_OBJECT);
  }

  @Test
  void shouldThrowExceptionWhenUpdatePrescriptionIdIsNull() {
    Assertions.assertThatThrownBy(
            () -> prescriptionService.updatePrescription(null, new Prescription()))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenUpdatePrescriptionIdIsWrong() {
    Assertions.assertThatThrownBy(
            () -> prescriptionService.updatePrescription(-1L, new Prescription()))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenUpdatePrescriptionIdIsNotFound() {
    // given
    given(prescriptionRepository.findById(anyLong())).willReturn(Optional.empty());

    // then
    Assertions.assertThatThrownBy(
            () -> prescriptionService.updatePrescription(1L, new Prescription()))
        .isInstanceOf(NoDataFoundException.class)
        .hasMessage(ExceptionConst.PRESCRIPTION_NOT_FOUND);
  }

  @Test
  void shouldReturnDeletePrescriptionCorrect() {
    // given
    Prescription prescription = new Prescription();
    prescription.setId(1L);
    given(prescriptionRepository.findById(anyLong())).willReturn(Optional.of(prescription));

    doAnswer(
            invocation -> {
              Prescription result = invocation.getArgument(0);
              Assertions.assertThat(result).isNotNull();
              Assertions.assertThat(result.getId()).isEqualTo(1L);

              return null;
            })
        .when(prescriptionRepository)
        .delete(any(Prescription.class));

    // when
    prescriptionService.deletePrescription(1L);

    // then
    verify(prescriptionRepository, times(1)).findById(anyLong());
    verify(prescriptionRepository, times(1)).delete(any(Prescription.class));
  }

  @Test
  void shouldThrowExceptionWhenDeletePrescriptionIdIsNull() {
    Assertions.assertThatThrownBy(() -> prescriptionService.deletePrescription(null))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenDeletePrescriptionIdIsWrong() {
    Assertions.assertThatThrownBy(() -> prescriptionService.deletePrescription(-1L))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenDeletePrescriptionIsNotFound() {
    // given
    given(prescriptionRepository.findById(anyLong())).willReturn(Optional.empty());

    // then
    Assertions.assertThatThrownBy(() -> prescriptionService.deletePrescription(1L))
        .isInstanceOf(NoDataFoundException.class)
        .hasMessage(ExceptionConst.PRESCRIPTION_NOT_FOUND);
  }
}
