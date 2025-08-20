package com.tdesi.sa_sistema_de_biblioteca.repository;


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
        List<Emprestimo> listaEmprestimos = findAll();
    
        // Ordenar por devolvido (false antes de true) usando Selection Sort
        for (int i = 0; i < listaEmprestimos.size() - 1; i++) {
            int indiceMenor = i;
            for (int j = i + 1; j < listaEmprestimos.size(); j++) {
                if (!listaEmprestimos.get(j).isDevolvido() && listaEmprestimos.get(indiceMenor).isDevolvido()) {
                    indiceMenor = j;
                }
            }
            Emprestimo temp = listaEmprestimos.get(i);
            listaEmprestimos.set(i, listaEmprestimos.get(indiceMenor));
            listaEmprestimos.set(indiceMenor, temp);
        }
    
        // Filtrar os que têm devolvido == b
        List<Emprestimo> listaResultado = new ArrayList<>();
        for (Emprestimo e : listaEmprestimos) {
            if (e.isDevolvido() == b) {
                listaResultado.add(e);
            }
        }
    
        return listaResultado;
    }


    public int countEmprestimosAtivosByLivro(Livro livro) throws IOException {
        List<Emprestimo> listaEmprestimos = findAll();
        int contador = 0;
    
        for (Emprestimo e : listaEmprestimos) {
            if (e.getLivro().getIdLivro()==livro.getIdLivro() && !e.isDevolvido()) {
                contador++;
                System.out.println(contador);
            }
        }
    
        return contador;
    }

}
