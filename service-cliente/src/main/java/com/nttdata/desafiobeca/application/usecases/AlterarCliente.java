package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.exceptions.ClienteNaoEncontradoException;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeCliente;
import com.nttdata.desafiobeca.domain.Cliente;

public class AlterarCliente {
    private final RepositorioDeCliente repositorio;

    public AlterarCliente(RepositorioDeCliente repositorio) {
        this.repositorio = repositorio;
    }

    public Cliente alteraCliente(Long id, String nome, String email) {
        var cliente = repositorio.buscarPorId(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado!"));

        cliente.atualizarDados(nome, email);

        return this.repositorio.alteraCliente(cliente);
    }
}
