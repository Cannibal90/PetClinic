package com.cannibal90.petclinic.WEB.service;

import com.cannibal90.petclinic.DAL.model.Medicament;
import com.cannibal90.petclinic.DAL.repository.MedicamentRepository;
import com.cannibal90.petclinic.WEB.exception.ExceptionConst;
import com.cannibal90.petclinic.WEB.exception.NoDataFoundException;
import com.cannibal90.petclinic.WEB.exception.WrongParameterException;
import liquibase.pro.packaged.M;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicamentServiceTest {

  @Mock Page<Medicament> medicaments;
  @Mock MedicamentRepository medicamentRepository;

  @InjectMocks MedicamentService medicamentService;

  @Test
  void shouldReturnMedicamentByIdCorrect() {
    // given
    given(medicamentRepository.findById(anyLong())).willReturn(Optional.of(new Medicament()));
    // when
    Medicament result = medicamentService.getMedicamentById(1L);
    // then
    Assertions.assertThat(result).isNotNull();
  }

  @Test
  void shouldThrowExceptionWhenGetMedicamentIdWithNull() {
    Assertions.assertThatThrownBy(() -> medicamentService.getMedicamentById(null))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenGetMedicamentIdWithWrongArgument() {
    Assertions.assertThatThrownBy(() -> medicamentService.getMedicamentById(-1L))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenGetMedicamentNotFound() {
    // given
    given(medicamentRepository.findById(anyLong())).willReturn(Optional.empty());

    // then
    Assertions.assertThatThrownBy(() -> medicamentService.getMedicamentById(1L))
        .isInstanceOf(NoDataFoundException.class)
        .hasMessage(ExceptionConst.MEDICAMENT_NOT_FOUND);
  }

  @Test
  void shouldGetAllMedicamentsCorrect() {
    // given
    PageRequest pageRequest = PageRequest.of(0, 4);
    given(medicamentRepository.findAll(any(Pageable.class))).willReturn(medicaments);

    // when
    Page<Medicament> result = medicamentService.getAllMedicaments(pageRequest);

    // then
    verify(medicamentRepository, times(1)).findAll(any(Pageable.class));
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getNumber()).isEqualTo(0);
  }

  @Test
  void shouldCreateMedicamentCorrect() {
    // given
    Medicament medicament = new Medicament();
    medicament.setId(1L);
    given(medicamentRepository.save(any())).willReturn(medicament);

    // when
    Medicament result = medicamentService.createMedicament(medicament);

    // then
    verify(medicamentRepository, times(1)).save(any(Medicament.class));
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getId()).isEqualTo(1L);
  }

  @Test
  void shouldThrowExceptionWhenMedicamentIsNull() {
    Assertions.assertThatThrownBy(() -> medicamentService.createMedicament(null))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_MEDICAMENT_OBJECT);
  }

  @Test
  void shouldUpdateMedicamentCorrect() {
    // given
    Medicament medicament = new Medicament();
    medicament.setId(1L);
    medicament.setName("Name");
    medicament.setPrice(12.3);
    given(medicamentRepository.findById(anyLong())).willReturn(Optional.of(new Medicament()));
    given(medicamentRepository.save(any(Medicament.class))).willReturn(medicament);

    // when
    Medicament result = medicamentService.updateMedicament(1L, new Medicament());

    // then
    verify(medicamentRepository, times(1)).findById(anyLong());
    verify(medicamentRepository, times(1)).save(any(Medicament.class));
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getId()).isEqualTo(1L);
    Assertions.assertThat(result.getName()).isEqualTo("Name");
    Assertions.assertThat(result.getPrice()).isEqualTo(12.3);
  }

  @Test
  void shouldThrowExceptionWhenUpdateMedicamentIsNull() {
    Assertions.assertThatThrownBy(() -> medicamentService.updateMedicament(1L, null))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_MEDICAMENT_OBJECT);
  }

  @Test
  void shouldThrowExceptionWhenUpdateMedicamentIdIsNull() {
    Assertions.assertThatThrownBy(() -> medicamentService.updateMedicament(null, new Medicament()))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenUpdateMedicamentIdIsWrong() {
    Assertions.assertThatThrownBy(() -> medicamentService.updateMedicament(-1L, new Medicament()))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenUpdateMedicamentIsNotFound() {
    // given
    given(medicamentRepository.findById(anyLong())).willReturn(Optional.empty());

    // then
    Assertions.assertThatThrownBy(() -> medicamentService.updateMedicament(1L, new Medicament()))
        .isInstanceOf(NoDataFoundException.class)
        .hasMessage(ExceptionConst.MEDICAMENT_NOT_FOUND);
  }

  @Test
  void shouldDeleteMedicamentCorrect() {
    // given
    given(medicamentRepository.findById(1L)).willReturn(Optional.of(new Medicament()));

    doAnswer(
            invocation -> {
              Medicament result = invocation.getArgument(0);
              Assertions.assertThat(result).isNotNull();
              return null;
            })
        .when(medicamentRepository)
        .delete(any(Medicament.class));

    // when
    medicamentService.deleteMedicament(1L);

    // then
    verify(medicamentRepository, times(1)).findById(anyLong());
    verify(medicamentRepository, times(1)).delete(any(Medicament.class));
  }

  @Test
  void shouldThrowExceptionWhenDeleteMedicamentIdIsNull() {
    Assertions.assertThatThrownBy(() -> medicamentService.deleteMedicament(null))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenDeleteMedicamentIdIsWrong() {
    Assertions.assertThatThrownBy(() -> medicamentService.deleteMedicament(-1L))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenDeleteMedicamentIsNotFound() {
    // given
    given(medicamentRepository.findById(anyLong())).willReturn(Optional.empty());

    // then
    Assertions.assertThatThrownBy(() -> medicamentService.deleteMedicament(1L))
        .isInstanceOf(NoDataFoundException.class)
        .hasMessage(ExceptionConst.MEDICAMENT_NOT_FOUND);
  }
}
