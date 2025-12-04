package com.bancoapi.banco_api.controller;

import com.bancoapi.banco_api.model.Transacao;
import com.bancoapi.banco_api.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    // POST - Realizar transferência
    @PostMapping("/transferencia")
    public ResponseEntity<Transacao> realizarTransferencia(
            @RequestParam Long contaOrigemId,
            @RequestParam Long contaDestinoId,
            @RequestParam BigDecimal valor,
            @RequestParam(required = false) String descricao) {
        try {
            Transacao transacao = transacaoService.realizarTransferencia(
                    contaOrigemId, contaDestinoId, valor, descricao);
            return ResponseEntity.status(HttpStatus.CREATED).body(transacao);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // GET - Listar todas as transações
    @GetMapping
    public ResponseEntity<List<Transacao>> listarTodas() {
        List<Transacao> transacoes = transacaoService.listarTodas();
        return ResponseEntity.ok(transacoes);
    }

    // GET - Buscar transação por ID
    @GetMapping("/{id}")
    public ResponseEntity<Transacao> buscarPorId(@PathVariable Long id) {
        try {
            Transacao transacao = transacaoService.buscarPorId(id);
            return ResponseEntity.ok(transacao);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET - Buscar transações por conta de origem
    @GetMapping("/origem/{contaId}")
    public ResponseEntity<List<Transacao>> buscarPorContaOrigem(@PathVariable Long contaId) {
        List<Transacao> transacoes = transacaoService.buscarPorContaOrigem(contaId);
        return ResponseEntity.ok(transacoes);
    }

    // GET - Buscar transações por conta de destino
    @GetMapping("/destino/{contaId}")
    public ResponseEntity<List<Transacao>> buscarPorContaDestino(@PathVariable Long contaId) {
        List<Transacao> transacoes = transacaoService.buscarPorContaDestino(contaId);
        return ResponseEntity.ok(transacoes);
    }

    // GET - Extrato completo de uma conta
    @GetMapping("/extrato/{contaId}")
    public ResponseEntity<List<Transacao>> obterExtrato(@PathVariable Long contaId) {
        List<Transacao> transacoes = transacaoService.obterExtratoConta(contaId);
        return ResponseEntity.ok(transacoes);
    }

    // GET - Extrato por período
    @GetMapping("/extrato/{contaId}/periodo")
    public ResponseEntity<List<Transacao>> obterExtratoPorPeriodo(
            @PathVariable Long contaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        List<Transacao> transacoes = transacaoService.obterExtratoPorPeriodo(contaId, dataInicio, dataFim);
        return ResponseEntity.ok(transacoes);
    }

    // GET - Buscar por tipo de transação
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Transacao>> buscarPorTipo(@PathVariable Transacao.TipoTransacao tipo) {
        List<Transacao> transacoes = transacaoService.buscarPorTipo(tipo);
        return ResponseEntity.ok(transacoes);
    }
}