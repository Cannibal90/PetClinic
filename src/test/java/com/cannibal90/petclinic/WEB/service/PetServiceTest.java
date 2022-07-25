package com.cannibal90.petclinic.WEB.service;

import com.cannibal90.petclinic.DAL.model.Owner;
import com.cannibal90.petclinic.DAL.model.Pet;
import com.cannibal90.petclinic.DAL.model.Pet;
import com.cannibal90.petclinic.DAL.model.Species;
import com.cannibal90.petclinic.DAL.repository.PetRepository;
import com.cannibal90.petclinic.WEB.exception.ExceptionConst;
import com.cannibal90.petclinic.WEB.exception.NoDataFoundException;
import com.cannibal90.petclinic.WEB.exception.WrongParameterException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    PetRepository petRepository;
    
    @Mock
    OwnerService ownerService;
    
    @Mock
    SpeciesService speciesService;
    
    @InjectMocks
    PetService petService;

    @Test
    void shouldReturnPetByIdCorrect() {
        // given
        given(petRepository.findById(anyLong()))
                .willReturn(Optional.of(new Pet()));

        // when
        Pet result = petService.getPetById(1L);

        // then
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenGetPetIdIsNull() {
        Assertions.assertThatThrownBy(() -> petService.getPetById(null))
                .isInstanceOf(WrongParameterException.class)
                .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenGetPetIdIsWrong() {
        Assertions.assertThatThrownBy(() -> petService.getPetById(-1L))
                .isInstanceOf(WrongParameterException.class)
                .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenGetPetIdNotFound() {
        // given
        given(petRepository.findById(anyLong())).willReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> petService.getPetById(1L))
                .isInstanceOf(NoDataFoundException.class)
                .hasMessage(ExceptionConst.PET_NOT_FOUND);
    }

    @Test
    void shouldReturnGetAllPetsByOwnerIdCorrect() {
        //given
        List<Pet> templateData = createTemplateData();
        given(ownerService.getOwnerById(anyLong())).willReturn(new Owner());
        given(petRepository.findAllByOwnersIsContaining(any(Owner.class))).willReturn(templateData);

        //when
        List<Pet> result = petService.getAllPetsByOwnerId(1L);

        //then
        verify(ownerService, times(1)).getOwnerById(anyLong());
        verify(petRepository,times(1)).findAllByOwnersIsContaining(any(Owner.class));
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(templateData.size());
        Assertions.assertThat(result).isEqualTo(templateData);
    }

    @Test
    void shouldThrowExceptionWhenGetAllPetsByOwnerIdIsNull(){
        Assertions.assertThatThrownBy(() -> petService.getAllPetsByOwnerId(null)).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenGetAllPetsByOwnerIdIsWrong(){
        Assertions.assertThatThrownBy(() -> petService.getAllPetsByOwnerId(-1L)).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldReturnGetAllPetsBySpeciesNameCorrect(){
        //given
        List<Pet> templateData = createTemplateData();
        given(speciesService.getSpeciesByName(anyString())).willReturn(new Species());
        given(petRepository.findAllBySpecies(any(Species.class))).willReturn(templateData);

        //when
        List<Pet> result = petService.getAllPetsBySpeciesName("Cat");

        //then
        verify(speciesService, times(1)).getSpeciesByName(anyString());
        verify(petRepository,times(1)).findAllBySpecies(any(Species.class));
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).hasSize(templateData.size());
        Assertions.assertThat(result).isEqualTo(templateData);
    }

    @Test
    void shouldReturnCheckOwnersAndSpeciesCorrect() {
        //given
        Pet pet = createTemplateData().get(0);
        given(ownerService.getOwnerById(anyLong())).willReturn(new Owner());
        given(speciesService.getSpeciesById(anyLong())).willReturn(new Species());

        //when
       petService.checkOwnersAndSpecie(pet);

       //then
        verify(ownerService,times(2)).getOwnerById(anyLong());
        verify(speciesService,times(1)).getSpeciesById(anyLong());

    }


    @Test
    void shouldReturnRegisterPetCorrect() {
        //given
        Pet pet = createTemplateData().get(0);
        given(petRepository.save(any(Pet.class))).willReturn(pet);

        //when
        Pet result = petService.registerPet(pet);

        //then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getOwners()).hasSize(2);
        Assertions.assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowExceptionWhenRegisterPetIsNull() {
        Assertions.assertThatThrownBy(() -> petService.registerPet(null)).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_PET_OBJECT);
    }

    @Test
    void shouldThrowExceptionWhenRegisterPetOwnersAreNull() {
        //given
        Pet pet = new Pet();
        //then
        Assertions.assertThatThrownBy(() -> petService.registerPet(pet)).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.NO_PET_OWNER);
    }

    @Test
    void shouldThrowExceptionWhenRegisterPetOwnersAreEmpty() {
        //given
        Pet pet = new Pet();
        pet.setOwners(new HashSet<>());
        //then
        Assertions.assertThatThrownBy(() -> petService.registerPet(pet)).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.NO_PET_OWNER);
    }

    @Test
    void shouldReturnUpdatePetDataCorrect() {
        //given
        Pet pet = createTemplateData().get(0);
        given(petRepository.findById(anyLong())).willReturn(Optional.of(new Pet()));
        given(petRepository.save(any(Pet.class))).willReturn(pet);

        //when
        Pet result = petService.updatePetData(1L, pet);

        //then
        verify(petRepository,times(1)).findById(anyLong());
        verify(petRepository, times(1)).save(any(Pet.class));
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(1L);
        Assertions.assertThat(result.getOwners()).hasSize(2);
    }

    @Test
    void shouldThrowExceptionWhenUpdatePetIsNull(){
        Assertions.assertThatThrownBy(() -> petService.updatePetData(1L, null)).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_PET_OBJECT);
    }

    @Test
    void shouldThrowExceptionWhenUpdatePetIdIsNull(){
        Assertions.assertThatThrownBy(() -> petService.updatePetData(null, new Pet())).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenUpdatePetIdIsWrong(){
        Assertions.assertThatThrownBy(() -> petService.updatePetData(-1L, new Pet())).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenUpdatePetOwnersAreNull(){
        Assertions.assertThatThrownBy(() -> petService.updatePetData(1L, new Pet())).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.NO_PET_OWNER);
    }

    @Test
    void shouldThrowExceptionWhenUpdatePetOwnersAreEmpty(){
        //given
        Pet pet = new Pet();
        pet.setOwners(new HashSet<>());
        //then
        Assertions.assertThatThrownBy(() -> petService.updatePetData(1L, pet)).isInstanceOf(WrongParameterException.class).hasMessage(ExceptionConst.NO_PET_OWNER);
    }

    @Test
    void shouldThrowExceptionWhenUpdatePetIsNotFound(){
        //given
        Pet pet = createTemplateData().get(0);
        given(petRepository.findById(anyLong())).willReturn(Optional.empty());
        //then
        Assertions.assertThatThrownBy(() -> petService.updatePetData(1L, pet)).isInstanceOf(NoDataFoundException.class).hasMessage(ExceptionConst.PET_NOT_FOUND);
    }


    @Test
    void shouldReturnDeletePetDataCorrect() {
        // given
        Pet pet = new Pet();
        pet.setId(1L);
        given(petRepository.findById(anyLong())).willReturn(Optional.of(pet));

        doAnswer(
                invocation -> {
                    Pet result = invocation.getArgument(0);
                    Assertions.assertThat(result).isNotNull();
                    Assertions.assertThat(result.getId()).isEqualTo(1L);
                    return null;
                })
                .when(petRepository)
                .delete(any(Pet.class));

        // when
        petService.deletePetData(1L);

        // then
        verify(petRepository, times(1)).findById(anyLong());
        verify(petRepository, times(1)).delete(any(Pet.class));
    }

    @Test
    void shouldThrowExceptionWhenDeletePetDataIdIsNull() {
        Assertions.assertThatThrownBy(() -> petService.deletePetData(null))
                .isInstanceOf(WrongParameterException.class)
                .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenDeletePetDataIdIsWrong() {
        Assertions.assertThatThrownBy(() -> petService.deletePetData(-1L))
                .isInstanceOf(WrongParameterException.class)
                .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
    }

    @Test
    void shouldThrowExceptionWhenDeletePetDataIsNotFound() {
        // given
        given(petRepository.findById(anyLong())).willReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> petService.deletePetData(1L))
                .isInstanceOf(NoDataFoundException.class)
                .hasMessage(ExceptionConst.PET_NOT_FOUND);
    }

    private List<Pet> createTemplateData() {
        Species species = new Species();
        species.setId(1L);
        species.setSpeciesName("Cat");

        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setFirstName("John");

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirstName("Anna");

        Set<Owner> owners = Set.of(owner1, owner2);

        Pet pet1 = new Pet();
        pet1.setId(1L);
        pet1.setSpecies(species);
        pet1.setName("Cat1");
        pet1.setOwners(owners);

        Pet pet2 = new Pet();
        pet2.setId(1L);
        pet2.setSpecies(species);
        pet2.setName("Cat2");
        pet2.setOwners(owners);

        return List.of(pet1, pet2);
    }
}