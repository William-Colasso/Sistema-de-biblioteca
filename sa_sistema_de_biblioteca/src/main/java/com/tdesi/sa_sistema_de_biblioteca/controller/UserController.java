package com.tdesi.sa_sistema_de_biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tdesi.sa_sistema_de_biblioteca.DTO.RelatorioEmprestimosDTO;
import com.tdesi.sa_sistema_de_biblioteca.DTO.UserLoginDTO;
import com.tdesi.sa_sistema_de_biblioteca.model.User;
import com.tdesi.sa_sistema_de_biblioteca.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/account")
    public String getAccount() {
        return "account";
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getUsers() {
        // Retorna lista de todos os usuários com status 200 OK
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/get")
    public ResponseEntity<User> getUser(@RequestParam Long idUser) {
        // Retorna um usuário pelo ID passado via parâmetro de query string
        User user = userService.findByID(idUser);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{id}/relatorio-emprestimos")
    public ResponseEntity<RelatorioEmprestimosDTO> getRelatorioEmprestimos(@PathVariable Long id) {
        // Gera e retorna relatório de empréstimos de um usuário pelo ID na URL
        RelatorioEmprestimosDTO relatorio = userService.gerarRelatorioEmprestimos(id);
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/libraria")
    public String getBibliotecario() {
        // Retorna uma string fixa "bibliotecario"
        // Possivelmente para teste ou endpoint simples
        return "bibliotecario";
    }

    @PostMapping("/login")
    public ResponseEntity<User> postUser(@RequestBody UserLoginDTO userLoginDTO) {
        // Recebe dados de login (email e senha) e retorna o usuário autenticado
        User getUser = userService.getUser(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        return ResponseEntity.ok().body(getUser);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Cria um novo usuário recebendo objeto User no corpo da requisição
        User save = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody User user) {
        // Atualiza dados do usuário identificado pelo ID na URL
        // Força o ID do objeto para garantir atualização do registro correto
        user.setIdUser(id);
        User edited = userService.editUser(user);
        return ResponseEntity.ok(edited);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        // Exclui usuário pelo ID, retorna 200 OK se excluído ou 404 Not Found se não existir
        return userService.deleteUser(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
