package com.tdesi.sa_sistema_de_biblioteca.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdesi.sa_sistema_de_biblioteca.exception.LivroIndisponivelException;
import com.tdesi.sa_sistema_de_biblioteca.model.Emprestimo;
import com.tdesi.sa_sistema_de_biblioteca.model.Livro;
import com.tdesi.sa_sistema_de_biblioteca.model.User;
import com.tdesi.sa_sistema_de_biblioteca.repository.EmprestimoRepository;
import com.tdesi.sa_sistema_de_biblioteca.repository.LivroRepository;
import com.tdesi.sa_sistema_de_biblioteca.repository.UserRepository;

@Service
public class EmprestimoService {
    @Autowired
    EmprestimoRepository emprestimoRepository;

    @Autowired
    LivroService livroService;

    @Autowired
    LivroRepository livroRepository;

    @Autowired 
    UserRepository userRepository;


    public Emprestimo save(Emprestimo emprestimo) {
        Livro livro = livroRepository.findById(emprestimo.getLivro().getIdLivro()).get();
        User user = userRepository.findById(emprestimo.getUser().getIdUser()).get();
        System.out.println(emprestimo.getLivro().getIdLivro());
        emprestimo.setLivro(livro);
        emprestimo.setUser(user);
        int qtdLivros = livroService.quantidadeDisponivel(livro);

        if (qtdLivros < 1) {
            throw new LivroIndisponivelException("Não há exemplares disponíveis para empréstimo.");
        }

        return emprestimoRepository.save(emprestimo);
    }

    public Emprestimo findById(Long id) {
        if (!emprestimoRepository.existsById(id)) {
            throw new RuntimeException("Emprestimo não encontrado.");
        }
        return emprestimoRepository.findById(id).get();
    }
}
