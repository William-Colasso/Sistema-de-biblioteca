package com.tdesi.sa_sistema_de_biblioteca.exception;

public class EmailNotProvidedException extends RuntimeException {
    public EmailNotProvidedException() {
        super("O e-mail deve ser fornecido.");
    }
}