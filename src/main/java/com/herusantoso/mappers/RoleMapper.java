package com.herusantoso.mappers;

import com.herusantoso.dtos.RoleDTO;
import com.herusantoso.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface RoleMapper {

    Role toEntity(RoleDTO dto, @MappingTarget Role entity);

    @Mapping(source = "secureId", target = "id")
    RoleDTO toDTO(Role entity, @MappingTarget RoleDTO dto);

}
