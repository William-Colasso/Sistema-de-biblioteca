package com.tdesi.sa_sistema_de_biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdesi.sa_sistema_de_biblioteca.model.Autor;
import com.tdesi.sa_sistema_de_biblioteca.repository.AutorRepository;

@Service
public class AutorService {
    @Autowired
    AutorRepository autorRepository;

    public Autor save(Autor autor){
        return autorRepository.save(autor);
    }

    public List<Autor> findAll(){
        return autorRepository.findAll();
    }
}
