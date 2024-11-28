package com.fatec.demo.controllers;

import com.fatec.demo.entities.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

//http://localhost:8080/api/contas-receber

@RestController
@RequestMapping("/api/contas-receber")
public class ContaReceberController {

    private final ContaReceberRepository contaReceberRepository;
    private final ClienteRepository cliente;

    public ContaReceberController(ContaReceberRepository contaReceberRepository, ClienteRepository cliente) {
        this.contaReceberRepository = contaReceberRepository;
        this.cliente = cliente;
    }

    @PostMapping("/criar")
    public ResponseEntity<ContaReceber> criarContaReceber(@RequestBody ContaReceber contaReceber) {
        
        // Verifica se a validade das datas
        if (contaReceber.getEmissao().isAfter(contaReceber.getVencimento())) {
            throw new IllegalArgumentException("A data de emissão não pode ser posterior à data de vencimento.");
        }

        // Verifica se o valor é positivo
        if (contaReceber.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da conta a receber deve ser positivo.");
        }

        // Busca o cliente pelo ID para ligar à conta a receber
        Long clienteId = contaReceber.getCliente().getId();
        Cliente clienteExistente = cliente.findById(clienteId)
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado. Por favor, cadastre o cliente antes de associá-lo a uma conta a receber."));
        
        contaReceber.setCliente(clienteExistente);

        // Salva a conta a receber no repositório
        ContaReceber contaReceberSalva = contaReceberRepository.save(contaReceber);
        return ResponseEntity.ok(contaReceberSalva);
    }


    @GetMapping("/listar")
    public List<ContaReceber> listarContasReceber() {
        return contaReceberRepository.findAll();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<ContaReceber> obterContaReceber(@PathVariable Long id) {
        return contaReceberRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ContaReceber> atualizarContaReceber(@PathVariable Long id, @RequestBody ContaReceber contaReceberAtualizada) {
        return contaReceberRepository.findById(id)
                .map(contaReceber -> {
                    // Validando datas
                    if (contaReceberAtualizada.getEmissao().isAfter(contaReceberAtualizada.getVencimento())) {
                        throw new IllegalArgumentException("A data de emissão não pode ser posterior à data de vencimento.");
                    }

                    // Validando valor
                    if (contaReceberAtualizada.getValor().compareTo(BigDecimal.ZERO) <= 0) {
                        throw new IllegalArgumentException("O valor da conta a receber deve ser positivo.");
                    }

                    // Verificando cliente
                    Long clienteId = contaReceberAtualizada.getCliente().getId();
                    Cliente clienteExistente = cliente.findById(clienteId)
                            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado. Por favor, forneça um cliente existente."));

                    // Atualizando
                    contaReceber.setEmissao(contaReceberAtualizada.getEmissao());
                    contaReceber.setVencimento(contaReceberAtualizada.getVencimento());
                    contaReceber.setValor(contaReceberAtualizada.getValor());
                    contaReceber.setCliente(clienteExistente);

                    return ResponseEntity.ok(contaReceberRepository.save(contaReceber));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarContaReceber(@PathVariable Long id) {
        if (!contaReceberRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        contaReceberRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
