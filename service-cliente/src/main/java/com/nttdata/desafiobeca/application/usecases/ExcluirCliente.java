package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.exceptions.ClienteNaoEncontradoException;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeCliente;

public class ExcluirCliente {
    private final RepositorioDeCliente repositorio;

    public ExcluirCliente(RepositorioDeCliente repositorio) {
        this.repositorio = repositorio;
    }

    public void excluirCliente(Long id) {
        var cliente = repositorio.buscarPorId(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado!"));

        this.repositorio.excluiClientePorId(cliente.getId());
    }
}
