package com.fatec.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fatec.demo.entities.Categoria;

import java.util.Optional;

//http://localhost:8080/api/categorias

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Create (POST)
    @PostMapping
    public ResponseEntity<Object> criarCategoria(@RequestBody Categoria categoria) {
        
        if (categoria.getDescricao() == null || categoria.getDescricao().trim().isEmpty()) {
            return new ResponseEntity<>("A descrição é obrigatória e não pode ser vazia.", HttpStatus.BAD_REQUEST);
        }

        Optional<Categoria> categoriaExistente = categoriaRepository.findByDescricao(categoria.getDescricao());
        if (categoriaExistente.isPresent()) {
            return new ResponseEntity<>("A descrição já está em uso.", HttpStatus.BAD_REQUEST);
        }

        if (categoria.getAtivo() == null) {
            categoria.setAtivo(true);
        }

        Categoria categoriaCriada = categoriaRepository.save(categoria);
        return new ResponseEntity<>(categoriaCriada, HttpStatus.CREATED);
    }
}
