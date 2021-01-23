package com.herusantoso.services;

import com.herusantoso.dtos.ResultPageDTO;
import com.herusantoso.dtos.UserDTO;
import com.herusantoso.entities.User;
import com.herusantoso.repositories.UserRepository;
import com.herusantoso.services.impl.FirebaseServiceImpl;
import com.herusantoso.services.impl.UserServiceImpl;
import com.herusantoso.utils.PaginationUtil;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.util.Arrays;
import java.util.Optional;

@QuarkusTest
public class UserServiceTest {

    @InjectMock
    UserRepository userRepository;

    @Inject
    UserServiceImpl userService;

    @InjectMock
    FirebaseServiceImpl firebaseService;

    @Test
    public void find_by_email_then_return_user(){
        String email = "email@example.com";
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        User user = userService.findByEmail(email);
        Assertions.assertNotNull(user);
    }

    @Test
    public void find_by_email_then_throw_bad_request(){
        String email = "email@example.com";
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertThrows(BadRequestException.class, () -> userService.findByEmail(email));
    }

    @Test
    public void get_by_email_then_return_user_dto(){
        String email = "email@example.com";

        User user = new User();
        user.setId(1L);
        user.setSecureId("123456789");
        user.setDisplayName("Example");
        user.setEmail(email);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDTO res = userService.getByEmail(email);

        Assertions.assertNotNull(res);
        Assertions.assertEquals(user.getSecureId(), res.getId());
        Assertions.assertEquals(user.getDisplayName(), res.getDisplayName());
        Assertions.assertEquals(user.getEmail(), res.getEmail());
    }

    @Test
    public void get_by_email_then_throw_bad_request(){
        String email = "email@example.com";
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertThrows(BadRequestException.class, () -> userService.getByEmail(email));
    }

    @Test
    public void delete_user_then_return_false(){
        Assertions.assertFalse(userService.deleteUser(1));
    }

    @Test
    public void get_all_then_return_result_page_dto(){
        Pageable pageable = PaginationUtil.getPageRequest("0", "10", "creationDate", "desc");
        Page<User> users = new PageImpl<>(Arrays.asList(new User(), new User()), pageable, 2);

        Mockito.when(userRepository.findAll(pageable)).thenReturn(users);

        ResultPageDTO res = userService.getAll(pageable);
        Assertions.assertEquals(2, res.getData().size());
        Assertions.assertEquals(10, res.getSize());
        Assertions.assertEquals(0, res.getPage());
        Assertions.assertEquals(1, res.getPages());
    }

}
