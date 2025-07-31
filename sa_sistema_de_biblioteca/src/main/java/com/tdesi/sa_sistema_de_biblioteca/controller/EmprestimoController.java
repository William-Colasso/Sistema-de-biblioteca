package com.tdesi.sa_sistema_de_biblioteca.controller;

import org.springframework.web.bind.annotation.RestController;

import com.tdesi.sa_sistema_de_biblioteca.model.Emprestimo;
import com.tdesi.sa_sistema_de_biblioteca.model.Livro;
import com.tdesi.sa_sistema_de_biblioteca.model.User;
import com.tdesi.sa_sistema_de_biblioteca.repository.EmprestimoRepository;
import com.tdesi.sa_sistema_de_biblioteca.service.EmprestimoService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.time.LocalDate;
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
    
    @PostMapping("/create")
    public ResponseEntity<Emprestimo> postMethodName(@RequestBody Emprestimo emprestimo) {
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
                    emprestimo.setLivro((Livro) valor);
                break;

                case "idUser":
                    emprestimo.setUser((User) valor);
                break;
                
                default:
                break;
            }
        });
        
        Emprestimo att = emprestimoService.save(emprestimo);    
        return ResponseEntity.ok(att);
    }

}
