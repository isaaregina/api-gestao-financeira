package com.nttdata.desafiobeca.infra.messaging.kafka.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionEventDTO(
        UUID id,
        Long clienteId,
        BigDecimal valorConvertido,
        String moeda,
        String tipo,
        String status
) {}
