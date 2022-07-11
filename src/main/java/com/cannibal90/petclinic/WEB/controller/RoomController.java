package com.cannibal90.petclinic.WEB.controller;

import com.cannibal90.petclinic.DAL.model.Room;
import com.cannibal90.petclinic.WEB.dto.PageDTO;
import com.cannibal90.petclinic.WEB.dto.RoomDTO;
import com.cannibal90.petclinic.WEB.mapper.RoomMapper;
import com.cannibal90.petclinic.WEB.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {

  private final RoomService roomService;
  private final RoomMapper roomMapper;

  @GetMapping("/{id}")
  public ResponseEntity<?> getRoomById(@PathVariable(name = "id") Long id) {
    RoomDTO roomDTO = roomMapper.roomToRoomDTO(roomService.getRoomById(id));
    return new ResponseEntity<>(roomDTO, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<?> getAllRooms(Pageable page) {
    PageDTO pageDTO = roomMapper.roomPageToPageDTO(roomService.getAllRooms(page));

    return new ResponseEntity<>(pageDTO, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> createRoom(@Valid @RequestBody RoomDTO roomDTO) {
    Room createdRoom = roomService.createRoom(roomMapper.roomDTOToRoom(roomDTO));
    RoomDTO createdRoomDTO = roomMapper.roomToRoomDTO(createdRoom);
    return new ResponseEntity<>(createdRoomDTO, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateRoom(
      @PathVariable(name = "id") Long id, @Valid @RequestBody RoomDTO roomDTO) {
    Room updatedRoom = roomService.updateRoom(id, roomMapper.roomDTOToRoom(roomDTO));
    RoomDTO updatedRoomDTO = roomMapper.roomToRoomDTO(updatedRoom);
    return new ResponseEntity<>(updatedRoomDTO, HttpStatus.OK);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deleteRoom(@PathVariable(name = "id") Long id) {
    roomService.deleteRoom(id);
    return ResponseEntity.noContent().build();
  }
}
