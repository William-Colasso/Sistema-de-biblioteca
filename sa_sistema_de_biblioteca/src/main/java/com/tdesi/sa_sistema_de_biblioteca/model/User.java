package com.tdesi.sa_sistema_de_biblioteca.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.tdesi.sa_sistema_de_biblioteca.service.UserService;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    private String nome;

    private String email;

    private String telefone;

    private String password;

    private boolean isBibliotecario;

}
