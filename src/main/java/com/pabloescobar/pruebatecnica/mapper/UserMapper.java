package com.pabloescobar.pruebatecnica.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.pabloescobar.pruebatecnica.dto.UserDTO;
import com.pabloescobar.pruebatecnica.dto.CreateUpdateUserResponseDTO;
import com.pabloescobar.pruebatecnica.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "created", source = "created", dateFormat = "dd-MM-yyyy HH:mm:ss")
    CreateUpdateUserResponseDTO userToUserResponseDTO(User user);

    User userDTOToUser(UserDTO userDTO);

    @Mapping(target = "created", source = "created", dateFormat = "dd-MM-yyyy HH:mm:ss")
    CreateUpdateUserResponseDTO userToCreateUpdateUserResponseDTO(User user);

    // updateUserFromUserDTO
    void updateUserFromUserDTO(UserDTO userDTO, @MappingTarget User user);

}