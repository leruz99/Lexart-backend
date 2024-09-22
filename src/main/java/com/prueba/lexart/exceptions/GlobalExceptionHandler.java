package com.prueba.lexart.exceptions;

import com.prueba.lexart.domain.dto.CreateProductResponse;
import com.prueba.lexart.domain.dto.CreateUserResponse;
import com.prueba.lexart.domain.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {EmailAlreadyExistsException.class})
    public ResponseEntity<CreateUserResponse<Object>> handleEmailAlreadyExistsException(
            EmailAlreadyExistsException ex) {
        return new ResponseEntity<>(buildResponseException(ex), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {InvalidPasswordFormatException.class})
    public ResponseEntity<CreateUserResponse<Object>> handleInvalidPasswordFormatException(
            InvalidPasswordFormatException ex) {
        return new ResponseEntity<>(buildResponseValidException(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<CreateUserResponse<Object>> handleException(Exception ex) {
        return new ResponseEntity<>(buildResponseException(ex, "Algo salió mal"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {UserRetrievalException.class})
    public ResponseEntity<CreateUserResponse<Object>> handleUserRetrieveException(Exception ex) {
        return new ResponseEntity<>(
                buildResponseException(ex, "Error al obtener todos los usuarios"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CreateUserResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(buildResponseValidException(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<CreateUserResponse<Object>> handleHttpServerErrorException(HttpServerErrorException ex) {
        return new ResponseEntity<>(buildResponseException(ex, ex.getMessage()), ex.getStatusCode());
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<CreateUserResponse<Object>> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(buildResponseException(ex, "Acceso denegado"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CreateUserResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(buildResponseException(ex), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<CreateProductResponse<Object>> handleProductNotFoundException(ProductNotFoundException ex) {
        return new ResponseEntity<>(buildResponseException(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<CreateUserResponse<Object>> handleResourceNotFoundException(UserRegistrationException ex) {
        return new ResponseEntity<>(buildResponseException(ex), HttpStatus.FORBIDDEN);
    }

    private CreateUserResponse<Object> buildResponseException(Throwable throwable, String message) {
        CreateUserResponse<Object> response = new CreateUserResponse<>();
        ApiError error = new ApiError();
        error.setCauseMessage(throwable.getMessage());
        error.setMessage(message);
        response.setError(error);
        response.setMessage(message);
        return response;
    }

    private CreateUserResponse<Object> buildResponseValidException(MethodArgumentNotValidException ex) {
        CreateUserResponse<Object> response = new CreateUserResponse<>();
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        response.setMessage(errors);
        return response;
    }

    private CreateUserResponse<Object> buildResponseValidException(String message) {
        CreateUserResponse<Object> response = new CreateUserResponse<>();
        response.setMessage(message);
        return response;
    }

    private CreateUserResponse<Object> buildResponseException(EmailAlreadyExistsException ex) {
        CreateUserResponse<Object> response = new CreateUserResponse<>();
        response.setMessage(ex.getMessage());
        return response;
    }

    private CreateUserResponse<Object> buildResponseException(ResourceNotFoundException ex) {
        CreateUserResponse<Object> response = new CreateUserResponse<>();
        response.setMessage(ex.getMessage());
        return response;
    }
    private CreateProductResponse<Object> buildResponseException(ProductNotFoundException ex) {
        CreateProductResponse<Object> response = new CreateProductResponse<>();
        response.setMessage(ex.getMessage());
        return response;
    }

    private CreateUserResponse<Object> buildResponseException(UserRegistrationException ex) {
        CreateUserResponse<Object> response = new CreateUserResponse<>();
        response.setMessage(ex.getMessage());
        return response;
    }

}
