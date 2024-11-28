package com.fatec.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fatec.demo.entities.Cliente;
import java.util.List;
import java.util.Optional;

//http://localhost:8080/api/clientes

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // Create (POST)
    @PostMapping("/criar")
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        //Salvando Cliente
        Cliente clienteCriado = clienteRepository.save(cliente);
        return new ResponseEntity<>(clienteCriado, HttpStatus.CREATED);
    }

    // Read (GET)
    @GetMapping("/listar")
    public ResponseEntity<List<Cliente>> getClientes() {
        //Listando clientes
        List<Cliente> clientes = clienteRepository.findAll();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    // Get por ID
    @GetMapping("/listar/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable Long id) {
        //Listando por ID
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update (PUT)
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Cliente> atualizaCliente(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
        //Atualizando
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            cliente.setNome(clienteAtualizado.getNome());
            clienteRepository.save(cliente);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Delete (DELETE)
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
