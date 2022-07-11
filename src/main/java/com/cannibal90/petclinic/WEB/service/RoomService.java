package com.cannibal90.petclinic.WEB.service;

import com.cannibal90.petclinic.DAL.model.Room;
import com.cannibal90.petclinic.DAL.repository.RoomRepository;
import com.cannibal90.petclinic.WEB.exception.ExceptionConst;
import com.cannibal90.petclinic.WEB.exception.NoDataFoundException;
import com.cannibal90.petclinic.WEB.exception.WrongParameterException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class RoomService {

  private final RoomRepository roomRepository;

  public Room getRoomById(Long id) {
    if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    return roomRepository
        .findById(id)
        .orElseThrow(
            () -> {
              throw new NoDataFoundException(ExceptionConst.ROOM_NOT_FOUND);
            });
  }

    public Page<Room> getAllRooms(Pageable page) {
        return roomRepository.findAll(page);
    }

  public Room createRoom(Room newRoom) {
    if (newRoom == null) {
      throw new WrongParameterException(ExceptionConst.WRONG_ROOM_OBJECT);
    }

    return roomRepository.save(newRoom);
  }

  public Room updateRoom(Long id, Room room) {
    if (room == null) {
      throw new WrongParameterException(ExceptionConst.WRONG_ROOM_OBJECT);
    } else if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    return roomRepository
        .findById(id)
        .map(
            foundRoom -> {
              foundRoom.setFloor(room.getFloor());
              foundRoom.setRoomDescription(room.getRoomDescription());
              return roomRepository.save(foundRoom);
            })
        .orElseThrow(
            () -> {
              throw new NoDataFoundException(ExceptionConst.ROOM_NOT_FOUND);
            });
  }

  public void deleteRoom(Long id) {
    if (id == null || id <= 0) {
      throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
    }

    Room room =
        roomRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  throw new NoDataFoundException(ExceptionConst.ROOM_NOT_FOUND);
                });

    roomRepository.delete(room);
  }
}
