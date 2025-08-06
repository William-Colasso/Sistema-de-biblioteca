package com.tdesi.sa_sistema_de_biblioteca.controller;

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

    @GetMapping("/get")
    public ResponseEntity<User> getUser(@RequestParam Long idUser) {
        User user = userService.findByID(idUser);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{id}/relatorio-emprestimos")
    public ResponseEntity<RelatorioEmprestimosDTO> getRelatorioEmprestimos(@PathVariable Long id) {
        RelatorioEmprestimosDTO relatorio = userService.gerarRelatorioEmprestimos(id);
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/libraria")
    public String getBibliotecario() {
        return "bibliotecario";
    }
    

    @PostMapping("/login")
    public ResponseEntity<User> postUser(@RequestBody UserLoginDTO userLoginDTO) {
        User getUser = userService.getUser(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        return ResponseEntity.ok().body(getUser);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User save = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody User user) {
        user.setIdUser(id);
        User edited = userService.editUser(user);
        return ResponseEntity.ok(edited);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
