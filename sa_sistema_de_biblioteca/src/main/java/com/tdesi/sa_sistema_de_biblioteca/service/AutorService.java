package com.tdesi.sa_sistema_de_biblioteca.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdesi.sa_sistema_de_biblioteca.model.Autor;
import com.tdesi.sa_sistema_de_biblioteca.repository.AutorRepository;

@Service
public class AutorService  {

    private final AutorRepository autorRepository;

    @Autowired
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor save(Autor autor) throws IOException{
        return autorRepository.save(autor);
    }

    public List<Autor> findAll() throws IOException{
        return autorRepository.findAll();
    }
}
