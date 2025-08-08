ALTER DATABASE sistemaBiblioteca CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE `autor` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE `emprestimo` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE `livro` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE `user` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- Inserção de autores
INSERT INTO autor (id_autor, nome_autor, foto_autor) VALUES
(1, 'Machado de Assis', NULL),
(2, 'Stephen King', 'https://upload.wikimedia.org/wikipedia/commons/e/e3/Stephen_King%2C_Comicon.jpg'),
(3, 'J.K. Rowling', 'https://upload.wikimedia.org/wikipedia/commons/5/5d/J._K._Rowling_2010.jpg'),
(4, 'Antoine de Saint-Exupéry', 'https://upload.wikimedia.org/wikipedia/commons/6/63/Antoine_de_Saint-Exupéry_01.jpg'),
(5, 'George Orwell', 'https://upload.wikimedia.org/wikipedia/commons/8/8e/George_Orwell_press_photo.jpg'),
(6, 'Sun Tzu', NULL),
(7, 'J.R.R. Tolkien', 'https://upload.wikimedia.org/wikipedia/commons/b/b4/Tolkien_1916.jpg'),
(8, 'Gabriel García Márquez', 'https://upload.wikimedia.org/wikipedia/commons/1/11/Gabriel_Garcia_Marquez.jpg'),
(9, 'Dan Brown', 'https://upload.wikimedia.org/wikipedia/commons/1/10/Dan_Brown_2017.jpg');

-- Inserção de usuários
INSERT INTO user (id_user, nome, email, telefone, password, is_bibliotecario) VALUES
(1, 'Ana Silva', 'ana@email.com', '11999999999', 'senha123', 0),
(2, 'Carlos Souza', 'carlos@email.com', '11888888888', 'admin123', 1);

-- Inserção de livros
-- Inserção de livros
INSERT INTO livro (id_livro, titulo, editora, ano_publicacao, sinopse, imagem_capa,
    categoria_livro, id_autor, quantidade_total
) VALUES
(1, 'Dom Casmurro', 'CARAMURE', '1899-01-01', 'Clássico da literatura brasileira.', 'https://m.media-amazon.com/images/I/61Z2bMhGicL.jpg', 'ROMANCE', 1, 5),
(2, 'It - A Coisa', 'DARKSIDE', '1986-01-01', 'Obra de terror de Stephen King.', 'https://covers.openlibrary.org/b/id/8231851-L.jpg', 'TERROR', 2, 3),
(3, 'Harry Potter e a Pedra Filosofal', 'ROCCO', '1997-06-26', 'Primeiro livro da saga.', 'https://covers.openlibrary.org/b/id/7984916-L.jpg', 'FANTASIA', 3, 7),
(4, 'O Pequeno Príncipe', 'FTD', '1943-04-06', 'Uma fábula filosófica sobre amor e amizade.', 'https://covers.openlibrary.org/b/id/7222246-L.jpg', 'INFANTIL', 4, 4),
(5, '1984', 'COMPANHIA_DAS_LETRAS', '1949-06-08', 'Distopia clássica sobre vigilância e controle.', 'https://m.media-amazon.com/images/I/819js3EQwbL.jpg', 'FICCAO', 5, 6),
(6, 'A Arte da Guerra', 'ALTA_BOOKS', '0500-01-01', 'Tratado militar e filosófico chinês.', 'https://covers.openlibrary.org/b/id/8319256-L.jpg', 'FILOSOFIA', 6, 2),
(7, 'O Senhor dos Anéis: A Sociedade do Anel', 'MARTINS_FONTES', '1954-07-29', 'A jornada de Frodo para destruir o Um Anel.', 'https://covers.openlibrary.org/b/id/8235110-L.jpg', 'FANTASIA', 7, 5),
(8, 'A Revolução dos Bichos', 'COMPANHIA_DAS_LETRAS', '1945-08-17', 'Sátira política sobre o totalitarismo.', 'https://covers.openlibrary.org/b/id/8268232-L.jpg', 'FICCAO', 5, 4),
(9, 'Cem Anos de Solidão', 'RECORD', '1967-05-30', 'Saga da família Buendía na fictícia Macondo.', 'https://covers.openlibrary.org/b/id/8231830-L.jpg', 'ROMANCE', 8, 3),
(10, 'O Código Da Vinci', 'ARQUEIRO', '2003-03-18', 'Suspense envolvendo símbolos e história religiosa.', 'https://covers.openlibrary.org/b/id/8226190-L.jpg', 'SUSPENSE', 9, 6);


-- Inserção de empréstimos
INSERT INTO emprestimo (data_emprestimo, data_devolucao_prevista, data_devolucao, devolvido,
    id_livro, id_user
) VALUES
(CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY), NULL, 0, 1, 1),
(CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY), NULL, 0, 3, 2);