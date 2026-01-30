package com.nttdata.desafiobeca.infra.controller.dto;

import com.nttdata.desafiobeca.domain.TipoTransacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionRequestDTO(
//        @NotNull(message = "O ID do cliente é obrigatório")
//        Long clienteId,
        @NotNull(message = "O valor original é obrigatório")
        @Positive(message = "O valor deve ser maior que zero")
        BigDecimal valorOriginal,
        @NotBlank(message = "A moeda é obrigatória")
        String moeda,
        @NotNull(message = "O tipo de transação (DEPOSIT/WITHDRAWAL) é obrigatório")        TipoTransacao tipo
) {
}
