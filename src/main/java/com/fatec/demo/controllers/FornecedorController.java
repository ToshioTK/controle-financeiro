package com.fatec.demo.controllers;

import com.fatec.demo.entities.Fornecedor;
import com.fatec.demo.controllers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//http://localhost:8080/api/fornecedores

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    // Create (POST)
    @PostMapping("/criar")
    public ResponseEntity<Fornecedor> criarFornecedor(@RequestBody Fornecedor fornecedor) {
        Fornecedor fornecedorCriado = fornecedorRepository.save(fornecedor);
        return new ResponseEntity<>(fornecedorCriado, HttpStatus.CREATED);
    }

    // Read (GET)
    @GetMapping("/listar")
    public ResponseEntity<List<Fornecedor>> getFornecedores() {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();
        return new ResponseEntity<>(fornecedores, HttpStatus.OK);
    }

    // Get pelo ID
    @GetMapping("/listar/{id}")
    public ResponseEntity<Fornecedor> getById(@PathVariable Long id) {
        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(id);
        if (fornecedor.isPresent()) {
            return new ResponseEntity<>(fornecedor.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update (PUT)
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Fornecedor> atualizaFornecedor(@PathVariable Long id, @RequestBody Fornecedor fornecedorAtualizado) {
        Optional<Fornecedor> fornecedorOptional = fornecedorRepository.findById(id);
        if (fornecedorOptional.isPresent()) {
            Fornecedor fornecedor = fornecedorOptional.get();
            fornecedor.setNome(fornecedorAtualizado.getNome()); // Atualizar outros campos conforme necessário
            fornecedorRepository.save(fornecedor);
            return new ResponseEntity<>(fornecedor, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete (DELETE)
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deleteFornecedor(@PathVariable Long id) {
        if (fornecedorRepository.existsById(id)) {
            fornecedorRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

