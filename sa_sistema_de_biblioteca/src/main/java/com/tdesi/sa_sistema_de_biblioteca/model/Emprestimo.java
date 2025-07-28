package com.tdesi.sa_sistema_de_biblioteca.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idEmprestimo;
    
    private Date dataEmprestimo;

    private Date dataDevolucaoPrevista;

    private Date dataDevolucao;

    @ManyToOne
    private Livro idLivro;

    @OneToMany
    private User idUser;
}
