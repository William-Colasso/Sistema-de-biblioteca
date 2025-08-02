package com.tdesi.sa_sistema_de_biblioteca.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tdesi.sa_sistema_de_biblioteca.model.CategoriaLivro;

@RestController
@RequestMapping
public class CategoriaLivroController {
    
    @GetMapping("/categoria")
    public CategoriaLivro[] getCategoria(){
        return CategoriaLivro.values();
    }
}
