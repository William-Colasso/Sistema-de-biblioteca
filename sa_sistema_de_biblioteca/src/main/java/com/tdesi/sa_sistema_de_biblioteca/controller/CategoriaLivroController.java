package com.tdesi.sa_sistema_de_biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tdesi.sa_sistema_de_biblioteca.model.CategoriaLivro;
import com.tdesi.sa_sistema_de_biblioteca.repository.CategoriaLivroRepository;

@Controller
@RequestMapping
public class CategoriaLivroController {

    @Autowired
    CategoriaLivroRepository categoriaLivroRepository;
    
    @GetMapping("/categoria")
    public List<CategoriaLivro> getCategoria(){
        return categoriaLivroRepository.findAll();
    }
}
