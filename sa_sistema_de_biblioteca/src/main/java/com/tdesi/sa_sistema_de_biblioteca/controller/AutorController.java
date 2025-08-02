package com.tdesi.sa_sistema_de_biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tdesi.sa_sistema_de_biblioteca.model.Autor;
import com.tdesi.sa_sistema_de_biblioteca.service.AutorService;

@RestController
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    AutorService autorService;
    
    @GetMapping("/all")
    public List<Autor> getAutor(){
        return autorService.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<Autor> saveAutor(@RequestBody Autor autor){
        Autor save = autorService.save(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }
    
}
