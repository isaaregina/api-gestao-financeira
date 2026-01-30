package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.exceptions.TransacaoNaoEncontradaException;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeContaBancaria;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeProcessor;
import com.nttdata.desafiobeca.domain.StatusTransacao;
import com.nttdata.desafiobeca.domain.Transaction;

import java.util.UUID;

public class AlterarStatusTransacao {

    private final RepositorioDeProcessor repositorio;
    private final RepositorioDeContaBancaria contaBancaria;

    public AlterarStatusTransacao(RepositorioDeProcessor repositorio, RepositorioDeContaBancaria contaBancaria) {
        this.repositorio = repositorio;
        this.contaBancaria = contaBancaria;
    }

    public void executar(UUID transactionId) {
        Transaction transaction = repositorio.buscarPorId(transactionId)
                .orElseThrow(() -> new TransacaoNaoEncontradaException("Transação não encontrada"));

        var conta = contaBancaria.buscarDadosBancarios(transaction.getClienteId());

        transaction.processar(conta.saldo(), conta.limiteDiario(), conta.ativa());

        if (transaction.getStatus().equals(StatusTransacao.APPROVED)) {
            var novoSaldo = transaction.calcularNovoSaldo(conta.saldo());

            contaBancaria.atualizarSaldo(transaction.getClienteId(), novoSaldo);
        }

        repositorio.atualizarStatus(transaction);
    }
}