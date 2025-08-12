package com.tdesi.sa_sistema_de_biblioteca.controller;

import org.springframework.web.bind.annotation.RestController;

import com.tdesi.sa_sistema_de_biblioteca.model.Emprestimo;
import com.tdesi.sa_sistema_de_biblioteca.model.Livro;
import com.tdesi.sa_sistema_de_biblioteca.model.User;
import com.tdesi.sa_sistema_de_biblioteca.repository.EmprestimoRepository;
import com.tdesi.sa_sistema_de_biblioteca.service.EmprestimoService;
import com.tdesi.sa_sistema_de_biblioteca.service.LivroService;
import com.tdesi.sa_sistema_de_biblioteca.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/loan")
public class EmprestimoController {

    @Autowired
    EmprestimoService emprestimoService;

    @Autowired
    EmprestimoRepository emprestimoRepository;

    @Autowired
    LivroService livroService;

    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<List<Emprestimo>> getEmprestimo() {
        // Retorna status 200 (OK) com a lista no corpo da resposta
        return ResponseEntity.ok().body(emprestimoRepository.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Emprestimo> createEmprestimo(@RequestBody Emprestimo emprestimo) {
        // Recebe o objeto no corpo da requisição e salva via service
        Emprestimo save = emprestimoService.save(emprestimo);
        // Retorna status 201 (Created) com o objeto salvo
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Emprestimo> patchEmprestimo(
            @PathVariable Long id,
            @RequestBody Map<String, Object> camposAtualizados) {
        // Endpoint PATCH para atualizar parcialmente um empréstimo existente
        // Recebe o ID pela URL e um mapa com os campos a serem alterados

        Emprestimo emprestimo = emprestimoService.findById(id);
        // Busca o empréstimo pelo ID; se não existir, retorna 404
        if (emprestimo == null) {
            return ResponseEntity.notFound().build();
        }

        // Itera sobre os campos recebidos e atualiza apenas os informados
        camposAtualizados.forEach((campo, valor) -> {
            switch (campo) {
                case "dataEmprestimo":
                    emprestimo.setDataEmprestimo(Date.valueOf(valor.toString()));
                    break;
                case "dataDevolucaoPrevista":
                    emprestimo.setDataDevolucaoPrevista(Date.valueOf(valor.toString()));
                    break;
                case "dataDevolucao":
                    emprestimo.setDataDevolucao(Date.valueOf(valor.toString()));
                    break;
                case "devolvido":
                    emprestimo.setDevolvido(Boolean.parseBoolean(String.valueOf(valor)));
                    break;
                case "idLivro":
                    Long idLivro = Long.parseLong(valor.toString());
                    Livro livro = livroService.findById(idLivro);
                    emprestimo.setLivro(livro);
                    break;
                case "idUser":
                    Long idUser = Long.parseLong(valor.toString());
                    User user = userService.findByID(idUser);
                    emprestimo.setUser(user);
                    break;
                default:
                    break;
            }
        });

        // Salva as alterações e retorna o empréstimo atualizado com status 200
        Emprestimo emprestimoAtt = emprestimoService.save(emprestimo);
        return ResponseEntity.ok(emprestimoAtt);
    }
}
