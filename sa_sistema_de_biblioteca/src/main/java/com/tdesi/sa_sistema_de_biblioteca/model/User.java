package com.tdesi.sa_sistema_de_biblioteca.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    
    private Long idUser;

    private String nome;

    private String email;

    private String telefone;

    private String password;

    private boolean isBibliotecario;

}
