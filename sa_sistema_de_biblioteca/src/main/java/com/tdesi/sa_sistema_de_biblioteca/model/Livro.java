package com.tdesi.sa_sistema_de_biblioteca.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Livro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLivro;

    private String titulo;

    private String editora;

    private Date anoPublicacao;

    private String sinopse;

    private String imagemCapa;

    private boolean disponivel;

    private CategoriaLivro categoriaLivro;

    @ManyToOne
    @JoinColumn(name = "idAutor")
    private Autor autor;

    private int quantidadeTotal;
}
