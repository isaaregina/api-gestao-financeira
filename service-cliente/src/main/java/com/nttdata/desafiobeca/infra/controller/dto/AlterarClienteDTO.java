package com.nttdata.desafiobeca.infra.controller.dto;

import java.time.LocalDate;

public record AlterarClienteDTO(
        Long id,
        String nome,
        String email,
        LocalDate dataNascimento
) {
}
