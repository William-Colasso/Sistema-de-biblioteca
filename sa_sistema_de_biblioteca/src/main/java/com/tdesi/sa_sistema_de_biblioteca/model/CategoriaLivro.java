package com.tdesi.sa_sistema_de_biblioteca.model;

public enum CategoriaLivro {
    ROMANCE(1),
    TERROR(2),
    ACAO(3),
    AVENTURA(4),
    COMEDIA(5),
    DRAMA(6),
    FICCAO(7),
    BIOGRAFIA(8),
    HISTORIA(9),
    MISTERIO(10),
    SUSPENSE(11),
    POESIA(12),
    EDUCACAO(13),
    FILOSOFIA(14),
    RELIGIAO(15),
    CIENCIA(16),
    TECNOLOGIA(17),
    AUTOAJUDA(18),
    FANTASIA(19),
    INFANTIL(20),
    JUVENIL(21),

    // Categorias SENAI / Técnicas
    INDUSTRIA(22),
    ELETRICIDADE(23),
    MECANICA(24),
    ELETRONICA(25),
    ROBOTICA(26),
    AUTOMACAO(27),
    INFORMATICA(28),
    PROGRAMACAO(29),
    ENGENHARIA(30),
    SEGURANCA_DO_TRABALHO(31),
    LOGISTICA(32),
    GESTAO(33),
    ADMINISTRACAO(34),
    EMPREENDEDORISMO(35),
    MEIO_AMBIENTE(36),
    SUSTENTABILIDADE(37),
    DESENHO_TECNICO(38),
    METROLOGIA(39),
    SOLDAGEM(40),
    USINAGEM(41),
    MATERIAIS(42);

    private final int codigo;

    CategoriaLivro(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
