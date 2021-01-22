package com.herusantoso.services;

import com.herusantoso.dtos.RoleDTO;
import com.herusantoso.entities.Role;
import com.herusantoso.enums.RoleEnum;
import com.herusantoso.mappers.RoleMapper;
import com.herusantoso.repositories.RoleRepository;
import com.herusantoso.services.impl.RoleServiceImpl;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.util.Optional;

@QuarkusTest
public class RoleServiceTest {

    @InjectMock
    RoleRepository roleRepository;

    @Inject
    RoleServiceImpl roleService;

    @InjectMock
    RoleMapper roleMapper;

    @Test
    public void GET_ROLE_THEN_RETURN_ROLE(){
        Mockito.when(roleRepository.findByName(RoleEnum.ADMIN))
                .thenReturn(Optional.of(new Role()));

        Role role = roleService.getRole(RoleEnum.ADMIN);
        Assertions.assertNotNull(role);
    }

    @Test
    public void GET_ROLE_THEN_RETURN_THROW_NOT_FOUND(){
        Mockito.when(roleRepository.findByName(RoleEnum.ADMIN))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(BadRequestException.class, () -> roleService.getRole(RoleEnum.ADMIN));
    }

    @Test
    public void CREATE_ROLE_THEN_RETURN_ROLE_DTO(){
        Role role = new Role();
        role.setName(RoleEnum.ADMIN);

        Role mock = new Role();
        mock.setId(1L);
        mock.setName(RoleEnum.ADMIN);

        RoleDTO mockDto = new RoleDTO();
        mockDto.setId("1234567890");
        mockDto.setName(RoleEnum.ADMIN.toString());

        Mockito.when(roleRepository.findByName(RoleEnum.ADMIN))
                .thenReturn(Optional.of(new Role()));

        Mockito.when(roleRepository.save(role)).thenReturn(mock);

        Mockito.when(roleMapper.toDTO(mock, new RoleDTO())).thenReturn(mockDto);

        RoleDTO result = roleService.createOrUpdate(RoleEnum.ADMIN);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), mockDto.getId());
        Assertions.assertEquals(result.getName(), mockDto.getName());
    }

    @Test
    public void UPDATE_ROLE_THEN_RETURN_ROLE_DTO(){
        Role role = new Role();
        role.setName(RoleEnum.ADMIN);

        Role mock = new Role();
        mock.setId(1L);
        mock.setName(RoleEnum.ADMIN);

        RoleDTO mockDto = new RoleDTO();
        mockDto.setId("1234567890");
        mockDto.setName(RoleEnum.ADMIN.toString());

        Mockito.when(roleRepository.findByName(RoleEnum.ADMIN))
                .thenReturn(Optional.of(mock));

        Mockito.when(roleRepository.save(role)).thenReturn(mock);

        Mockito.when(roleMapper.toDTO(mock, new RoleDTO())).thenReturn(mockDto);

        RoleDTO result = roleService.createOrUpdate(RoleEnum.ADMIN);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), mockDto.getId());
        Assertions.assertEquals(result.getName(), mockDto.getName());
    }

}
