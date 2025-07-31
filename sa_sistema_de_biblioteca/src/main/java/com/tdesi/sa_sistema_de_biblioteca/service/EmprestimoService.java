package com.tdesi.sa_sistema_de_biblioteca.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdesi.sa_sistema_de_biblioteca.exception.LivroIndisponivelException;
import com.tdesi.sa_sistema_de_biblioteca.model.Emprestimo;
import com.tdesi.sa_sistema_de_biblioteca.repository.EmprestimoRepository;

@Service
public class EmprestimoService {
    @Autowired
    EmprestimoRepository emprestimoRepository;

    @Autowired
    LivroService livroService;

    public Emprestimo save(Emprestimo emprestimo) {
        int qtdLivros = livroService.quantidadeDisponivel(emprestimo.getLivro());

        if (qtdLivros < 1) {
            throw new LivroIndisponivelException("Não há exemplares disponíveis para empréstimo.");
        }

        return emprestimoRepository.save(emprestimo);
    }

    public Emprestimo findById(Long id) {
        return emprestimoRepository.findById(id).get();
    }
}
