package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.exceptions.AcessoException;
import com.nttdata.desafiobeca.application.exceptions.TransacaoNaoEncontradaException;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeTransaction;
import com.nttdata.desafiobeca.domain.Transaction;

public class AtualizarTransacao {
    private final RepositorioDeTransaction repositorio;

    public AtualizarTransacao(RepositorioDeTransaction repositorio) {
        this.repositorio = repositorio;
    }

    public Transaction executa(Transaction novosDados, Long idRequisitante) {
        Transaction existente = repositorio.buscarPorId(novosDados.getId())
                .orElseThrow(() -> new TransacaoNaoEncontradaException("Transação não encontrada!"));

        if (!existente.getClienteId().equals(idRequisitante)) {
            throw new AcessoException("Acesso negado: Você não pode alterar transações de outro usuário.");
        }

        Transaction transacaoParaSalvar = new Transaction(
                existente.getId(),
                existente.getClienteId(), // Mantemos o dono original do banco por segurança
                novosDados.getValorOriginal(),
                novosDados.getMoeda(),
                existente.getTaxaCambio(),
                existente.getValorConvertido(),
                novosDados.getTipo(),
                existente.getStatus(),
                existente.getDataCriacao()
        );

        return this.repositorio.atualizar(transacaoParaSalvar);
    }
}
