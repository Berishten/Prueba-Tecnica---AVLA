package com.pabloescobar.pruebatecnica.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.pabloescobar.pruebatecnica.dto.UserDTO;
import com.pabloescobar.pruebatecnica.dto.UserResponseDTO;
import com.pabloescobar.pruebatecnica.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // maper de userdto a user
    @Mapping(target = "phones", ignore = true)
    User userDtoToUser(UserDTO userDto);

    // maper de user a userdto
    @Mapping(target = "phones", ignore = true)
    UserDTO userToUserDto(User user);

    // touserresponse
    @Mapping(target = "phones", ignore = true)
    UserResponseDTO userToUserResponseDTO(User user);

}