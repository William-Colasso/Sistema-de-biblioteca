package com.tdesi.sa_sistema_de_biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tdesi.sa_sistema_de_biblioteca.model.Emprestimo;
import com.tdesi.sa_sistema_de_biblioteca.model.Livro;
import com.tdesi.sa_sistema_de_biblioteca.model.User;

import java.util.List;


@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo,Long> {
    @Query("SELECT COUNT(e) FROM Emprestimo e WHERE e.livro = :livro AND e.devolvido = false")
    Long countEmprestimosAtivosByLivro(@Param("livro") Livro livro);

    List<Emprestimo> findByUser(User user);
}
