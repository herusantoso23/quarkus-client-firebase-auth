package com.herusantoso.services;

import com.herusantoso.dtos.FirebaseTokenDTO;
import com.herusantoso.dtos.UserCreateDTO;
import com.herusantoso.dtos.ResultPageDTO;
import com.herusantoso.dtos.UserDTO;
import com.herusantoso.entities.User;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDTO createAdmin(FirebaseTokenDTO request);

    UserDTO createCashier(UserCreateDTO request);

    boolean deleteUser(long id);

    ResultPageDTO getAll(Pageable pageable);

    UserDTO getByEmail(String email);

    User findByEmail(String email);

}
