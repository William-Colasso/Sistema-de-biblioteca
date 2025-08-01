package com.tdesi.sa_sistema_de_biblioteca.Specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tdesi.sa_sistema_de_biblioteca.model.Livro;

import jakarta.persistence.criteria.Predicate;

public class LivroSpecification {

    public static Specification<Livro> filtrar(
        String titulo,
        String editora,
        Long idAutor,
        Long idCategoria
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (titulo != null && !titulo.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%"));
            }

            if (editora != null && !editora.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("editora")), "%" + editora.toLowerCase() + "%"));
            }

            if (idAutor != null) {
                predicates.add(cb.equal(root.get("autor").get("id"), idAutor));
            }

            if (idCategoria != null) {
                predicates.add(cb.equal(root.get("categoriaLivro").get("id"), idCategoria));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
