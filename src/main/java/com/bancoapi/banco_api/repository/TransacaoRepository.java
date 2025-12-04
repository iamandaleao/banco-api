package com.bancoapi.banco_api.repository;

import com.bancoapi.banco_api.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    // Buscar transações por conta de origem
    List<Transacao> findByContaOrigemId(Long contaOrigemId);

    // Buscar transações por conta de destino
    List<Transacao> findByContaDestinoId(Long contaDestinoId);

    // Buscar todas as transações de uma conta (origem OU destino)
    List<Transacao> findByContaOrigemIdOrContaDestinoId(Long contaOrigemId, Long contaDestinoId);

    // Buscar transações por tipo
    List<Transacao> findByTipo(Transacao.TipoTransacao tipo);

    // Buscar transações por período
    List<Transacao> findByDataTransacaoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    // Extrato: transações de uma conta em um período
    List<Transacao> findByContaOrigemIdOrContaDestinoIdAndDataTransacaoBetween(
            Long contaOrigemId, Long contaDestinoId, LocalDateTime dataInicio, LocalDateTime dataFim);
}