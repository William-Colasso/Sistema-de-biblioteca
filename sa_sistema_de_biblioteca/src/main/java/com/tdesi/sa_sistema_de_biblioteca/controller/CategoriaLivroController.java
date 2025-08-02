package com.tdesi.sa_sistema_de_biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tdesi.sa_sistema_de_biblioteca.model.CategoriaLivro;

@Controller
@RequestMapping
public class CategoriaLivroController {
    
    @GetMapping("/categoria")
    public CategoriaLivro[] getCategoria(){
        return CategoriaLivro.values();
    }
}
