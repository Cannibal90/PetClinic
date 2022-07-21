package com.cannibal90.petclinic.WEB.service;

import com.cannibal90.petclinic.DAL.model.Owner;
import com.cannibal90.petclinic.DAL.model.Owner;
import com.cannibal90.petclinic.DAL.model.Owner;
import com.cannibal90.petclinic.DAL.model.Owner;
import com.cannibal90.petclinic.DAL.repository.OwnerRepository;
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
class OwnerServiceTest {

    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerService ownerService;

    @Test
    void shouldReturnOwnerByIdCorrect() {
        // given
        given(ownerRepository.findById(anyLong()))
                .willReturn(Optional.of(new Owner()));

        // when
        Owner result = ownerService.getOwnerById(1L);

        // then
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenGetOwnerIdIsNull() {
        Assertions.assertThatThrownBy(() -> ownerService.getOwnerById(null))
                .isInstanceOf(WrongParameterException.class)
                .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenGetOwnerIdIsWrong() {
        Assertions.assertThatThrownBy(() -> ownerService.getOwnerById(-1L))
                .isInstanceOf(WrongParameterException.class)
                .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenGetOwnerIdNotFound() {
        // given
        given(ownerRepository.findById(anyLong())).willReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> ownerService.getOwnerById(1L))
                .isInstanceOf(NoDataFoundException.class)
                .hasMessage(ExceptionConst.OWNER_NOT_FOUND);
    }

    @Test
    void shouldReturnCreateOwnerCorrect(){
        //given
        Owner owner = new Owner();
        owner.setId(1L);
        given(ownerRepository.save(any(Owner.class))).willReturn(owner);

        //when
        Owner result = ownerService.createOwner(owner);

        //then
        verify(ownerRepository,times(1)).save(any(Owner.class));
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowExceptionWhenCreateOwnerIsNull(){
        Assertions.assertThatThrownBy(() -> ownerService.createOwner(null)).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_OWNER_OBJECT);
    }

    @Test
    void shouldReturnUpdateOwnerCorrect() {
        //given
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setFirstName("Kowalski");
        given(ownerRepository.findById(anyLong())).willReturn(Optional.of(owner));
        given(ownerRepository.save(any(Owner.class))).willReturn(owner);

        //when
        Owner result = ownerService.updateOwner(1L, owner);

        //then
        verify(ownerRepository, times(1)).findById(anyLong());
        verify(ownerRepository, times(1)).save(any(Owner.class));
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(1L);
        Assertions.assertThat(result.getFirstName()).isEqualTo("Kowalski");
    }

    @Test
    void shouldThrowExceptionWhenUpdateOwnerIsNull(){
        Assertions.assertThatThrownBy(() -> ownerService.updateOwner(1L, null)).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_OWNER_OBJECT);
    }

    @Test
    void shouldThrowExceptionWhenUpdateOwnerIdIsNull(){
        Assertions.assertThatThrownBy(() -> ownerService.updateOwner(null, new Owner())).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenUpdateOwnerIdIsWrong(){
        Assertions.assertThatThrownBy(() -> ownerService.updateOwner(-1L, new Owner())).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenUpdateOwnerIsNotFound(){
        //given
        given(ownerRepository.findById(anyLong())).willReturn(Optional.empty());
        //then
        Assertions.assertThatThrownBy(() -> ownerService.updateOwner(1L, new Owner())).isInstanceOf(NoDataFoundException.class).hasMessage(ExceptionConst.OWNER_NOT_FOUND);
    }

    @Test
    void shouldReturnDeleteOwnerCorrect() {
        // given
        Owner owner = new Owner();
        owner.setId(1L);
        given(ownerRepository.findById(anyLong())).willReturn(Optional.of(owner));

        doAnswer(
                invocation -> {
                    Owner result = invocation.getArgument(0);
                    Assertions.assertThat(result).isNotNull();
                    Assertions.assertThat(result.getId()).isEqualTo(1L);
                    return null;
                })
                .when(ownerRepository)
                .delete(any(Owner.class));

        // when
        ownerService.deleteOwner(1L);

        // then
        verify(ownerRepository, times(1)).findById(anyLong());
        verify(ownerRepository, times(1)).delete(any(Owner.class));
    }

    @Test
    void shouldThrowExceptionWhenDeleteOwnerIdIsNull() {
        Assertions.assertThatThrownBy(() -> ownerService.deleteOwner(null))
                .isInstanceOf(WrongParameterException.class)
                .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenDeleteOwnerIdIsWrong() {
        Assertions.assertThatThrownBy(() -> ownerService.deleteOwner(-1L))
                .isInstanceOf(WrongParameterException.class)
                .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenDeleteOwnerIsNotFound() {
        // given
        given(ownerRepository.findById(anyLong())).willReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> ownerService.deleteOwner(1L))
                .isInstanceOf(NoDataFoundException.class)
                .hasMessage(ExceptionConst.OWNER_NOT_FOUND);
    }

}