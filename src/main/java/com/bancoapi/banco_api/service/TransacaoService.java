package com.bancoapi.banco_api.service;

import com.bancoapi.banco_api.model.Conta;
import com.bancoapi.banco_api.model.Transacao;
import com.bancoapi.banco_api.repository.ContaRepository;
import com.bancoapi.banco_api.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    // Realizar transferência entre contas
    @Transactional
    public Transacao realizarTransferencia(Long contaOrigemId, Long contaDestinoId,
                                           BigDecimal valor, String descricao) {
        // Buscar contas
        Conta contaOrigem = contaRepository.findById(contaOrigemId)
                .orElseThrow(() -> new RuntimeException("Conta de origem não encontrada"));

        Conta contaDestino = contaRepository.findById(contaDestinoId)
                .orElseThrow(() -> new RuntimeException("Conta de destino não encontrada"));

        // Validações
        if (!contaOrigem.getAtiva()) {
            throw new RuntimeException("Conta de origem está inativa");
        }

        if (!contaDestino.getAtiva()) {
            throw new RuntimeException("Conta de destino está inativa");
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor deve ser maior que zero");
        }

        if (contaOrigem.getSaldo().compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        if (contaOrigemId.equals(contaDestinoId)) {
            throw new RuntimeException("Não é possível transferir para a mesma conta");
        }

        // Realizar transferência
        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

        // Salvar contas atualizadas
        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        // Registrar transação
        Transacao transacao = new Transacao();
        transacao.setValor(valor);
        transacao.setTipo(Transacao.TipoTransacao.TRANSFERENCIA);
        transacao.setDescricao(descricao);
        transacao.setContaOrigem(contaOrigem);
        transacao.setContaDestino(contaDestino);
        transacao.setDataTransacao(LocalDateTime.now());

        return transacaoRepository.save(transacao);
    }

    // Buscar todas as transações
    public List<Transacao> listarTodas() {
        return transacaoRepository.findAll();
    }

    // Buscar transação por ID
    public Transacao buscarPorId(Long id) {
        return transacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
    }

    // Buscar transações de uma conta (origem)
    public List<Transacao> buscarPorContaOrigem(Long contaId) {
        return transacaoRepository.findByContaOrigemId(contaId);
    }

    // Buscar transações de uma conta (destino)
    public List<Transacao> buscarPorContaDestino(Long contaId) {
        return transacaoRepository.findByContaDestinoId(contaId);
    }

    // Extrato completo de uma conta (todas as transações)
    public List<Transacao> obterExtratoConta(Long contaId) {
        return transacaoRepository.findByContaOrigemIdOrContaDestinoId(contaId, contaId);
    }

    // Extrato por período
    public List<Transacao> obterExtratoPorPeriodo(Long contaId,
                                                  LocalDateTime dataInicio,
                                                  LocalDateTime dataFim) {
        return transacaoRepository.findByContaOrigemIdOrContaDestinoIdAndDataTransacaoBetween(
                contaId, contaId, dataInicio, dataFim);
    }

    // Buscar por tipo
    public List<Transacao> buscarPorTipo(Transacao.TipoTransacao tipo) {
        return transacaoRepository.findByTipo(tipo);
    }
}