package com.tdesi.sa_sistema_de_biblioteca.repository;


import java.io.IOException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tdesi.sa_sistema_de_biblioteca.model.Livro;
import com.tdesi.sa_sistema_de_biblioteca.model.User;

@Repository
public class LivroRepository extends ObjectFileRepository<Long, Livro>  {

    public LivroRepository(Class<Livro> typeClass, String idFieldName) throws IOException {
        super(typeClass, "idLivro");
        
    }
    
}