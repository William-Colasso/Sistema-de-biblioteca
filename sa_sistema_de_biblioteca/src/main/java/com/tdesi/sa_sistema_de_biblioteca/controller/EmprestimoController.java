package com.tdesi.sa_sistema_de_biblioteca.controller;

import org.springframework.web.bind.annotation.RestController;

import com.tdesi.sa_sistema_de_biblioteca.model.Emprestimo;
import com.tdesi.sa_sistema_de_biblioteca.model.Livro;
import com.tdesi.sa_sistema_de_biblioteca.model.User;
import com.tdesi.sa_sistema_de_biblioteca.service.EmprestimoService;
import com.tdesi.sa_sistema_de_biblioteca.service.LivroService;
import com.tdesi.sa_sistema_de_biblioteca.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    LivroService livroService;

    @Autowired
    UserService userService;
    
    @PostMapping("/create")
    public ResponseEntity<Emprestimo> createEmprestimo(@RequestBody Emprestimo emprestimo) {
        Emprestimo save = emprestimoService.save(emprestimo);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }
    
    @PatchMapping("/update/{id}")
    public ResponseEntity<Emprestimo> patchEmprestimo(
        @PathVariable Long id,
        @RequestBody Map<String, Object> camposAtualizados) {

        Emprestimo emprestimo = emprestimoService.findById(id);

        if (emprestimo == null) {
            return ResponseEntity.notFound().build();
        }
        
        camposAtualizados.forEach((campo, valor)->{
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
        
        Emprestimo emprestimoAtt = emprestimoService.save(emprestimo);    
        return ResponseEntity.ok(emprestimoAtt);
    }

}
