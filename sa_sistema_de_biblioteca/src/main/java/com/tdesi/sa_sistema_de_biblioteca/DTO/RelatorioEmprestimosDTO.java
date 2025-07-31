package com.tdesi.sa_sistema_de_biblioteca.DTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioEmprestimosDTO {
    private List<LivroDTO> livrosEmprestados;
    private List<LivroDTO> livrosDevolvidos;
}
