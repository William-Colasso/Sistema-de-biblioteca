package com.tdesi.sa_sistema_de_biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdesi.sa_sistema_de_biblioteca.Specification.LivroSpecification;
import com.tdesi.sa_sistema_de_biblioteca.model.Livro;
import com.tdesi.sa_sistema_de_biblioteca.repository.EmprestimoRepository;
import com.tdesi.sa_sistema_de_biblioteca.repository.LivroRepository;

@Service
public class LivroService {
    @Autowired
    LivroRepository livroRepository;

    @Autowired
    EmprestimoRepository emprestimoRepository;

    public Livro save(Livro livro){
        return livroRepository.save(livro);
    }

    public List<Livro> getTotalLivros(){
        List<Livro> lista = livroRepository.findAll();
        return lista;
    }

    public int quantidadeDisponivel(Livro livro){
        return (int) (livro.getQuantidadeTotal()-emprestimoRepository.countEmprestimosAtivosByLivro(livro));
    }

    public String relatorio(String nomeLivro){
        Livro livro = livroRepository.findByTitulo(nomeLivro);
        StringBuilder sb = new StringBuilder();
        sb.append("ID "+livro.getIdLivro()+"\n");
        sb.append("Titulo do livro: "+livro.getTitulo()+"\n");
        sb.append("Autor do Livro: "+livro.getAutor().getNomeAutor()+"\n");
        sb.append("Quantidade Total: "+livro.getQuantidadeTotal()+"\n");
        sb.append("Quantidade Disponivel: "+quantidadeDisponivel(livro)+"\n");
        sb.append("Quantidade Emprestada: "+emprestimoRepository.countEmprestimosAtivosByLivro(livro));
        return sb.toString();
    }

    public Livro findById(Long idLivro) {
        return livroRepository.findById(idLivro).get();
    }

    public List<Livro> buscarPorFiltros(String titulo, String editora, Long idAutor, String idCategoria, String sinopse, Integer quantidadeTotal) {
        return livroRepository.findAll(LivroSpecification.filtrar(titulo, editora, idAutor, idCategoria, sinopse, quantidadeTotal));
    }
}
