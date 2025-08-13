package com.tdesi.sa_sistema_de_biblioteca.repository;

import java.io.IOException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tdesi.sa_sistema_de_biblioteca.model.User;

@Repository
public class UserRepository extends ObjectFileRepository<Long, User> {

     UserRepository(Class<User> t) throws IOException {
        super(t, "idUser");
    }


}