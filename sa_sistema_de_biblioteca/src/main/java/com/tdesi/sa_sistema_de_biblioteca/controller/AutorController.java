package com.tdesi.sa_sistema_de_biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tdesi.sa_sistema_de_biblioteca.model.Autor;
import com.tdesi.sa_sistema_de_biblioteca.repository.AutorRepository;

@Controller
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    AutorRepository autorRepository;
    
    @GetMapping("/all")
    public List<Autor> getAutor(){
        return autorRepository.findAll();
    }


    @GetMapping("/register")
    public String getRegister() {
        return "cadastroAutor";
    }
}
