package com.tdesi.sa_sistema_de_biblioteca.repository;

import java.beans.JavaBean;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Repository;


import com.tdesi.sa_sistema_de_biblioteca.model.User;

@Repository
@JavaBean
public class UserRepository extends ObjectFileRepository<Long, User> {

    public UserRepository() {
        super(User.class, "idUser");
    }

    public User findByEmail(String email) throws IOException {
        List<User> listaUser = findAll();

        // Ordenar a lista pelo email (Insertion Sort)
        for (int i = 1; i < listaUser.size(); i++) {
            User chave = listaUser.get(i);
            int j = i - 1;

            while (j >= 0 && listaUser.get(j).getEmail().compareToIgnoreCase(chave.getEmail()) > 0) {
                listaUser.set(j + 1, listaUser.get(j));
                j--;
            }
            listaUser.set(j + 1, chave);
        }

        // Busca Binária pelo email
        int inicio = 0;
        int fim = listaUser.size() - 1;
        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            User usuarioMeio = listaUser.get(meio);
            int comparacao = usuarioMeio.getEmail().compareToIgnoreCase(email);

            if (comparacao == 0) {
                return usuarioMeio; // Encontrado
            } else if (comparacao < 0) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }

        return null; // Não encontrado
    }

}