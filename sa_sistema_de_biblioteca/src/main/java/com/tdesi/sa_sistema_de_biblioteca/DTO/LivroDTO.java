package com.tdesi.sa_sistema_de_biblioteca.DTO;

import java.sql.Date;

import com.tdesi.sa_sistema_de_biblioteca.model.Autor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {
    private Long id;
    private String titulo;
    private Autor autor;
    private Date dataDevolucao;
}
