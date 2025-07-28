package com.tdesi.sa_sistema_de_biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Autor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
