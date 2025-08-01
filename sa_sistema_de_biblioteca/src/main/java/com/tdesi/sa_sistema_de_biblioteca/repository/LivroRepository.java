package com.tdesi.sa_sistema_de_biblioteca.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tdesi.sa_sistema_de_biblioteca.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>, JpaSpecificationExecutor<Livro> {
    Livro findByTitulo(String titulo);
}