package com.tdesi.sa_sistema_de_biblioteca.repository;


import com.tdesi.sa_sistema_de_biblioteca.model.Autor;
import com.tdesi.sa_sistema_de_biblioteca.model.Emprestimo;
import com.tdesi.sa_sistema_de_biblioteca.model.Livro;
import com.tdesi.sa_sistema_de_biblioteca.model.User;

import java.beans.JavaBean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
@JavaBean
public class EmprestimoRepository  extends ObjectFileRepository<Long, Emprestimo> {

    // construtor padrão usado pelo Spring
    public EmprestimoRepository() {
        super(Emprestimo.class, "idEmprestimo");
    }


    public List<Emprestimo> findByUser(User user){
        List<Emprestimo> emprestimos = new ArrayList<>();
        return emprestimos ;
    }


    public List<Emprestimo> findByDevolvido(boolean b) throws IOException {
        List<Emprestimo> lista = findAll();
    
        // Ordenar por devolvido (false antes de true) usando Selection Sort
        for (int i = 0; i < lista.size() - 1; i++) {
            int indiceMenor = i;
            for (int j = i + 1; j < lista.size(); j++) {
                if (!lista.get(j).isDevolvido() && lista.get(indiceMenor).isDevolvido()) {
                    indiceMenor = j;
                }
            }
            Emprestimo temp = lista.get(i);
            lista.set(i, lista.get(indiceMenor));
            lista.set(indiceMenor, temp);
        }
    
        // Filtrar os que têm devolvido == b
        List<Emprestimo> resultado = new ArrayList<>();
        for (Emprestimo e : lista) {
            if (e.isDevolvido() == b) {
                resultado.add(e);
            }
        }
    
        return resultado;
    }


    public int countEmprestimosAtivosByLivro(Livro livro) throws IOException {
        List<Emprestimo> lista = findAll();
        int contador = 0;
    
        for (Emprestimo e : lista) {
            if (e.getLivro().equals(livro) && !e.isDevolvido()) {
                contador++;
            }
        }
    
        return contador;
    }

}
