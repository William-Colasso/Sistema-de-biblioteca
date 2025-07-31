package com.tdesi.sa_sistema_de_biblioteca.exception;

public class LivroIndisponivelException extends RuntimeException {
    public LivroIndisponivelException(String message) {
        super(message);
    }
}