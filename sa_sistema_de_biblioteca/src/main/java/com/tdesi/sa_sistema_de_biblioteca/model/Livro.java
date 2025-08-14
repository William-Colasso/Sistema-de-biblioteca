package com.tdesi.sa_sistema_de_biblioteca.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Livro {
    
    private Long idLivro;

    private String titulo;

    private EditoraLivro editora;

    private Date anoPublicacao;

    private String sinopse;

    private String imagemCapa;

    private CategoriaLivro categoriaLivro;

    private Autor autor;

    private int quantidadeTotal;
}
