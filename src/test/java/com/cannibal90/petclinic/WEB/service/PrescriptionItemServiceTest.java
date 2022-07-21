package com.cannibal90.petclinic.WEB.service;

import com.cannibal90.petclinic.DAL.model.Medicament;
import com.cannibal90.petclinic.DAL.model.PrescriptionItem;
import com.cannibal90.petclinic.DAL.repository.MedicamentRepository;
import com.cannibal90.petclinic.DAL.repository.PrescriptionItemRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrescriptionItemServiceTest {

  @Mock PrescriptionItemRepository prescriptionItemRepository;

  @Mock MedicamentService medicamentService;

  @InjectMocks PrescriptionItemService prescriptionItemService;

  @Test
  void shouldReturnPrescriptionItemByIdCorrect() {
    // given
    given(prescriptionItemRepository.findById(anyLong()))
        .willReturn(Optional.of(new PrescriptionItem()));

    // when
    PrescriptionItem result = prescriptionItemService.getPrescriptionItemById(1L);

    // then
    Assertions.assertThat(result).isNotNull();
  }

  @Test
  void shouldThrowExceptionWhenGetPrescriptionItemIdIsNull() {
    Assertions.assertThatThrownBy(() -> prescriptionItemService.getPrescriptionItemById(null))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenGetPrescriptionItemIdIsWrong() {
    Assertions.assertThatThrownBy(() -> prescriptionItemService.getPrescriptionItemById(-1L))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenGetPrescriptionItemIdNotFound() {
    // given
    given(prescriptionItemRepository.findById(anyLong())).willReturn(Optional.empty());

    // then
    Assertions.assertThatThrownBy(() -> prescriptionItemService.getPrescriptionItemById(1L))
        .isInstanceOf(NoDataFoundException.class)
        .hasMessage(ExceptionConst.PRESCRIPTION_ITEM_NOT_FOUND);
  }

  @Test
  void shouldCreatePrescriptionItemCorrect() {
    // given
    Medicament medicament = new Medicament();
    medicament.setId(1L);
    PrescriptionItem prescriptionItem = new PrescriptionItem();
    prescriptionItem.setMedicament(medicament);
    given(medicamentService.getMedicamentById(anyLong())).willReturn(medicament);
    given(prescriptionItemRepository.save(any(PrescriptionItem.class)))
        .willReturn(prescriptionItem);

    // when
    PrescriptionItem result = prescriptionItemService.createPrescriptionItem(prescriptionItem);

    // then
    verify(medicamentService, times(1)).getMedicamentById(anyLong());
    verify(prescriptionItemRepository, times(1)).save(any(PrescriptionItem.class));
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getMedicament()).isNotNull();
    Assertions.assertThat(result.getMedicament().getId()).isEqualTo(1L);
  }

  @Test
  void shouldThrowExceptionWhenCreatePrescriptionItemIsNull() {
    Assertions.assertThatThrownBy(() -> prescriptionItemService.createPrescriptionItem(null))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_PRESCRIPTION_ITEM_OBJECT);
  }

  @Test
  void shouldThrowExceptionWhenCreatePrescriptionItemMedicamentIdIsNull() {
    // given
    PrescriptionItem prescriptionItem = new PrescriptionItem();
    prescriptionItem.setMedicament(new Medicament());
    given(medicamentService.getMedicamentById(any()))
        .willThrow(new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER));

    // then
    Assertions.assertThatThrownBy(
            () -> prescriptionItemService.createPrescriptionItem(prescriptionItem))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenCreatePrescriptionItemMedicamentIdIsWrong() {
    // given
    Medicament medicament = new Medicament();
    medicament.setId(-1L);
    PrescriptionItem prescriptionItem = new PrescriptionItem();
    prescriptionItem.setMedicament(medicament);
    given(medicamentService.getMedicamentById(anyLong()))
        .willThrow(new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER));

    // then
    Assertions.assertThatThrownBy(
            () -> prescriptionItemService.createPrescriptionItem(prescriptionItem))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenCreatePrescriptionItemMedicamentIsNotFound() {
    // given
    PrescriptionItem prescriptionItem = new PrescriptionItem();
    prescriptionItem.setMedicament(new Medicament());
    given(medicamentService.getMedicamentById(any()))
        .willThrow(new NoDataFoundException(ExceptionConst.MEDICAMENT_NOT_FOUND));

    // then
    Assertions.assertThatThrownBy(
            () -> prescriptionItemService.createPrescriptionItem(prescriptionItem))
        .isInstanceOf(NoDataFoundException.class)
        .hasMessage(ExceptionConst.MEDICAMENT_NOT_FOUND);
  }

  @Test
  void shouldReturnUpdatePrescriptionItemCorrect() {
    // given
    Medicament medicament = new Medicament();
    medicament.setId(1L);
    PrescriptionItem prescriptionItem = new PrescriptionItem();
    prescriptionItem.setMedicament(medicament);
    given(medicamentService.getMedicamentById(anyLong())).willReturn(medicament);
    given(prescriptionItemRepository.findById(anyLong())).willReturn(Optional.of(prescriptionItem));
    given(prescriptionItemRepository.save(any(PrescriptionItem.class)))
        .willReturn(prescriptionItem);

    // when
    PrescriptionItem result = prescriptionItemService.updatePrescriptionItem(1L, prescriptionItem);

    // then
    verify(prescriptionItemRepository, times(1)).findById(anyLong());
    verify(medicamentService, times(1)).getMedicamentById(anyLong());
    verify(prescriptionItemRepository, times(1)).save(any(PrescriptionItem.class));

    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getMedicament()).isNotNull();
    Assertions.assertThat(result.getMedicament().getId()).isEqualTo(1L);
  }

  @Test
  void shouldThrowExceptionWhenUpdatePrescriptionItemIsNull() {
    Assertions.assertThatThrownBy(() -> prescriptionItemService.updatePrescriptionItem(1L, null))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_PRESCRIPTION_ITEM_OBJECT);
  }

  @Test
  void shouldThrowExceptionWhenUpdatePrescriptionItemIdIsNull() {
    Assertions.assertThatThrownBy(
            () -> prescriptionItemService.updatePrescriptionItem(null, new PrescriptionItem()))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenUpdatePrescriptionItemIdIsWrong() {
    Assertions.assertThatThrownBy(
            () -> prescriptionItemService.updatePrescriptionItem(-1L, new PrescriptionItem()))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenUpdatePrescriptionItemMedicamentIdIsNull() {
    // given
    Medicament medicament = new Medicament();
    PrescriptionItem prescriptionItem = new PrescriptionItem();
    prescriptionItem.setMedicament(medicament);
    given(prescriptionItemRepository.findById(anyLong())).willReturn(Optional.of(prescriptionItem));
    given(medicamentService.getMedicamentById(any()))
        .willThrow(new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER));

    // then
    Assertions.assertThatThrownBy(
            () -> prescriptionItemService.updatePrescriptionItem(1L, prescriptionItem))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenUpdatePrescriptionItemMedicamentIdIsWrong() {
    // given
    Medicament medicament = new Medicament();
    medicament.setId(-1L);
    PrescriptionItem prescriptionItem = new PrescriptionItem();
    prescriptionItem.setMedicament(medicament);
    given(prescriptionItemRepository.findById(anyLong())).willReturn(Optional.of(prescriptionItem));
    given(medicamentService.getMedicamentById(anyLong()))
        .willThrow(new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER));

    // then
    Assertions.assertThatThrownBy(
            () -> prescriptionItemService.updatePrescriptionItem(1L, prescriptionItem))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenUpdatePrescriptionItemMedicamentIsNotFound() {
    // given
    Medicament medicament = new Medicament();
    medicament.setId(1L);
    PrescriptionItem prescriptionItem = new PrescriptionItem();
    prescriptionItem.setMedicament(medicament);
    given(prescriptionItemRepository.findById(anyLong())).willReturn(Optional.of(prescriptionItem));
    given(medicamentService.getMedicamentById(anyLong()))
        .willThrow(new NoDataFoundException(ExceptionConst.MEDICAMENT_NOT_FOUND));

    // then
    Assertions.assertThatThrownBy(
            () -> prescriptionItemService.updatePrescriptionItem(1L, prescriptionItem))
        .isInstanceOf(NoDataFoundException.class)
        .hasMessage(ExceptionConst.MEDICAMENT_NOT_FOUND);
  }

  @Test
  void shouldThrowExceptionWhenUpdatePrescriptionItemIsNotFound() {
    // given
    given(prescriptionItemRepository.findById(anyLong())).willReturn(Optional.empty());

    // then
    Assertions.assertThatThrownBy(
            () -> prescriptionItemService.updatePrescriptionItem(1L, new PrescriptionItem()))
        .isInstanceOf(NoDataFoundException.class)
        .hasMessage(ExceptionConst.PRESCRIPTION_ITEM_NOT_FOUND);
  }

  @Test
  void shouldReturnDeletePrescriptionItemCorrect() {
    // given
    PrescriptionItem prescriptionItem = new PrescriptionItem();
    prescriptionItem.setId(1L);
    given(prescriptionItemRepository.findById(anyLong())).willReturn(Optional.of(prescriptionItem));

    doAnswer(
            invocation -> {
              PrescriptionItem result = invocation.getArgument(0);
              Assertions.assertThat(result).isNotNull();
              Assertions.assertThat(result.getId()).isEqualTo(1L);
              return null;
            })
        .when(prescriptionItemRepository)
        .delete(any(PrescriptionItem.class));

    // when
    prescriptionItemService.deletePrescriptionItem(1L);

    // then
    verify(prescriptionItemRepository, times(1)).findById(anyLong());
    verify(prescriptionItemRepository, times(1)).delete(any(PrescriptionItem.class));
  }

  @Test
  void shouldThrowExceptionWhenDeletePrescriptionItemIdIsNull() {
    Assertions.assertThatThrownBy(() -> prescriptionItemService.deletePrescriptionItem(null))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenDeletePrescriptionItemIdIsWrong() {
    Assertions.assertThatThrownBy(() -> prescriptionItemService.deletePrescriptionItem(-1L))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenDeletePrescriptionItemIsNotFound() {
    // given
    given(prescriptionItemRepository.findById(anyLong())).willReturn(Optional.empty());

    // then
    Assertions.assertThatThrownBy(() -> prescriptionItemService.deletePrescriptionItem(1L))
        .isInstanceOf(NoDataFoundException.class)
        .hasMessage(ExceptionConst.PRESCRIPTION_ITEM_NOT_FOUND);
  }
}
