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
        User user = userRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Emprestimo> emprestimos = emprestimoRepository.findByUser(user);

        List<LivroDTO> emprestados = new ArrayList<>();
        List<LivroDTO> devolvidos = new ArrayList<>();

        for (Emprestimo e : emprestimos) {
            LivroDTO livroDTO = new LivroDTO();
            livroDTO.setId(e.getLivro().getIdLivro());
            livroDTO.setTitulo(e.getLivro().getTitulo());
            livroDTO.setAutor(e.getLivro().getAutor());
            
            if (e.getDataDevolucao() == null) {
                emprestados.add(livroDTO);
            } else {
                livroDTO.setDataDevolucao(e.getDataDevolucao());
                devolvidos.add(livroDTO);
            }
        }

        RelatorioEmprestimosDTO relatorio = new RelatorioEmprestimosDTO();
        relatorio.setLivrosEmprestados(emprestados);
        relatorio.setLivrosDevolvidos(devolvidos);

        return relatorio;
    }

    public User save(User user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("O e-mail deve ser fornecido.");
        }
    
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalStateException("E-mail já está em uso.");
        }

        return userRepository.save(user);
    }

    public User getUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new NoSuchElementException("Email não existe.");
        }

        if (!user.getPassword().equals(password)) {
            throw new SecurityException("Senha incorreta.");
        }

        return user;
    }

    public User editUser(User user) {
        if (!userRepository.existsById(user.getIdUser())) {
            throw new RuntimeException("Usuário não encontrado para edição.");
        }
        return userRepository.save(user);
    }

    public boolean deleteUser(Long id){
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    public User findByID(Long id){
        if(!userRepository.existsById(id)){
            throw new RuntimeException("Usuário não encontrado.");
        }
        return userRepository.findById(id).get();
    }

}
