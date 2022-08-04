package com.cannibal90.petclinic.WEB.controller;

import com.cannibal90.petclinic.DAL.model.Medicament;
import com.cannibal90.petclinic.WEB.dto.MedicamentDTO;
import com.cannibal90.petclinic.WEB.dto.PageDTO;
import com.cannibal90.petclinic.WEB.exception.ExceptionConst;
import com.cannibal90.petclinic.WEB.exception.NoDataFoundException;
import com.cannibal90.petclinic.WEB.exception.WrongParameterException;
import com.cannibal90.petclinic.WEB.mapper.MedicamentMapper;
import com.cannibal90.petclinic.WEB.service.MedicamentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicamentController.class)
@AutoConfigureMockMvc
class MedicamentControllerTest {

  @MockBean private MedicamentService medicamentService;

  @MockBean private MedicamentMapper medicamentMapper;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  private static final String MEDICAMENT_URI = "/api/medicament";

  private static MedicamentDTO medicamentDTO;

  @BeforeAll
  static void setUp() {
    medicamentDTO = new MedicamentDTO();
    medicamentDTO.setId(1L);
    medicamentDTO.setName("Test");
    medicamentDTO.setPrice(12.32);
  }

  public String performMockRequest(
      MockHttpServletRequestBuilder method, Object body, HttpStatus expectedStatus)
      throws Exception {
    return mockMvc
        .perform(
            method
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
        .andExpect(status().is(expectedStatus.value()))
        .andReturn()
        .getResponse()
        .getContentAsString();
  }

  public @Test void shouldGetMedicamentByIdReturn200() throws Exception {
    // given
    given(medicamentService.getMedicamentById(anyLong())).willReturn(new Medicament());
    given(medicamentMapper.medicamentToMedicamentDTO(any(Medicament.class)))
        .willReturn(medicamentDTO);

    // when
    String result = performMockRequest(get(MEDICAMENT_URI + "/{id}", 2), null, HttpStatus.OK);

    // then
    Assertions.assertThat(result)
        .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(medicamentDTO));
    verify(medicamentService, times(1)).getMedicamentById(anyLong());
    verify(medicamentMapper, times(1)).medicamentToMedicamentDTO(any(Medicament.class));
  }

  @Test
  void shouldGetMedicamentByIdThrowWrongParameterException400() throws Exception {
    // given
    given(medicamentService.getMedicamentById(anyLong()))
        .willThrow(new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER));

    // then
    String result =
        performMockRequest(get(MEDICAMENT_URI + "/{id}", 0), null, HttpStatus.BAD_REQUEST);
    Assertions.assertThat(result).contains(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldGetMedicamentByIdThrowNoDataFoundException404() throws Exception {
    // given
    given(medicamentService.getMedicamentById(anyLong()))
        .willThrow(new NoDataFoundException(ExceptionConst.MEDICAMENT_NOT_FOUND));

    // then
    String result =
        performMockRequest(get(MEDICAMENT_URI + "/{id}", 2L), null, HttpStatus.NOT_FOUND);
    Assertions.assertThat(result).contains(ExceptionConst.MEDICAMENT_NOT_FOUND);
  }

  @Test
  void shouldGetAllMedicamentsReturn200() throws Exception {
    // given
    PageDTO pageDTO = new PageDTO();
    pageDTO.setTotalPages(10);
    pageDTO.setNumber(0);
    given(medicamentMapper.medicamentPageToPageDTO(any())).willReturn(pageDTO);

    // when
    MvcResult mvcResult =
        mockMvc
            .perform(
                get(MEDICAMENT_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("page", "10")
                    .param("size", "4"))
            .andExpect(status().isOk())
            .andReturn();
    String result = mvcResult.getResponse().getContentAsString();

    // then
    Assertions.assertThat(result)
        .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(pageDTO));
    verify(medicamentMapper, times(1)).medicamentPageToPageDTO(any());
  }

  @Test
  void shouldCreateMedicamentReturn201() throws Exception {
    // given
    given(medicamentService.createMedicament(any())).willReturn(new Medicament());
    given(medicamentMapper.medicamentToMedicamentDTO(any(Medicament.class)))
        .willReturn(medicamentDTO);

    // when
    String result = performMockRequest(post(MEDICAMENT_URI), medicamentDTO, HttpStatus.CREATED);

    // then
    Assertions.assertThat(result)
        .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(medicamentDTO));
    verify(medicamentService, times(1)).createMedicament(any());
    verify(medicamentMapper, times(1)).medicamentToMedicamentDTO(any(Medicament.class));
  }

  @Test
  void shouldCreateMedicamentThrowWrongParameterException400() throws Exception {
    // given
    given(medicamentService.createMedicament(any()))
        .willThrow(new WrongParameterException(ExceptionConst.WRONG_MEDICAMENT_OBJECT));

    // then
    String result = performMockRequest(post(MEDICAMENT_URI), medicamentDTO, HttpStatus.BAD_REQUEST);
    Assertions.assertThat(result).contains(ExceptionConst.WRONG_MEDICAMENT_OBJECT);
  }

  @Test
  void shouldCreateMedicamentThrowValidationException400() throws Exception {
    String result =
        performMockRequest(post(MEDICAMENT_URI), new MedicamentDTO(), HttpStatus.BAD_REQUEST);
    Assertions.assertThat(result).contains("Validation Errors:");
  }

  @Test
  void shouldUpdateMedicamentReturn200() throws Exception {
    // given
    given(medicamentService.updateMedicament(anyLong(), any())).willReturn(new Medicament());
    given(medicamentMapper.medicamentToMedicamentDTO(any(Medicament.class)))
        .willReturn(medicamentDTO);

    // when
    String result =
        performMockRequest(put(MEDICAMENT_URI + "/{id}", 2), medicamentDTO, HttpStatus.OK);

    // then
    Assertions.assertThat(result)
        .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(medicamentDTO));
    verify(medicamentService, times(1)).updateMedicament(anyLong(), any());
    verify(medicamentMapper, times(1)).medicamentToMedicamentDTO(any(Medicament.class));
  }

  @Test
  void shouldUpdateMedicamentThrowWrongParameterException400() throws Exception {
    // given
    given(medicamentService.updateMedicament(anyLong(), any()))
        .willThrow(new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER));

    // then
    String result =
        performMockRequest(put(MEDICAMENT_URI + "/{id}", 2), medicamentDTO, HttpStatus.BAD_REQUEST);
    Assertions.assertThat(result).contains(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldUpdateMedicamentThrowValidationException400() throws Exception {
    String result =
        performMockRequest(
            put(MEDICAMENT_URI + "/{id}", 2), new MedicamentDTO(), HttpStatus.BAD_REQUEST);
    Assertions.assertThat(result).contains("Validation Errors:");
  }

  @Test
  void shouldUpdateMedicamentThrowNoDataFoundException404() throws Exception {
    // given
    given(medicamentService.updateMedicament(anyLong(), any()))
        .willThrow(new NoDataFoundException(ExceptionConst.MEDICAMENT_NOT_FOUND));

    // then
    String result =
        performMockRequest(put(MEDICAMENT_URI + "/{id}", 2), medicamentDTO, HttpStatus.NOT_FOUND);
    Assertions.assertThat(result).contains(ExceptionConst.MEDICAMENT_NOT_FOUND);
  }

  @Test
  void shouldDeleteMedicamentReturn204() throws Exception {
    // then
    performMockRequest(delete(MEDICAMENT_URI + "/{id}", 1), null, HttpStatus.NO_CONTENT);

    verify(medicamentService, times(1)).deleteMedicament(anyLong());
  }

  @Test
  void shouldDeleteMedicamentThrowWrongParameterException400() throws Exception {
    // given
    doThrow(new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER))
        .when(medicamentService)
        .deleteMedicament(anyLong());

    // then
    String result =
        performMockRequest(delete(MEDICAMENT_URI + "/{id}", 2), null, HttpStatus.BAD_REQUEST);
    Assertions.assertThat(result).contains(ExceptionConst.WRONG_ID_PARAMETER);

    verify(medicamentService, times(1)).deleteMedicament(anyLong());
  }

  @Test
  void shouldDeleteMedicamentThrowNoDataFoundException404() throws Exception {
    // given
    doThrow(new NoDataFoundException(ExceptionConst.MEDICAMENT_NOT_FOUND))
        .when(medicamentService)
        .deleteMedicament(anyLong());

    // then
    String result =
        performMockRequest(delete(MEDICAMENT_URI + "/{id}", 2), null, HttpStatus.NOT_FOUND);
    Assertions.assertThat(result).contains(ExceptionConst.MEDICAMENT_NOT_FOUND);

    verify(medicamentService, times(1)).deleteMedicament(anyLong());
  }
}
