package com.pabloescobar.pruebatecnica.exceptions;

// EmailAlreadyExistsException
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
