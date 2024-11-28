package com.fatec.demo.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.demo.entities.User;



@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    //http://localhost:<port>/api/usuario/register
    //POST
    //Parametro: @RequestBody => enviar no corpo da requisicao (body)

    //@PostMapping => POST - CRIACAO / CONSULTAS COM PARAMETROS NO CORPO DA REQUISICAO
    //@GetMapping => GET - CONSULTAS
    //@PutMapping -> PUT - ALTERACAO
    //@DeleteMapping -> DELETE - DELETAR
    //@PatchMapping -> PATCH - ANEXAR/ALTERAR

    @PostMapping("/register")
    //RequestBody => permite passar variaveis pelo corpo da requisição -> POST
    public String registerUser(@RequestBody User usuario) {
        return "Bem-vindo, " + usuario.getName() + "! Você tem " + usuario.getAge() + " anos.";
    }

    @PostMapping("/register/{name}/{age}")
    //PathVriable => permite passar variaveis pela URl -> GET
    public String registerUser(@PathVariable String name, @PathVariable int age) {

        User usuario = new User();
        usuario.setName(name);
        usuario.setAge(age);

        return "Bem-vindo, " + usuario.getName() + "! Você tem " + usuario.getAge() + " anos.";
    }
}