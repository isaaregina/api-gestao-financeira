package com.nttdata.desafiobeca.domain;

import com.nttdata.desafiobeca.application.exceptions.LimiteExcedidoException;
import com.nttdata.desafiobeca.application.exceptions.SaldoInsuficienteException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private final UUID id;
    private final Long clienteId;
    private final BigDecimal valorOriginal;
    private final String moeda;
    private final BigDecimal taxaCambio;
    private final BigDecimal valorConvertido;
    private final TipoTransacao tipo;
    private StatusTransacao status;
    private final LocalDateTime dataCriacao;

    public Transaction(UUID id, Long clienteId, BigDecimal valorOriginal, String moeda,
                       BigDecimal taxaCambio, BigDecimal valorConvertido,
                       TipoTransacao tipo, StatusTransacao status, LocalDateTime dataCriacao) {
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

    public void processar(BigDecimal saldoAtual, BigDecimal limiteDisponivel, boolean contaAtiva) {
        if (!contaAtiva) {
            this.status = StatusTransacao.REJECTED;
            // Você pode lançar uma ContaInativaException aqui se quiser que o Global trate
            return;
        }
        if (this.tipo == TipoTransacao.DEPOSIT) {
            this.status = StatusTransacao.APPROVED;
        } else {
            // Saques/Pagamentos exigem validação de saldo e limite
            validarRegras(saldoAtual, limiteDisponivel);
            this.status = StatusTransacao.APPROVED;
        }
    }

    public void validarRegras(BigDecimal saldo, BigDecimal limite) {
        if (saldo.compareTo(this.valorConvertido) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }
        if (this.valorConvertido.compareTo(limite) > 0) {
            throw new LimiteExcedidoException("Limite excedido");
        }
    }

    public void rejeitar() {
        this.status = StatusTransacao.REJECTED;
    }

    public BigDecimal calcularNovoSaldo(BigDecimal saldoAtual) {
        if (this.tipo == TipoTransacao.DEPOSIT) {
            return saldoAtual.add(this.valorConvertido);
        }
        return saldoAtual.subtract(this.valorConvertido);
    }
}
