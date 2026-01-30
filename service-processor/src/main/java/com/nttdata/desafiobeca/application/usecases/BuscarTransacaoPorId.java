package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.exceptions.TransacaoNaoEncontradaException;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeProcessor;
import com.nttdata.desafiobeca.domain.Transaction;

import java.util.UUID;

public class BuscarTransacaoPorId {

    private final RepositorioDeProcessor repositorio;

    public BuscarTransacaoPorId(RepositorioDeProcessor repositorio) {
        this.repositorio = repositorio;
    }

    public Transaction executar(UUID id) {
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new TransacaoNaoEncontradaException("Transação com ID " + id + " não encontrada no banco."));
    }
}