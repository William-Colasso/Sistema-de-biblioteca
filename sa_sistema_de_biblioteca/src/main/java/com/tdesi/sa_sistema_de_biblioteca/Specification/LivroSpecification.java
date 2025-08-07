package com.tdesi.sa_sistema_de_biblioteca.Specification;

import java.sql.Date;

import org.springframework.data.jpa.domain.Specification;

import com.tdesi.sa_sistema_de_biblioteca.model.CategoriaLivro;
import com.tdesi.sa_sistema_de_biblioteca.model.EditoraLivro;
import com.tdesi.sa_sistema_de_biblioteca.model.Livro;

import jakarta.persistence.criteria.Predicate;

public class LivroSpecification {

    public static Specification<Livro> filtrar(String titulo, String editora, Date dataPublicacao, Long idAutor, String categoria,
            String sinopse, Integer quantidadeTotal) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (titulo != null && !titulo.isEmpty()) {
                predicate = builder.and(predicate,
                        builder.like(builder.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%"));
            }

            if (editora != null && !editora.isEmpty()) {
                try {
                    EditoraLivro editoraEnum = EditoraLivro.valueOf(editora.toUpperCase());
                    predicate = builder.and(predicate, builder.equal(root.get("editora"), editoraEnum));
                } catch (IllegalArgumentException e) {
                    
                }
            }

            if (dataPublicacao != null) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get("dataPublicacao"), dataPublicacao));
            }

            if (idAutor != null) {
                predicate = builder.and(predicate, builder.equal(root.get("autor").get("idAutor"), idAutor));
            }

            if (categoria != null && !categoria.isEmpty()) {
                try {
                    CategoriaLivro categoriaEnum = CategoriaLivro.valueOf(categoria.toUpperCase());
                    predicate = builder.and(predicate, builder.equal(root.get("categoriaLivro"), categoriaEnum));
                } catch (IllegalArgumentException e) {
                    
                }
            }
            if (sinopse != null && !sinopse.isEmpty()) {
                predicate = builder.and(predicate,
                        builder.like(builder.lower(root.get("sinopse")), "%" + sinopse.toLowerCase() + "%"));
            }

            if (quantidadeTotal != null) {
                predicate = builder.and(predicate,
                        builder.greaterThanOrEqualTo(root.get("quantidadeTotal"), quantidadeTotal));
            }
            System.out.println(predicate);
            return predicate;
        };
    }
}
