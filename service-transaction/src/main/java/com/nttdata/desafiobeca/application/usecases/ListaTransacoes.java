package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.exceptions.TransacaoNaoEncontradaException;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeTransaction;
import com.nttdata.desafiobeca.domain.Transaction;

import java.util.List;

public class ListaTransacoes {
    private final RepositorioDeTransaction repositorio;

    public ListaTransacoes(RepositorioDeTransaction repositorio) {
        this.repositorio = repositorio;
    }

    public List<Transaction> executa() {
        List<Transaction> transacoes = this.repositorio.listarTodas();

        if (transacoes.isEmpty()) {
            throw new TransacaoNaoEncontradaException("Nenhum cliente cadastrado no sistema!");
        }

        return transacoes;
    }
}
