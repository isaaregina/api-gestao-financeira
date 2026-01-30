package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.exceptions.ClienteNaoEncontradoException;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeCliente;
import com.nttdata.desafiobeca.domain.Cliente;

public class BuscarClientePorId {
    private final RepositorioDeCliente repositorio;

    public BuscarClientePorId(RepositorioDeCliente repositorio) {
        this.repositorio = repositorio;
    }

    public Cliente buscarClientePorId(Long id) {
        return this.repositorio.buscarPorId(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado!"));
    }
}
