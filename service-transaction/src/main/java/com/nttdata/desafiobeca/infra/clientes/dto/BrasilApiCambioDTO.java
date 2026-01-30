package com.nttdata.desafiobeca.infra.clientes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

// Este é o nosso "molde".
// Quando a BrasilAPI responder, o Spring vai tentar encaixar os valores aqui.
public record BrasilApiCambioDTO(
        @JsonProperty("cotacoes") // Corresponde à lista na imagem image_33e57f.png
        List<CotacaoItemDTO> cotacoes,
        String moeda
) {}
