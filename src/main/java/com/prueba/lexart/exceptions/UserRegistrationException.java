package com.prueba.lexart.exceptions;

public class UserRegistrationException extends RuntimeException{
    public UserRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
