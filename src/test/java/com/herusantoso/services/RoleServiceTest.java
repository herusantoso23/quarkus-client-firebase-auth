package com.herusantoso.services;

import com.herusantoso.dtos.RoleDTO;
import com.herusantoso.entities.Role;
import com.herusantoso.enums.RoleEnum;
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

    @Test
    public void get_role_then_return_role(){
        Mockito.when(roleRepository.findByName(RoleEnum.ADMIN))
                .thenReturn(Optional.of(new Role()));

        Role role = roleService.getRole(RoleEnum.ADMIN);
        Assertions.assertNotNull(role);
    }

    @Test
    public void get_role_then_throw_bad_request(){
        Mockito.when(roleRepository.findByName(RoleEnum.ADMIN))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(BadRequestException.class, () -> roleService.getRole(RoleEnum.ADMIN));
    }

    @Test
    public void create_role_then_return_role_dto(){
        Role role = new Role();
        role.setName(RoleEnum.ADMIN);

        Role mock = new Role();
        mock.setId(1L);
        mock.setSecureId("123456789");
        mock.setName(RoleEnum.ADMIN);

        Mockito.when(roleRepository.findByName(RoleEnum.ADMIN))
                .thenReturn(Optional.of(new Role()));

        Mockito.when(roleRepository.save(role)).thenReturn(mock);

        RoleDTO result = roleService.createOrUpdate(RoleEnum.ADMIN);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(mock.getSecureId(), result.getId());
        Assertions.assertEquals(mock.getName().toString(), result.getName());
    }

    @Test
    public void update_role_then_return_role_dto(){
        Role mock = new Role();
        mock.setId(1L);
        mock.setSecureId("1234567890");
        mock.setName(RoleEnum.ADMIN);

        RoleDTO mockDto = new RoleDTO();
        mockDto.setName(RoleEnum.ADMIN.toString());

        Mockito.when(roleRepository.findByName(RoleEnum.ADMIN))
                .thenReturn(Optional.of(mock));

        Mockito.when(roleRepository.save(mock)).thenReturn(mock);

        RoleDTO result = roleService.createOrUpdate(RoleEnum.ADMIN);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(mock.getSecureId(), result.getId());
        Assertions.assertEquals(mock.getName().toString(), result.getName());
    }

}
