package com.tdesi.sa_sistema_de_biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tdesi.sa_sistema_de_biblioteca.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
