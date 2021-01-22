package com.herusantoso.services.impl;

import com.herusantoso.repositories.UserRepository;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.herusantoso.dtos.FirebaseTokenDTO;
import com.herusantoso.dtos.UserCreateDTO;
import com.herusantoso.dtos.ResultPageDTO;
import com.herusantoso.dtos.UserDTO;
import com.herusantoso.entities.Role;
import com.herusantoso.entities.User;
import com.herusantoso.enums.RoleEnum;
import com.herusantoso.mappers.UserMapper;
import com.herusantoso.services.FirebaseService;
import com.herusantoso.services.RoleService;
import com.herusantoso.services.UserService;
import com.herusantoso.utils.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    FirebaseService firebaseService;

    @Inject
    RoleService roleService;

    @Inject
    UserMapper userMapper;

    @Transactional
    @Override
    public UserDTO createAdmin(FirebaseTokenDTO request) {
        UserRecord userRecord = firebaseService.getUserFromTokenId(request.getTokenId());
        UserCreateDTO createRequest = userMapper.toDTO(userRecord, new UserCreateDTO());
        User user = createUser(createRequest, RoleEnum.ADMIN);
        return userMapper.toDTO(user, new UserDTO());
    }

    @Transactional
    @Override
    public UserDTO createCashier(UserCreateDTO request) {
        try {
            User user = createUser(request, RoleEnum.CASHIER);

            UserRecord.CreateRequest requestFirebase = new UserRecord.CreateRequest();
            requestFirebase.setUid(UUID.randomUUID().toString());
            requestFirebase.setDisplayName(request.getDisplayName());
            requestFirebase.setDisabled(false);
            requestFirebase.setEmail(request.getEmail());
            requestFirebase.setPassword(request.getPassword());
            requestFirebase.setPhoneNumber(request.getPhoneNumber());
            requestFirebase.setPhotoUrl(request.getPhotoUrl());
            firebaseService.createUserWithEmail(requestFirebase);
            return userMapper.toDTO(user, new UserDTO());
        } catch (FirebaseAuthException e){
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        } catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    private User createUser(UserCreateDTO request, RoleEnum role){
        Optional<User> userFound = userRepository.findByEmail(request.getEmail());
        if(userFound.isPresent()) throw new BadRequestException("Email already registered");

        User user = userMapper.toEntity(request, new User());
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRole(role));
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(long id) {
        return false;
    }

    @Override
    public ResultPageDTO getAll(Pageable pageable) {
        Page<User> resultPage = userRepository.findAll(pageable);
        Collection<UserDTO> dtos = resultPage.getContent().stream()
                .map(user -> userMapper.toDTO(user, new UserDTO()))
                .collect(Collectors.toCollection(LinkedList::new));
        return PaginationUtil.map(dtos, resultPage);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new BadRequestException("Not found"));
    }

    @Override
    public UserDTO getByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new BadRequestException("Not found"));
        return userMapper.toDTO(user, new UserDTO());
    }
}
