package com.tdesi.sa_sistema_de_biblioteca.service;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdesi.sa_sistema_de_biblioteca.model.Autor;
import com.tdesi.sa_sistema_de_biblioteca.model.Livro;
import com.tdesi.sa_sistema_de_biblioteca.repository.AutorRepository;
import com.tdesi.sa_sistema_de_biblioteca.repository.EmprestimoRepository;
import com.tdesi.sa_sistema_de_biblioteca.repository.LivroRepository;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    private final AutorRepository autorRepository;

    @Autowired
    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }
    
    @Autowired
    EmprestimoRepository emprestimoRepository;

    public Livro save(Livro livro) throws IOException{
        // Salva ou atualiza um livro no banco
        return livroRepository.save(livro);
    }
    
    public List<Livro> getTotalLivros() throws IOException{
        // Retorna a lista completa de livros cadastrados
        List<Livro> lista = livroRepository.findAll();
        return lista;
    }
    
    public int quantidadeDisponivel(Livro livro) throws IOException{
        // Calcula a quantidade disponível do livro
        // Subtrai os empréstimos ativos da quantidade total
        System.out.println(emprestimoRepository.countEmprestimosAtivosByLivro(livro));
        return (int) (livro.getQuantidadeTotal() - emprestimoRepository.countEmprestimosAtivosByLivro(livro));
    }
    
    public Livro relatorio(String nomeLivro) throws IOException{
        // Gera um relatório em texto com informações do livro pelo título
        Livro livro = livroRepository.findByTitulo(nomeLivro);
        Autor autor = autorRepository.findById(livro.getAutor().getIdAutor()).get();
        livro.setAutor(autor);
        livro.setQuantidadeTotal(quantidadeDisponivel(livro));
        return livro;
    }
    
    public Livro findById(Long idLivro) throws IOException {
        // Busca um livro pelo ID, retornando o objeto encontrado
        return livroRepository.findById(idLivro).get();
    }
    
    public List<Livro> buscarPorFiltros(String titulo, String editora, Date dataPublicacao, Long idAutor, String idCategoria, String sinopse, Integer quantidadeTotal) throws IOException {
        // Retorna uma lista de livros filtrados pelos parâmetros informados
        return livroRepository.buscarPorFiltros(
            titulo, editora, dataPublicacao, idAutor, idCategoria, sinopse, quantidadeTotal
        );
    }
}
