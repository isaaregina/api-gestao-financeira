package com.nttdata.desafiobeca.infra.controller.dto;

import java.math.BigDecimal;

public record AccountResponseDTO(
        Long clienteId,
        BigDecimal saldo,
        BigDecimal limiteDiario,
        boolean ativa
) {}
