package com.nttdata.desafiobeca.infra.gateways;

import com.nttdata.desafiobeca.domain.Cliente;
import com.nttdata.desafiobeca.infra.persistence.ClienteEntity;

public class ClienteEntityMapper {

    public ClienteEntity toEntity(Cliente cliente) {
        if (cliente == null) return null;

        ClienteEntity entity = new ClienteEntity(cliente.getNome(), cliente.getEmail(), cliente.getSenha());

        if (cliente.getId() != null) {
            entity.setId(cliente.getId());
        }

        return entity;
    }

    public Cliente toDomain(ClienteEntity entity) {
        if (entity == null) {
            return null; //
        }
        return new Cliente(entity.getId(), entity.getNome(), entity.getEmail(), entity.getSenha());
    }
}
