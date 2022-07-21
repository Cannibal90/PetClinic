package com.cannibal90.petclinic.WEB.service;

import com.cannibal90.petclinic.DAL.model.Species;
import com.cannibal90.petclinic.DAL.repository.SpeciesRepository;
import com.cannibal90.petclinic.WEB.exception.ExceptionConst;
import com.cannibal90.petclinic.WEB.exception.NoDataFoundException;
import com.cannibal90.petclinic.WEB.exception.WrongParameterException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpeciesServiceTest {

    @Mock
    SpeciesRepository speciesRepository;

    @InjectMocks
    SpeciesService speciesService;

    @Test
    void shouldReturnGetSpeciesByIdCorrect(){
        //given
        given(speciesRepository.findById(anyLong())).willReturn(Optional.of(new Species()));
        //when
        Species result = speciesService.getSpeciesById(1L);
        //then
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenGetSpeciesIdIsNull(){
        Assertions.assertThatThrownBy(() -> speciesService.getSpeciesById(null)).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenGetSpeciesIdIsWrong() {
        Assertions.assertThatThrownBy(() -> speciesService.getSpeciesById(-1L)).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenGetSpeciesIsNotFound() {
        //given
        given(speciesRepository.findById(anyLong())).willReturn(Optional.empty());

        //then
        Assertions.assertThatThrownBy(() -> speciesService.getSpeciesById(1L)).isInstanceOf(NoDataFoundException.class).hasMessage(ExceptionConst.SPECIES_NOT_FOUND);
    }

    @Test
    void shouldReturnGetSpeciesByNameCorrect() {
        //given
        given(speciesRepository.findSpeciesBySpeciesNameIgnoreCase(anyString())).willReturn(Optional.of(new Species()));

        //when
        Species result = speciesService.getSpeciesByName("test");

        //then
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenGetSpeciesByNameIsNull(){
        Assertions.assertThatThrownBy(() -> speciesService.getSpeciesByName(null)).isInstanceOf(NoDataFoundException.class).hasMessage(ExceptionConst.WRONG_SPECIES_NAME);
    }

    @Test
    void shouldThrowExceptionWhenGetSpeciesByNameIsEmpty(){
        Assertions.assertThatThrownBy(() -> speciesService.getSpeciesByName("")).isInstanceOf(NoDataFoundException.class).hasMessage(ExceptionConst.WRONG_SPECIES_NAME);
    }

    @Test
    void shouldThrowExceptionWhenGetSpeciesByNameIsNotFound(){
        //given
        given(speciesRepository.findSpeciesBySpeciesNameIgnoreCase(anyString())).willReturn(Optional.empty());
        //then
        Assertions.assertThatThrownBy(() -> speciesService.getSpeciesByName("test")).isInstanceOf(NoDataFoundException.class).hasMessage(ExceptionConst.SPECIES_NOT_FOUND);
    }

    @Test
    void shouldReturnGetAllSpeciesCorrect(){
        //given
        Species species1 = new Species();
        Species species2 = new Species();
        given(speciesRepository.findAll()).willReturn(List.of(species1, species2));

        //when
        List<Species> result = speciesService.getAllSpecies();

        //then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(2);
    }

    @Test
    void shouldReturnCreateSpeciesCorrect(){
        //given
        Species species = new Species();
        species.setId(1L);
        given(speciesRepository.save(any(Species.class))).willReturn(species);

        //when
        Species result = speciesService.createSpecies(species);

        //then
        verify(speciesRepository,times(1)).save(any(Species.class));
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowExceptionWhenCreateSpeciesIsNull(){
        Assertions.assertThatThrownBy(() -> speciesService.createSpecies(null)).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_SPECIES_OBJECT);
    }

    @Test
    void shouldReturnUpdateSpeciesCorrect() {
        //given
        Species species = new Species();
        species.setId(1L);
        species.setSpeciesName("Dog");
        given(speciesRepository.findById(anyLong())).willReturn(Optional.of(species));
        given(speciesRepository.save(any(Species.class))).willReturn(species);

        //when
        Species result = speciesService.updateSpecies(1L, species);

        //then
        verify(speciesRepository, times(1)).findById(anyLong());
        verify(speciesRepository, times(1)).save(any(Species.class));
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(1L);
        Assertions.assertThat(result.getSpeciesName()).isEqualTo("Dog");
    }

    @Test
    void shouldThrowExceptionWhenUpdateSpeciesIsNull(){
        Assertions.assertThatThrownBy(() -> speciesService.updateSpecies(1L, null)).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_SPECIES_OBJECT);
    }

    @Test
    void shouldThrowExceptionWhenUpdateSpeciesIdIsNull(){
        Assertions.assertThatThrownBy(() -> speciesService.updateSpecies(null, new Species())).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenUpdateSpeciesIdIsWrong(){
        Assertions.assertThatThrownBy(() -> speciesService.updateSpecies(-1L, new Species())).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenUpdateSpeciesIsNotFound(){
        //given
        given(speciesRepository.findById(anyLong())).willReturn(Optional.empty());
        //then
        Assertions.assertThatThrownBy(() -> speciesService.updateSpecies(1L, new Species())).isInstanceOf(NoDataFoundException.class).hasMessage(ExceptionConst.SPECIES_NOT_FOUND);
    }

    @Test
    void shouldReturnDeleteSpeciesCorrect() {
        //given
        Species species = new Species();
        species.setId(1L);
        given(speciesRepository.findById(anyLong())).willReturn(Optional.of(species));

        doAnswer(invocation -> {
            Species result = invocation.getArgument(0);
            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result.getId()).isEqualTo(1L);

            return null;
        }).when(speciesRepository).delete(any(Species.class));

        //when
        speciesService.deleteSpecies(1L);

        //then
        verify(speciesRepository,times(1)).findById(anyLong());
        verify(speciesRepository, times(1)).delete(any(Species.class));
    }

    @Test
    void shouldThrowExceptionWhenDeleteSpeciesIdIsNull(){
        Assertions.assertThatThrownBy(() -> speciesService.deleteSpecies(null)).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenDeleteSpeciesIdIsWrong(){
        Assertions.assertThatThrownBy(() -> speciesService.deleteSpecies(-1L)).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenDeleteSpeciesIsNotFound(){
        //given
        given(speciesRepository.findById(anyLong())).willReturn(Optional.empty());
        //then
        Assertions.assertThatThrownBy(() -> speciesService.deleteSpecies(1L)).isInstanceOf(NoDataFoundException.class).hasMessage(ExceptionConst.SPECIES_NOT_FOUND);
    }

}