package com.herusantoso.services.impl;

import com.herusantoso.repositories.RoleRepository;
import com.herusantoso.dtos.RoleDTO;
import com.herusantoso.entities.Role;
import com.herusantoso.enums.RoleEnum;
import com.herusantoso.mappers.RoleMapper;
import com.herusantoso.services.RoleService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

@ApplicationScoped
public class RoleServiceImpl implements RoleService {

    @Inject
    RoleRepository roleRepository;

    @Inject
    RoleMapper roleMapper;

    @Override
    public Role getRole(RoleEnum name) {
        return roleRepository.findByName(name)
                .orElseThrow(()-> new BadRequestException("Role not found"));
    }

    @Override
    public RoleDTO createOrUpdate(RoleEnum name){
        Role role = roleRepository.findByName(name).orElse(new Role());
        role.setName(name);
        Role roleSaved = roleRepository.save(role);
        return roleMapper.toDTO(roleSaved, new RoleDTO());
    }
}
