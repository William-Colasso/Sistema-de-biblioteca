package com.tdesi.sa_sistema_de_biblioteca.repository;

import java.beans.JavaBean;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tdesi.sa_sistema_de_biblioteca.model.EditoraLivro;
import com.tdesi.sa_sistema_de_biblioteca.model.Livro;

@Repository
@JavaBean
public class LivroRepository extends ObjectFileRepository<Long, Livro> {

    public LivroRepository() {
        super(Livro.class, "idLivro");
    }

    public Livro findByTitulo(String nomeLivro) throws IOException {
        List<Livro> listaLivros = findAll();

        // Ordenar a lista pelo título (Insertion Sort)
        for (int i = 1; i < listaLivros.size(); i++) {
            Livro chave = listaLivros.get(i);
            int j = i - 1;

            while (j >= 0 && listaLivros.get(j).getTitulo().compareToIgnoreCase(chave.getTitulo()) > 0) {
                listaLivros.set(j + 1, listaLivros.get(j));
                j--;
            }
            listaLivros.set(j + 1, chave);
        }

        // Busca Binária pelo título
        int inicio = 0;
        int fim = listaLivros.size() - 1;
        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            Livro livroMeio = listaLivros.get(meio);
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
            Long idAutor, String categoria, String sinopse,
            Integer quantidadeTotal) throws IOException {
        List<Livro> listaLivros = findAll(); // Pega todos os livros do arquivo
        List<Livro> listaFiltrados = new ArrayList<>();
        // Filtra manualmente
        for (Livro livro : listaLivros) {
            boolean incluir = true;

            if (titulo != null && !livro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                incluir = false;
            }

            if (editora != null && !livro.getEditora().toString().equals(editora)) {
                incluir = false;
            }
            
            if (dataPublicacao != null && !livro.getAnoPublicacao().after(dataPublicacao)) {
                incluir = false;
            }

            if (idAutor != null && !livro.getAutor().getIdAutor().equals(idAutor)) {
                incluir = false;
            }

            if (categoria != null && !livro.getCategoriaLivro().toString().equals(categoria)) {
                incluir = false;
            }

            if (sinopse != null && !livro.getSinopse().toLowerCase().contains(sinopse.toLowerCase())) {
                incluir = false;
            }

            if (quantidadeTotal != null && livro.getQuantidadeTotal() < quantidadeTotal) {
                incluir = false;
            }

            if (incluir) {
                listaFiltrados.add(livro);
            }
        }

        // Ordena pelo título usando Insertion Sort
        for (int i = 1; i < listaFiltrados.size(); i++) {
            Livro chave = listaFiltrados.get(i);
            int j = i - 1;

            while (j >= 0 && listaFiltrados.get(j).getTitulo().compareToIgnoreCase(chave.getTitulo()) > 0) {
                listaFiltrados.set(j + 1, listaFiltrados.get(j));
                j--;
            }
            listaFiltrados.set(j + 1, chave);
        }

        return listaFiltrados;
    }

}