package com.tdesi.sa_sistema_de_biblioteca.repository;

import java.beans.JavaBean;
import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.tdesi.sa_sistema_de_biblioteca.model.Autor;
import com.tdesi.sa_sistema_de_biblioteca.model.User;

@Repository
public class AutorRepository extends ObjectFileRepository<Long, Autor> {

    // construtor padrão usado pelo Spring
    public AutorRepository() {
        super(Autor.class, "idAutor");
    }

    // Se quiser um construtor auxiliar para testes, mantenha-o mas sem throws (ou transforme exceção em runtime)
}