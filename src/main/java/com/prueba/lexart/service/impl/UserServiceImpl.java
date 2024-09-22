package com.prueba.lexart.service.impl;

import com.prueba.lexart.domain.dto.CreateUserRequest;
import com.prueba.lexart.domain.dto.CreateUserResponse;
import com.prueba.lexart.domain.dto.UserDTO;
import com.prueba.lexart.domain.entities.User;
import com.prueba.lexart.domain.enums.Role;
import com.prueba.lexart.domain.mapper.UserMapper;
import com.prueba.lexart.exceptions.EmailAlreadyExistsException;
import com.prueba.lexart.exceptions.InvalidPasswordFormatException;
import com.prueba.lexart.exceptions.ResourceNotFoundException;
import com.prueba.lexart.exceptions.UserRegistrationException;
import com.prueba.lexart.repository.UserRepository;
import com.prueba.lexart.security.jwt.TokenProvider;
import com.prueba.lexart.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Value("${app.regex.password}")
    private String passwordRegex;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManagerBuilder authenticationManagerBuilder,
            TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public List<UserDTO> getAll() {
        log.info("consulta de usuarios iniciada");
        return userRepository.findAll().stream().map(UserMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public CreateUserResponse createUser(CreateUserRequest user, Role role) {
        try {
            validateEmail(user.email());
            validatePassword(user.password());
            String token = tokenProvider.createToken(
                    new UsernamePasswordAuthenticationToken(user.email(), Collections.singletonList(role)));
            User newUser = UserMapper.convertToEntity(user);
            newUser.setPassword(passwordEncoder.encode(user.password()));
            newUser.setToken(token);
            newUser.setRole(role);
            User savedUser = userRepository.save(newUser);
            log.info("Creación de Usuario con id={}", savedUser.getId());
            return buildSuccessResponse(savedUser);
        } catch (EmailAlreadyExistsException | InvalidPasswordFormatException ex) {
            log.error("Validación de Error: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Error inesperado durante el registro de usuario", ex);
            throw new UserRegistrationException("Error al registrar al usuario", ex);
        }
    }
    @Override
    public String authenticate(CreateUserRequest request) {
        User user = userRepository
                .findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return authenticateUser(user, request);
    }


    @Override
    public String authenticateUser(User user, CreateUserRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getEmail(), request.password());
        try {
            Authentication authentication =
                    authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return tokenProvider.createToken(authentication);
        } catch (AuthenticationException e) {
            log.error("Error de autenticación para el usuario: {}", request.email(), e);
            throw new AccessDeniedException("Error de autenticación", e);
        }
    }
    private CreateUserResponse<UserDTO> buildSuccessResponse(User user) {
        UserDTO userDTO = UserMapper.convertToDTO(user);
        CreateUserResponse<UserDTO> response = new CreateUserResponse<>();
        response.setBody(userDTO);
        response.setMessage("Usuario creado satisfactoriamente.");
        return response;
    }
    private void validateEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("El correo de " + email + " ya se encuentra registrado");
        }
    }

    private void validatePassword(String password) {
        if (!password.matches(passwordRegex)) {
            throw new InvalidPasswordFormatException("Formato de contraseña es inválido");
        }
    }
}
