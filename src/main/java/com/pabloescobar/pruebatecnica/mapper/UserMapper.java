package com.pabloescobar.pruebatecnica.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.pabloescobar.pruebatecnica.dto.UserResponseDTO;
import com.pabloescobar.pruebatecnica.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // touserresponse
    @Mapping(target = "created", source = "created", dateFormat = "dd-MM-yyyy HH:mm:ss")
    UserResponseDTO userToUserResponseDTO(User user);

}