package com.tdesi.sa_sistema_de_biblioteca.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tdesi.sa_sistema_de_biblioteca.model.EditoraLivro;
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
    public List<Livro> getLivros() throws IOException {
        // Retorna diretamente a lista obtida no service
        return livroService.getTotalLivros();
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<Livro>> buscarLivrosComFiltro(
        @RequestParam(required = false) String titulo,
        @RequestParam(required = false) String editora,
        @RequestParam(required = false) Date dataPublicacao,
        @RequestParam(required = false) Long idAutor,
        @RequestParam(required = false) String categoria,
        @RequestParam(required = false) String sinopse,
        @RequestParam(required = false) Integer quantidadeTotal
    ) throws IOException {
        // Endpoint GET para busca filtrada
        // Chama o service para filtrar e retorna 200 (OK) com a lista
        List<Livro> livros = livroService.buscarPorFiltros(
            titulo, editora, dataPublicacao, idAutor, categoria, sinopse, quantidadeTotal
        );
        return ResponseEntity.ok(livros);
    }
    
    @PostMapping("/register")
    public ResponseEntity<Livro> createBook(@RequestBody Livro livro) throws IOException {
        // Recebe o objeto no corpo da requisição e salva via service
        Livro livroSalvo = livroService.save(livro);
        // Retorna 201 (Created) com o livro salvo
        return ResponseEntity.status(HttpStatus.CREATED).body(livroSalvo);
    }
    
    @GetMapping("/relatorio")
    public String getRelatorio(@RequestParam String titulo) throws IOException {
        // Recebe o título como parâmetro obrigatório
        return livroService.relatorio(titulo);
    }
    
    @GetMapping("/editoras")
    public EditoraLivro[] getEditora() {
        // O retorno é um array de valores do enum EditoraLivro
        return EditoraLivro.values();
    }

}
