package com.bancoapi.banco_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transacoes")
public class transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @NotNull(message = "Tipo de transação é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoTransacao tipo;

    @Column(length = 500)
    private String descricao;

    @Column(name = "data_transacao", nullable = false)
    private LocalDateTime dataTransacao = LocalDateTime.now();

    // Conta de origem (quem envia)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "conta_origem_id")
    private Conta contaOrigem;

    // Conta de destino (quem recebe)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "conta_destino_id")
    private Conta contaDestino;

    // Enum para tipo de transação
    public enum TipoTransacao {
        TRANSFERENCIA,
        DEPOSITO,
        SAQUE,
        PIX
    }
}