package com.fatec.demo.controllers;

import com.fatec.demo.entities.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

//http://localhost:8080/api/contas-pagar

@RestController
@RequestMapping("/api/contas-pagar")
public class ContaPagarController {

    private final ContaPagarRepository contaPagarRepository;
    private final FornecedorRepository fornecedor;

    public ContaPagarController(ContaPagarRepository contaPagarRepository, FornecedorRepository fornecedor) {
        this.contaPagarRepository = contaPagarRepository;
        this.fornecedor = fornecedor;
    }

    @PostMapping("/criar")
    public ResponseEntity<ContaPagar> criarContaPagar(@RequestBody ContaPagar contaPagar) {
        
        // Validando datas
        if (contaPagar.getEmissao().isAfter(contaPagar.getVencimento())) {
            throw new IllegalArgumentException("A data de emissão não pode ser posterior à data de vencimento.");
        }

        // Verificando o valor
        if (contaPagar.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da conta a pagar deve ser positivo (maior que zero).");
        }

        // Busca o fornecedor pelo ID para ligar à conta a pagar
        Long fornecedorId = contaPagar.getFornecedor().getId();
        Fornecedor fornecedorExistente = fornecedor.findById(fornecedorId)
            .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado. Por favor, cadastre o fornecedor antes de associá-lo a uma conta a pagar."));
        
        contaPagar.setFornecedor(fornecedorExistente);

        ContaPagar contaSalva = contaPagarRepository.save(contaPagar);
        return ResponseEntity.ok(contaSalva);
    }


    @GetMapping("/listar")
    public List<ContaPagar> listarContasPagar() {
        return contaPagarRepository.findAll();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<ContaPagar> obterContaPagar(@PathVariable Long id) {
        return contaPagarRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ContaPagar> atualizarContaPagar(@PathVariable Long id, @RequestBody ContaPagar contaPagarAtualizada) {
        return contaPagarRepository.findById(id)
                .map(contaPagar -> {
                    // Validando as datas
                    if (contaPagarAtualizada.getEmissao().isAfter(contaPagarAtualizada.getVencimento())) {
                        throw new IllegalArgumentException("A data de emissão não pode ser posterior à data de vencimento.");
                    }

                    // Validando o valor
                    if (contaPagarAtualizada.getValor().compareTo(BigDecimal.ZERO) <= 0) {
                        throw new IllegalArgumentException("O valor da conta a pagar deve ser positivo.");
                    }

                    // Verificando fornecedor
                    Long fornecedorId = contaPagarAtualizada.getFornecedor().getId();
                    Fornecedor fornecedorExistente = fornecedor.findById(fornecedorId)
                            .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado. Por favor, forneça um fornecedor existente."));

                    // Atualizando os dados da conta
                    contaPagar.setEmissao(contaPagarAtualizada.getEmissao());
                    contaPagar.setVencimento(contaPagarAtualizada.getVencimento());
                    contaPagar.setValor(contaPagarAtualizada.getValor());
                    contaPagar.setFornecedor(fornecedorExistente);

                    return ResponseEntity.ok(contaPagarRepository.save(contaPagar));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarContaPagar(@PathVariable Long id) {
        if (!contaPagarRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        contaPagarRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
