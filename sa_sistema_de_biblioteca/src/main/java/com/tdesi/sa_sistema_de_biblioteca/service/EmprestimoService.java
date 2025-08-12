package com.tdesi.sa_sistema_de_biblioteca.service;

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
        // Busca Livro e User do emprestimo
        Livro livro = livroRepository.findById(emprestimo.getLivro().getIdLivro()).get();
        User user = userRepository.findById(emprestimo.getUser().getIdUser()).get();

        // Atualiza o objeto empréstimo com as entidades completas
        emprestimo.setLivro(livro);
        emprestimo.setUser(user);

        // Verifica quantos exemplares do livro estão disponíveis para empréstimo
        int qtdLivros = livroService.quantidadeDisponivel(livro);

        // Se não houver exemplares disponíveis, lança exceção personalizada
        if (qtdLivros < 1) {
            throw new LivroIndisponivelException("Não há exemplares disponíveis para empréstimo.");
        }

        // Salva o empréstimo no banco e retorna o objeto salvo
        return emprestimoRepository.save(emprestimo);
    }

    public Emprestimo findById(Long id) {
        if (!emprestimoRepository.existsById(id)) {
            throw new RuntimeException("Emprestimo não encontrado.");
        }
        return emprestimoRepository.findById(id).get();
    }
}
