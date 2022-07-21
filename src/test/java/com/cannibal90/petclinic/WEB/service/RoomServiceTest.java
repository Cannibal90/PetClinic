package com.cannibal90.petclinic.WEB.service;

import com.cannibal90.petclinic.DAL.model.Room;
import com.cannibal90.petclinic.DAL.repository.RoomRepository;
import com.cannibal90.petclinic.WEB.exception.ExceptionConst;
import com.cannibal90.petclinic.WEB.exception.NoDataFoundException;
import com.cannibal90.petclinic.WEB.exception.WrongParameterException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

  @Mock RoomRepository roomRepository;

  @Mock Page<Room> rooms;

  @InjectMocks RoomService roomService;

  @Test
  void shouldReturnGetRoomByIdCorrect() {
    // given
    given(roomRepository.findById(anyLong())).willReturn(Optional.of(new Room()));
    // when
    Room result = roomService.getRoomById(1L);
    // then
    Assertions.assertThat(result).isNotNull();
  }

  @Test
  void shouldThrowExceptionWhenGetRoomIdIsNull() {
    Assertions.assertThatThrownBy(() -> roomService.getRoomById(null))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenGetRoomIdIsWrong() {
    Assertions.assertThatThrownBy(() -> roomService.getRoomById(-1L))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenGetRoomIsNotFound() {
    // given
    given(roomRepository.findById(anyLong())).willReturn(Optional.empty());

    // then
    Assertions.assertThatThrownBy(() -> roomService.getRoomById(1L))
        .isInstanceOf(NoDataFoundException.class)
        .hasMessage(ExceptionConst.ROOM_NOT_FOUND);
  }

  @Test
  void shouldReturnAllRoomsCorrect() {
    // given
    PageRequest pageRequest = PageRequest.of(0, 4);
    given(roomRepository.findAll(any(Pageable.class))).willReturn(rooms);

    // when
    Page<Room> result = roomService.getAllRooms(pageRequest);

    // then
    verify(roomRepository, times(1)).findAll(any(Pageable.class));
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getNumber()).isEqualTo(0);
  }

  @Test
  void shouldReturnCreateRoomCorrect() {
    // given
    Room room = new Room();
    room.setId(1L);
    given(roomRepository.save(any(Room.class))).willReturn(room);

    // when
    Room result = roomService.createRoom(room);

    // then
    verify(roomRepository, times(1)).save(any(Room.class));
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getId()).isEqualTo(1L);
  }

  @Test
  void shouldThrowExceptionWhenCreateRoomIsNull() {
    Assertions.assertThatThrownBy(() -> roomService.createRoom(null))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ROOM_OBJECT);
  }

  @Test
  void shouldReturnUpdateRoomCorrect() {
    // given
    Room room = new Room();
    room.setId(1L);
    given(roomRepository.findById(anyLong())).willReturn(Optional.of(room));
    given(roomRepository.save(any(Room.class))).willReturn(room);

    // when
    Room result = roomService.updateRoom(1L, room);

    // then
    verify(roomRepository, times(1)).findById(anyLong());
    verify(roomRepository, times(1)).save(any(Room.class));
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getId()).isEqualTo(1L);
  }

  @Test
  void shouldThrowExceptionWhenUpdateRoomIsNull() {
    Assertions.assertThatThrownBy(() -> roomService.updateRoom(1L, null))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ROOM_OBJECT);
  }

  @Test
  void shouldThrowExceptionWhenUpdateRoomIdIsNull() {
    Assertions.assertThatThrownBy(() -> roomService.updateRoom(null, new Room()))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenUpdateRoomIdIsWrong() {
    Assertions.assertThatThrownBy(() -> roomService.updateRoom(-1L, new Room()))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenUpdateRoomIsNotFound() {
    // given
    given(roomRepository.findById(anyLong())).willReturn(Optional.empty());
    // then
    Assertions.assertThatThrownBy(() -> roomService.updateRoom(1L, new Room()))
        .isInstanceOf(NoDataFoundException.class)
        .hasMessage(ExceptionConst.ROOM_NOT_FOUND);
  }

  @Test
  void shouldReturnDeleteRoomCorrect() {
    // given
    Room room = new Room();
    room.setId(1L);
    given(roomRepository.findById(anyLong())).willReturn(Optional.of(room));

    doAnswer(
            invocation -> {
              Room result = invocation.getArgument(0);
              Assertions.assertThat(result).isNotNull();
              Assertions.assertThat(result.getId()).isEqualTo(1L);

              return null;
            })
        .when(roomRepository)
        .delete(any(Room.class));

    // when
    roomService.deleteRoom(1L);

    // then
    verify(roomRepository, times(1)).findById(anyLong());
    verify(roomRepository, times(1)).delete(any(Room.class));
  }

  @Test
  void shouldThrowExceptionWhenDeleteRoomIdIsNull() {
    Assertions.assertThatThrownBy(() -> roomService.deleteRoom(null))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenDeleteRoomIdIsWrong() {
    Assertions.assertThatThrownBy(() -> roomService.deleteRoom(-1L))
        .isInstanceOf(WrongParameterException.class)
        .hasMessage(ExceptionConst.WRONG_ID_PARAMETER);
  }

  @Test
  void shouldThrowExceptionWhenDeleteRoomIsNotFound() {
    // given
    given(roomRepository.findById(anyLong())).willReturn(Optional.empty());
    // then
    Assertions.assertThatThrownBy(() -> roomService.deleteRoom(1L))
        .isInstanceOf(NoDataFoundException.class)
        .hasMessage(ExceptionConst.ROOM_NOT_FOUND);
  }
}
