package com.prueba.lexart.service;

import com.prueba.lexart.domain.dto.CreateUserRequest;
import com.prueba.lexart.domain.dto.CreateUserResponse;
import com.prueba.lexart.domain.dto.UserDTO;
import com.prueba.lexart.domain.entities.User;
import com.prueba.lexart.domain.enums.Role;

import java.util.List;

public interface UserService {
    List<UserDTO> getAll();
    CreateUserResponse createUser(CreateUserRequest request, Role role);
    String authenticateUser(User user, CreateUserRequest request);
    String authenticate(CreateUserRequest request);
}
