package com.prueba.lexart.exceptions;

public class InvalidPasswordFormatException extends RuntimeException{
    public InvalidPasswordFormatException(String message) {
        super(message);
    }
}