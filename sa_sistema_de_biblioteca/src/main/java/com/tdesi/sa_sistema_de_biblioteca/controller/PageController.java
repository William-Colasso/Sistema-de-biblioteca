package com.tdesi.sa_sistema_de_biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping
public class PageController {
    @GetMapping("/home")
    public String getIndex() {
        return "index";
    }
    @GetMapping("/profile")
    public String getProfile() {
        return "profile";
    }
    @GetMapping("/browse")
    public String getBrowse() {
        return "browse";
    }
    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }
    @GetMapping("/cadastroLivro")
    public String getCadastroLivro() {
        return "cadastroLivro";
    }
    @GetMapping("/autor/register")
    public String getRegister() {
        return "cadastroAutor";
    }

    @GetMapping("/categorias")
    public String getCategoriasPage(){
        return "categorias";
    }


    @GetMapping("/emprestimo/register")
    public String getRegisterEmprestimo(){
        return "emprestimo";
    }

    
}
