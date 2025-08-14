package com.tdesi.sa_sistema_de_biblioteca.repository;

import java.beans.JavaBean;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tdesi.sa_sistema_de_biblioteca.model.Emprestimo;
import com.tdesi.sa_sistema_de_biblioteca.model.Livro;
import com.tdesi.sa_sistema_de_biblioteca.model.User;

@Repository
@JavaBean
public class LivroRepository extends ObjectFileRepository<Long, Livro> {

    public LivroRepository() {
        super(Livro.class, "idLivro");
    }

    public Livro findByTitulo(String nomeLivro) throws IOException {
        List<Livro> lista = findAll();

        // Ordenar a lista pelo título (Insertion Sort)
        for (int i = 1; i < lista.size(); i++) {
            Livro chave = lista.get(i);
            int j = i - 1;

            while (j >= 0 && lista.get(j).getTitulo().compareToIgnoreCase(chave.getTitulo()) > 0) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, chave);
        }

        // Busca Binária pelo título
        int inicio = 0;
        int fim = lista.size() - 1;
        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            Livro livroMeio = lista.get(meio);
            int comparacao = livroMeio.getTitulo().compareToIgnoreCase(nomeLivro);

            if (comparacao == 0) {
                return livroMeio; // Encontrou
            } else if (comparacao < 0) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }

        return null; // Não encontrado
    }

    public List<Livro> buscarPorFiltros(String titulo, String editora, Date dataPublicacao,
            Long idAutor, String idCategoria, String sinopse,
            Integer quantidadeTotal) throws IOException {
        List<Livro> lista = findAll(); // Pega todos os livros do arquivo
        List<Livro> filtrados = new ArrayList<>();

        // Filtra manualmente
        for (Livro livro : lista) {
            boolean incluir = true;

            if (titulo != null && !livro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                incluir = false;
            }

            if (editora != null && !livro.getEditora().equals(editora)) {
                incluir = false;
            }

            if (dataPublicacao != null && !livro.getAnoPublicacao().equals(dataPublicacao)) {
                incluir = false;
            }

            if (idAutor != null && !livro.getAutor().getIdAutor().equals(idAutor)) {
                incluir = false;
            }

            if (idCategoria != null && !livro.getCategoriaLivro().equals(idCategoria)) {
                incluir = false;
            }

            if (sinopse != null && !livro.getSinopse().toLowerCase().contains(sinopse.toLowerCase())) {
                incluir = false;
            }

            if (quantidadeTotal != null && livro.getQuantidadeTotal() != quantidadeTotal) {
                incluir = false;
            }

            if (incluir) {
                filtrados.add(livro);
            }
        }

        // Ordena pelo título usando Insertion Sort
        for (int i = 1; i < filtrados.size(); i++) {
            Livro chave = filtrados.get(i);
            int j = i - 1;

            while (j >= 0 && filtrados.get(j).getTitulo().compareToIgnoreCase(chave.getTitulo()) > 0) {
                filtrados.set(j + 1, filtrados.get(j));
                j--;
            }
            filtrados.set(j + 1, chave);
        }

        return filtrados;
    }

}