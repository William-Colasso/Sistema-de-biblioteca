package com.tdesi.sa_sistema_de_biblioteca.repository;


import org.springframework.stereotype.Repository;

import com.tdesi.sa_sistema_de_biblioteca.model.Autor;

@Repository
public class AutorRepository extends ObjectFileRepository<Long, Autor> {

    public AutorRepository() {
        super(Autor.class, "idAutor");
    }

}