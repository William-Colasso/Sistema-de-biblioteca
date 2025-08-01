package com.tdesi.sa_sistema_de_biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tdesi.sa_sistema_de_biblioteca.model.Livro;
import com.tdesi.sa_sistema_de_biblioteca.service.LivroService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/book")
public class LivroController {
    @Autowired
    LivroService livroService;

    @GetMapping("")
    public List<Livro> getLivros() {
        return livroService.getTotalLivros();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Livro>> buscarLivrosComFiltro(
        @RequestParam(required = false) String titulo,
        @RequestParam(required = false) String editora,
        @RequestParam(required = false) Long idAutor,
        @RequestParam(required = false) Long idCategoria
    ) {
        List<Livro> livros = livroService.buscarPorFiltros(titulo, editora, idAutor, idCategoria);
        return ResponseEntity.ok(livros);
    }
    
    @PostMapping("/register")
    public ResponseEntity<Livro> createBook(@RequestBody Livro livro) {
        Livro save = livroService.save(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @GetMapping("/relatorio")
    public String getRelatorio(@RequestParam String titulo) {
        return livroService.relatorio(titulo);
    }
    
}

    

    
    
