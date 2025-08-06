package com.tdesi.sa_sistema_de_biblioteca.Specification;

import org.springframework.data.jpa.domain.Specification;

import com.tdesi.sa_sistema_de_biblioteca.model.Livro;

import jakarta.persistence.criteria.Predicate;

public class LivroSpecification {

    public static Specification<Livro> filtrar(String titulo, String editora, Long idAutor, String categoria, String sinopse, Integer quantidadeTotal) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();
    
            if (titulo != null && !titulo.isEmpty()) {
                predicate = builder.and(predicate, builder.like(builder.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%"));
            }
    
            if (editora != null && !editora.isEmpty()) {
                predicate = builder.and(predicate, builder.like(builder.lower(root.get("editora")), "%" + editora.toLowerCase() + "%"));
            }
    
            if (idAutor != null) {
                predicate = builder.and(predicate, builder.equal(root.get("autor").get("id"), idAutor));
            }
    
            if (categoria != null && !categoria.isEmpty()) {
                predicate = builder.and(predicate, builder.like(builder.lower(root.get("categoria")), "%" + categoria.toLowerCase() + "%"));
            }
    
            if (sinopse != null && !sinopse.isEmpty()) {
                predicate = builder.and(predicate, builder.like(builder.lower(root.get("sinopse")), "%" + sinopse.toLowerCase() + "%"));
            }
    
            if (quantidadeTotal != null) {
                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get("quantidadeTotal"), quantidadeTotal));
            }
    
            return predicate;
        };
    }
}
