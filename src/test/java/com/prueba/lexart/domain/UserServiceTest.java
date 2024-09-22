package com.prueba.lexart.domain;

import com.prueba.lexart.domain.dto.CreateUserRequest;
import com.prueba.lexart.domain.dto.CreateUserResponse;
import com.prueba.lexart.domain.dto.UserDTO;
import com.prueba.lexart.domain.entities.User;
import com.prueba.lexart.domain.enums.Role;
import com.prueba.lexart.domain.mapper.UserMapper;
import com.prueba.lexart.exceptions.EmailAlreadyExistsException;
import com.prueba.lexart.exceptions.InvalidPasswordFormatException;
import com.prueba.lexart.exceptions.ResourceNotFoundException;
import com.prueba.lexart.repository.UserRepository;
import com.prueba.lexart.security.jwt.TokenProvider;
import com.prueba.lexart.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Mock
    private TokenProvider tokenProvider;

    private User USER_1;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        USER_1 = new User("Luis Ruz", "luis.ruz@gmail.com", "Password123!", Role.ADMINISTRADOR);

        assertNotNull(USER_1, "USER_1 should not be null");

        List<User> users = Arrays.asList(USER_1);

        lenient().when(userRepository.findAll()).thenReturn(users);

        userService = new UserServiceImpl(userRepository, passwordEncoder, authenticationManagerBuilder, tokenProvider);
    }
    @Test
    public void readAllTest() throws Exception {
        UserDTO userDTO = UserMapper.convertToDTO(USER_1);
        List<UserDTO> response = userService.getAll();

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(response.size(), 1);
        assertEquals(USER_1.getEmail(), userDTO.email());
    }
    @Test
    public void createUser_Success() {
        ReflectionTestUtils.setField(
                userService, "passwordRegex", "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@#$%^&+=!*]{7,}$");

        CreateUserRequest request =
                new CreateUserRequest("John Doe", "user@example.com", "Password123!", Role.EXTERNO);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(tokenProvider.createToken(any())).thenReturn("token");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        CreateUserResponse response = userService.createUser(request, request.role());

        assertNotNull(response);
        assertEquals("Usuario creado satisfactoriamente.", response.getMessage());
        verify(userRepository).save(any(User.class));
    }
    @Test
    public void createUser_EmailAlreadyExists() {
        CreateUserRequest request =
                new CreateUserRequest("John Doe", "user@example.com", "Password123!", Role.EXTERNO);
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(new User()));

        Exception exception = assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.createUser(request, request.role());
        });

        assertEquals("El correo de user@example.com ya se encuentra registrado", exception.getMessage());
    }
    @Test
    public void createUser_InvalidPasswordFormat() {
        ReflectionTestUtils.setField(
                userService, "passwordRegex", "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
        CreateUserRequest request =
                new CreateUserRequest("John Doe", "user@example.com", "pass", Role.EXTERNO);

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());

        Exception exception = assertThrows(InvalidPasswordFormatException.class, () -> {
            userService.createUser(request, request.role());
        });

        assertEquals("Formato de contraseña es inválido", exception.getMessage());
    }
    @Test
    public void authenticateUser_Success() throws Exception {
        ReflectionTestUtils.setField(
                userService, "passwordRegex", "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@#$%^&+=!*]{7,}$");

        User user = new User();
        user.setEmail("user@example.com");

        CreateUserRequest request =
                new CreateUserRequest("John Doe", "user@example.com", "Password123!", Role.EXTERNO);

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        Authentication authentication = mock(Authentication.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        when(authenticationManagerBuilder.getObject()).thenReturn(authenticationManager);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(tokenProvider.createToken(authentication)).thenReturn("token");

        String result = userService.authenticate(request);

        assertEquals("token", result);
    }
    @Test
    public void authenticateUser_Failure() {

        CreateUserRequest request =
                new CreateUserRequest("John Doe", "user@example.com", "Password123!", Role.EXTERNO);
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.authenticate(request);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
    }
    @Test
    public void testEmailAlreadyExists() {
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(new User()));
        Exception exception = assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.createUser(
                    new CreateUserRequest("John Doe", "user@example.com", "Password123!", Role.EXTERNO),
                    Role.EXTERNO);
        });
        assertEquals("El correo de user@example.com ya se encuentra registrado", exception.getMessage());
    }
    @Test
    public void testInvalidPasswordFormat() {
        ReflectionTestUtils.setField(
                userService, "passwordRegex", "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@#$%^&+=!*]{7,}$");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());
        Exception exception = assertThrows(InvalidPasswordFormatException.class, () -> {
            userService.createUser(
                    new CreateUserRequest("John Doe", "user@example.com", "pass", Role.EXTERNO),
                    Role.EXTERNO);
        });
        assertEquals("Formato de contraseña es inválido", exception.getMessage());
    }
}
