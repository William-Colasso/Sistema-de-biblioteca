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

    @Autowired
    ImageCompressionService imageCompressionService;

    public Autor save(Autor autor){
        autor.setFotoAutor(imageCompressionService.compressBase64(autor.getFotoAutor()));
        return autorRepository.save(autor);
    }

    public List<Autor> findAll(){
        return autorRepository.findAll();
    }
}
