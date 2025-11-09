package com.bancoapi.banco_api.controller;

import com.bancoapi.banco_api.model.Conta;
import com.bancoapi.banco_api.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    // POST - Criar conta para um cliente
    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<Conta> criarConta(
            @Valid @RequestBody Conta conta,
            @PathVariable Long clienteId) {
        try {
            Conta novaConta = contaService.criarConta(conta, clienteId);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // GET - Listar todas as contas
    @GetMapping
    public ResponseEntity<List<Conta>> listarTodas() {
        List<Conta> contas = contaService.listarTodas();
        return ResponseEntity.ok(contas);
    }

    // GET - Listar apenas contas ativas
    @GetMapping("/ativas")
    public ResponseEntity<List<Conta>> listarAtivas() {
        List<Conta> contas = contaService.listarAtivas();
        return ResponseEntity.ok(contas);
    }

    // GET - Buscar conta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscarPorId(@PathVariable Long id) {
        return contaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET - Buscar conta por número
    @GetMapping("/numero/{numeroConta}")
    public ResponseEntity<Conta> buscarPorNumero(@PathVariable String numeroConta) {
        return contaService.buscarPorNumero(numeroConta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET - Listar contas de um cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Conta>> listarContasPorCliente(@PathVariable Long clienteId) {
        List<Conta> contas = contaService.listarContasPorCliente(clienteId);
        return ResponseEntity.ok(contas);
    }

    // GET - Listar contas ativas de um cliente
    @GetMapping("/cliente/{clienteId}/ativas")
    public ResponseEntity<List<Conta>> listarContasAtivasPorCliente(@PathVariable Long clienteId) {
        List<Conta> contas = contaService.listarContasAtivasPorCliente(clienteId);
        return ResponseEntity.ok(contas);
    }

    // GET - Consultar saldo
    @GetMapping("/{id}/saldo")
    public ResponseEntity<BigDecimal> consultarSaldo(@PathVariable Long id) {
        try {
            BigDecimal saldo = contaService.consultarSaldo(id);
            return ResponseEntity.ok(saldo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PATCH - Desativar conta
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativarConta(@PathVariable Long id) {
        try {
            contaService.desativarConta(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PATCH - Reativar conta
    @PatchMapping("/{id}/reativar")
    public ResponseEntity<Void> reativarConta(@PathVariable Long id) {
        try {
            contaService.reativarConta(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}