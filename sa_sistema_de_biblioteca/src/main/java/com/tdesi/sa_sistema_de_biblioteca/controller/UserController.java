package com.tdesi.sa_sistema_de_biblioteca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/")
public class UserController {
    

    @GetMapping("/home")
    public String getMethodName(@RequestParam String param) {
        return "index.html";
    }
    
}
