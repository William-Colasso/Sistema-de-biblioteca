package com.tdesi.sa_sistema_de_biblioteca.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdesi.sa_sistema_de_biblioteca.DTO.LivroDTO;
import com.tdesi.sa_sistema_de_biblioteca.DTO.RelatorioEmprestimosDTO;
import com.tdesi.sa_sistema_de_biblioteca.model.Emprestimo;
import com.tdesi.sa_sistema_de_biblioteca.model.User;
import com.tdesi.sa_sistema_de_biblioteca.repository.EmprestimoRepository;
import com.tdesi.sa_sistema_de_biblioteca.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    public RelatorioEmprestimosDTO gerarRelatorioEmprestimos(Long idUsuario) {
        // Busca usuário pelo ID ou lança exceção se não existir
        User user = userRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    
        // Busca todos os empréstimos feitos por esse usuário
        List<Emprestimo> emprestimos = emprestimoRepository.findByUser(user);
    
        // Listas para separar livros emprestados e devolvidos
        List<LivroDTO> emprestados = new ArrayList<>();
        List<LivroDTO> devolvidos = new ArrayList<>();
    
        // Percorre todos os empréstimos e organiza nas listas
        for (Emprestimo e : emprestimos) {
            LivroDTO livroDTO = new LivroDTO();
            livroDTO.setId(e.getLivro().getIdLivro());
            livroDTO.setTitulo(e.getLivro().getTitulo());
            livroDTO.setAutor(e.getLivro().getAutor());
            
            if (e.getDataDevolucao() == null) {
                // Ainda não devolvido
                emprestados.add(livroDTO);
            } else {
                // Já devolvido
                livroDTO.setDataDevolucao(e.getDataDevolucao());
                devolvidos.add(livroDTO);
            }
        }
    
        // Monta DTO final do relatório
        RelatorioEmprestimosDTO relatorio = new RelatorioEmprestimosDTO();
        relatorio.setLivrosEmprestados(emprestados);
        relatorio.setLivrosDevolvidos(devolvidos);
    
        return relatorio;
    }
    
    public User save(User user) {
        // Valida email
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("O e-mail deve ser fornecido.");
        }
        // Verifica duplicidade de email
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalStateException("E-mail já está em uso.");
        }
        // Salva usuário
        return userRepository.save(user);
    }
    
    public User getUser(String email, String password) {
        // Busca usuário pelo email
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NoSuchElementException("Email não existe.");
        }
        // Valida senha
        if (!user.getPassword().equals(password)) {
            throw new SecurityException("Senha incorreta.");
        }
        return user;
    }
    
    public User editUser(User user) {
        // Verifica se o usuário existe antes de editar
        if (!userRepository.existsById(user.getIdUser())) {
            throw new RuntimeException("Usuário não encontrado para edição.");
        }
        return userRepository.save(user);
    }
    
    public boolean deleteUser(Long id){
        // Exclui usuário se existir
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
    
    public User findByID(Long id){
        // Busca usuário pelo ID ou lança exceção
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado.");
        }
        return userRepository.findById(id).get();
    }
    
    public List<User> findAll(){
        // Lista todos os usuários
        return userRepository.findAll();
    }

}
