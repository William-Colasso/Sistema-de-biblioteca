package com.tdesi.sa_sistema_de_biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tdesi.sa_sistema_de_biblioteca.model.Emprestimo;
import com.tdesi.sa_sistema_de_biblioteca.model.Livro;
import com.tdesi.sa_sistema_de_biblioteca.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class EmprestimoRepository  extends ObjectFileRepository<Long, Emprestimo> {

    public EmprestimoRepository(Class<Emprestimo> typeClass, String idFieldName) throws IOException {
        super(typeClass, "idEmprestimo");
    }


    public List<Emprestimo> findByUser(User user){
        List<Emprestimo> emprestimos = new ArrayList<>();
        return emprestimos ;
    }

}
