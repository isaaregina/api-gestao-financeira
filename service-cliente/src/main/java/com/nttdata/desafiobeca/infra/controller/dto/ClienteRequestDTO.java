package com.nttdata.desafiobeca.infra.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequestDTO(
        @NotBlank
        String nome,
        @Email
        @NotBlank
        String email,
        @NotBlank
        String senha
) {
}
