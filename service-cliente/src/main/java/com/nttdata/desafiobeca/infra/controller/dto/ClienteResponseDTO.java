package com.nttdata.desafiobeca.infra.controller.dto;

import com.nttdata.desafiobeca.domain.Cliente;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String email
) {

    public ClienteResponseDTO (Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail()
        );
    }
}
