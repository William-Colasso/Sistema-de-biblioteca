package com.tdesi.sa_sistema_de_biblioteca.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Emprestimo {
    private Long idEmprestimo;
    
    private Date dataEmprestimo;

    private Date dataDevolucaoPrevista;

    private Date dataDevolucao;

    private boolean devolvido;

    private Livro livro;

    private User user;
}
