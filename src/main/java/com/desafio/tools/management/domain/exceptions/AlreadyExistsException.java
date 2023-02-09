package com.desafio.tools.management.domain.exceptions;

public class AlreadyExistsException extends Exception{
    public AlreadyExistsException(String message) {
        super(message);
    }
}
