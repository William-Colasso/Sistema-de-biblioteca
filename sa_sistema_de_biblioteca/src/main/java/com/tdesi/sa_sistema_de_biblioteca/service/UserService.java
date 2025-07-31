package com.tdesi.sa_sistema_de_biblioteca.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tdesi.sa_sistema_de_biblioteca.model.User;
import com.tdesi.sa_sistema_de_biblioteca.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
}
