package com.herusantoso.services;

import com.herusantoso.dtos.RoleDTO;
import com.herusantoso.entities.Role;
import com.herusantoso.enums.RoleEnum;

public interface RoleService {

    RoleDTO createOrUpdate(RoleEnum name);

    Role getRole(RoleEnum name);

}
