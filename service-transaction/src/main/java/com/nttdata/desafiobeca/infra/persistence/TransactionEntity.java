package com.nttdata.desafiobeca.infra.persistence;

import com.nttdata.desafiobeca.domain.StatusTransacao;
import com.nttdata.desafiobeca.domain.TipoTransacao;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    private UUID id;

    private Long clienteId;
    private BigDecimal valorOriginal;
    private String moeda;

    @Column(precision = 19, scale = 4)
    private BigDecimal taxaCambio;

    @Column(precision = 19, scale = 2)
    private BigDecimal valorConvertido;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;
    @Enumerated(EnumType.STRING)
    private StatusTransacao status;

    @Column(updatable = false)
    private LocalDateTime dataCriacao;

    public TransactionEntity() {}

    public TransactionEntity(UUID id, Long clienteId, BigDecimal valorOriginal, String moeda, BigDecimal taxaCambio, BigDecimal valorConvertido, TipoTransacao tipo, StatusTransacao status, LocalDateTime dataCriacao) {
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

    public TransactionEntity(Long clienteId, BigDecimal valorOriginal, String moeda, BigDecimal taxaCambio, BigDecimal valorConvertido, TipoTransacao tipo, StatusTransacao status, LocalDateTime dataCriacao) {
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

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
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
}
