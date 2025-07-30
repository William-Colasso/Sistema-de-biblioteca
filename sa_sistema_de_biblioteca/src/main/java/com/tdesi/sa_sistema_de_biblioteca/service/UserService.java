package com.tdesi.sa_sistema_de_biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdesi.sa_sistema_de_biblioteca.model.User;
import com.tdesi.sa_sistema_de_biblioteca.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }
}
