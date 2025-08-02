-- Inserção de autores
INSERT INTO autor (nome) VALUES
('Machado de Assis'),
('Stephen King'),
('J.K. Rowling');

-- Inserção de usuários
INSERT INTO user (id_user, nome, email, telefone, password, is_bibliotecario) VALUES
(1, 'Ana Silva', 'ana@email.com', '11999999999', 'senha123', 0),
(2, 'Carlos Souza', 'carlos@email.com', '11888888888', 'admin123', 1);

-- Inserção de livros
INSERT INTO livro (
    id_livro, titulo, editora, ano_publicacao, sinopse, imagem_capa,
    categoria_livro, id_autor, quantidade_total
) VALUES
(1, 'Dom Casmurro', 'Editora Brasil', '1899-01-01', 'Clássico da literatura brasileira.', NULL, 'ROMANCE', 1, 5),
(2, 'It - A Coisa', 'Suma', '1986-01-01', 'Obra de terror de Stephen King.', NULL, 'TERROR', 2, 3),
(3, 'Harry Potter e a Pedra Filosofal', 'Rocco', '1997-01-01', 'Primeiro livro da saga.', NULL, 'FANTASIA', 3, 7);

-- Inserção de empréstimos
INSERT INTO emprestimo (
    id_emprestimo, data_emprestimo, data_devolucao_prevista, data_devolucao, devolvido,
    id_livro, id_user
) VALUES
(1, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY), NULL, 0, 1, 1),
(2, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY), NULL, 0, 3, 2);
