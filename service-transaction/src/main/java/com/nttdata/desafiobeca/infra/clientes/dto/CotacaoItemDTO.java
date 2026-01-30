package com.nttdata.desafiobeca.infra.clientes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CotacaoItemDTO(
        @JsonProperty("cotacao_compra") // Nome exato que vem no JSON
        java.math.BigDecimal cotacaoCompra,

        @JsonProperty("cotacao_venda")
        java.math.BigDecimal cotacaoVenda,

        @JsonProperty("data_hora_cotacao")
        String dataHoraCotacao
) {}
