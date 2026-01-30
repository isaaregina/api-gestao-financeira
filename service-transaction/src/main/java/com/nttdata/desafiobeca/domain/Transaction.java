package com.nttdata.desafiobeca.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private Long clienteId;

    private BigDecimal valorOriginal; // Valor que o usuário quer
    private String moeda; // Necessário para BrasilAPI (Moeda)
    // Campos que serão preenchidos pela BrasilAPI depois:
    private BigDecimal taxaCambio;
    private BigDecimal valorConvertido;

    private TipoTransacao tipo; // Apenas DEPOSIT ou WITHDRAWAL
    private StatusTransacao status; // PENDING, APPROVED, REJECTED
    private LocalDateTime dataCriacao;

    public Transaction(UUID id, Long clienteId, BigDecimal valorOriginal, String moeda, BigDecimal taxaCambio, BigDecimal valorConvertido, TipoTransacao tipo, StatusTransacao status, LocalDateTime dataCriacao) {
        this.id = id;
        this.clienteId = clienteId;
        this.valorOriginal = valorOriginal;
        this.moeda = moeda;
        this.taxaCambio = taxaCambio;
        this.valorConvertido = valorConvertido;
        this.tipo = tipo;
        this.status = status;
        this.dataCriacao = dataCriacao;
    }

    public UUID getId() {
        return id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public BigDecimal getValorOriginal() {
        return valorOriginal;
    }

    public String getMoeda() {
        return moeda;
    }

    public BigDecimal getTaxaCambio() {
        return taxaCambio;
    }

    public BigDecimal getValorConvertido() {
        return valorConvertido;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public StatusTransacao getStatus() {
        return status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void prepararNovaTransacao() {
        this.id = UUID.randomUUID();
        this.status = StatusTransacao.PENDING; // O Kafka ditará o próximo passo
        this.dataCriacao = LocalDateTime.now();
    }

    public void aplicarConversao(BigDecimal taxa) {
        if (taxa == null || taxa.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Taxa de câmbio inválida");
        }
        this.taxaCambio = taxa;
        this.valorConvertido = this.valorOriginal
                .multiply(taxa)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
