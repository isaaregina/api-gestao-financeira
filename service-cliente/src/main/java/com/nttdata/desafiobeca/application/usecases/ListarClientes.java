package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.exceptions.ClienteNaoEncontradoException;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeCliente;
import com.nttdata.desafiobeca.domain.Cliente;

import java.util.List;

public class ListarClientes {
    private final RepositorioDeCliente repositorio;

    public ListarClientes(RepositorioDeCliente repositorio) {
        this.repositorio = repositorio;
    }

    public List<Cliente> listaCliente() {
        List<Cliente> clientes = this.repositorio.listarTodos();

        if (clientes.isEmpty()) {
            throw new ClienteNaoEncontradoException("Nenhum cliente cadastrado no sistema!");
        }

        return clientes;
    }
}
