package com.nttdata.desafiobeca.infra.controller.dto;

import com.nttdata.desafiobeca.domain.StatusTransacao;
import com.nttdata.desafiobeca.domain.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponseDTO(
        UUID id,
        Long clienteId,
        BigDecimal valorOriginal,
        String moeda,
        StatusTransacao status,
        LocalDateTime dataCriacao,
        String tipo
) {
    public TransactionResponseDTO(Transaction t) {
        this(
                t.getId(),
                t.getClienteId(),
                t.getValorOriginal(),
                t.getMoeda(),
                t.getStatus(),
                t.getDataCriacao(),
                t.getTipo().name()
        );
    }
}
