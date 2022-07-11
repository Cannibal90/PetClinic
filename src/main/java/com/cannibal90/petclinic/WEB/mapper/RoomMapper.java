package com.cannibal90.petclinic.WEB.mapper;

import com.cannibal90.petclinic.DAL.model.Room;
import com.cannibal90.petclinic.WEB.dto.PageDTO;
import com.cannibal90.petclinic.WEB.dto.RoomDTO;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoomMapper {
  RoomDTO roomToRoomDTO(Room room);

  Room roomDTOToRoom(RoomDTO roomDTO);

  default PageDTO roomPageToPageDTO(Page<Room> rooms) {
    if (rooms == null) {
      return null;
    }

    PageDTO pageDTO = new PageDTO();
    pageDTO.setTotalPages(rooms.getTotalPages());
    pageDTO.setTotalItems(rooms.getTotalElements());
    pageDTO.setCurrentPage(rooms.getNumber());
    pageDTO.setItems(rooms.get().map(this::roomToRoomDTO).collect(Collectors.toList()));

    return pageDTO;
  }
}
