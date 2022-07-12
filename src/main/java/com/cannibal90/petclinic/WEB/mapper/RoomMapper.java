package com.cannibal90.petclinic.WEB.mapper;

import com.cannibal90.petclinic.DAL.model.Medicament;
import com.cannibal90.petclinic.DAL.model.Room;
import com.cannibal90.petclinic.WEB.dto.PageDTO;
import com.cannibal90.petclinic.WEB.dto.RoomDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoomMapper {
  RoomDTO roomToRoomDTO(Room room);

  Room roomDTOToRoom(RoomDTO roomDTO);

  @Named("pageToList")
  default List<Object> pageToList(Page<Room> page) {
    return page.get().map(this::roomToRoomDTO).collect(Collectors.toList());
  }

  @Mapping(source = "page", target = "items", qualifiedByName = "pageToList")
  PageDTO roomPageToPageDTO(Page<Room> page);
}
