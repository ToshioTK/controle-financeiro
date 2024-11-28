package com.fatec.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





//ENDEREÇO DO ENDPOINT

//http://localhost:8080/api/exercicio1/
@RestController
@RequestMapping("/api/exercicio1")
public class exercicio1controller {
    

    @GetMapping("/teste")
    public String helloWorld() {
        return "hello1";
    }
    
    @GetMapping()
    public String teste() {
        return "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    }
    @PostMapping("/requisicao-corpo")
    public String teste2 (@RequestBody String nome){
        return new StringBuilder(nome).reverse().toString();
    }
    


}
