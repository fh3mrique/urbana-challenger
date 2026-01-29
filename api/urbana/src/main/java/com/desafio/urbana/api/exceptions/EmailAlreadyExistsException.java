package com.desafio.urbana.api.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("Email já registrado: " + email);
    }
}
