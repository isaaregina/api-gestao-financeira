package com.nttdata.desafiobeca.application.gateways.dto;

import java.math.BigDecimal;

public record ContaBancariaDTO(
        Long clienteId,
        BigDecimal saldo,
        BigDecimal limiteDiario,
        boolean ativa
) {}
