package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.exceptions.TransacaoNaoEncontradaException;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeTransaction;
import com.nttdata.desafiobeca.domain.Transaction;

import java.util.UUID;

public class BuscarTransacaoPorId {
    private final RepositorioDeTransaction repositorio;

    public BuscarTransacaoPorId(RepositorioDeTransaction repositorio) {
        this.repositorio = repositorio;
    }

    public Transaction executa(UUID id) {
        return this.repositorio.buscarPorId(id)
                .orElseThrow(() -> new TransacaoNaoEncontradaException("Transação não encontrada!"));
    }
}
