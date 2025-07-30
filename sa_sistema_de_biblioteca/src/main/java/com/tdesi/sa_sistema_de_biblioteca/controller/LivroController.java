package com.tdesi.sa_sistema_de_biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tdesi.sa_sistema_de_biblioteca.model.Livro;
import com.tdesi.sa_sistema_de_biblioteca.repository.LivroRepository;


@Controller
@RequestMapping("/book")
public class LivroController {
    @Autowired
    LivroRepository livroRepository;

    @GetMapping("")
    public String get() {
        return "book";
    }
    @PostMapping("/register")
    public String createBook(@RequestBody Livro livro) {
        livroRepository.save(livro);
        return "#";
    }
}

    

    
    
