package com.tdesi.sa_sistema_de_biblioteca.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("O e-mail '" + email + "' já está em uso.");
    }
}
