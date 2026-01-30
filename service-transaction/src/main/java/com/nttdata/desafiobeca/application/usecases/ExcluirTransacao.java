package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.exceptions.TransacaoNaoEncontradaException;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeTransaction;

import java.util.UUID;

public class ExcluirTransacao {
    private final RepositorioDeTransaction repositorio;

    public ExcluirTransacao(RepositorioDeTransaction repositorio) {
        this.repositorio = repositorio;
    }

    public void executa(UUID id) {
        var transacao = repositorio.buscarPorId(id)
                .orElseThrow(() -> new TransacaoNaoEncontradaException("Transação não encontrada!"));

        this.repositorio.excluirTransacao(transacao.getId());
    }
}
