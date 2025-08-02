package com.tdesi.sa_sistema_de_biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tdesi.sa_sistema_de_biblioteca.model.Autor;
import com.tdesi.sa_sistema_de_biblioteca.repository.AutorRepository;

@Controller
@RequestMapping
public class AutorController {

    @Autowired
    AutorRepository autorRepository;
    
    @GetMapping("/autor")
    public List<Autor> getAutor(){
        return autorRepository.findAll();
    }
}
