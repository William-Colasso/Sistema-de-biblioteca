package com.tdesi.sa_sistema_de_biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdesi.sa_sistema_de_biblioteca.exception.EmailAlreadyExistsException;
import com.tdesi.sa_sistema_de_biblioteca.exception.EmailNotProvidedException;
import com.tdesi.sa_sistema_de_biblioteca.model.User;
import com.tdesi.sa_sistema_de_biblioteca.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new EmailNotProvidedException();
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }

        return userRepository.save(user);
    }

    public String getUser(String email, String password){
        if(userRepository.findByEmail(email) == null){
            return "Email não existe";
        }
        User user = userRepository.findByEmail(email);
        if(user.getPassword() != password){
            return "Senha incorreta";
        }

        return "Sucesso";
    }
}
