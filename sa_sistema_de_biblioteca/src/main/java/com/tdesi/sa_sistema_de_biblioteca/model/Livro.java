package com.tdesi.sa_sistema_de_biblioteca.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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

    @Enumerated(EnumType.STRING)
    private EditoraLivro editora;

    private Date anoPublicacao;

    private String sinopse;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imagemCapa;

    @Enumerated(EnumType.STRING)
    private CategoriaLivro categoriaLivro;

    @ManyToOne
    @JoinColumn(name = "idAutor")
    private Autor autor;

    private int quantidadeTotal;
}
