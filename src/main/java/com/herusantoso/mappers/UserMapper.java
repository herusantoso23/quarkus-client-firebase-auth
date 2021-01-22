package com.herusantoso.mappers;

import com.google.firebase.auth.UserRecord;
import com.herusantoso.dtos.UserCreateDTO;
import com.herusantoso.dtos.UserDTO;
import com.herusantoso.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    User toEntity(UserDTO dto, @MappingTarget User entity);

    User toEntity(UserCreateDTO dto, @MappingTarget User entity);

    UserCreateDTO toDTO(UserRecord userRecord, @MappingTarget UserCreateDTO dto);

    @Mapping(source = "secureId", target = "id")
    UserDTO toDTO(User entity, @MappingTarget UserDTO dto);

}
