package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.gateways.RepositorioDeTransaction;
import com.nttdata.desafiobeca.domain.Transaction;

import java.util.List;

public class ListaTransacoesPorCliente {
    private final RepositorioDeTransaction repositorio;

    public ListaTransacoesPorCliente(RepositorioDeTransaction repositorio) {
        this.repositorio = repositorio;
    }

    public List<Transaction> executa(Long clienteId) {
        return this.repositorio.listarPorCliente(clienteId);
    }
}
