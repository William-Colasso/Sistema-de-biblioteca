package com.tdesi.sa_sistema_de_biblioteca.repository;

import java.io.IOException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tdesi.sa_sistema_de_biblioteca.model.Autor;
import com.tdesi.sa_sistema_de_biblioteca.model.User;

@Repository
public class AutorRepository extends ObjectFileRepository<Long, Autor> {

    public AutorRepository(Class<Autor> typeClass, String idFieldName) throws IOException {
        super(typeClass, "idAutor");
        
    }
    
}
