package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.exceptions.LimiteExcedidoException;
import com.nttdata.desafiobeca.application.exceptions.SaldoInsuficienteException;
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

        try {
            var conta = contaBancaria.buscarDadosBancarios(transaction.getClienteId());

            // Aqui acontecem suas validações de saldo e limite!
            transaction.processar(conta.saldo(), conta.limiteDiario(), conta.ativa());

            // Só entra aqui se o status for APPROVED (sem exceções disparadas)
            var novoSaldo = transaction.calcularNovoSaldo(conta.saldo());
            contaBancaria.atualizarSaldo(transaction.getClienteId(), novoSaldo);

        } catch (SaldoInsuficienteException | LimiteExcedidoException e) {
            // Se a regra falhar, marcamos como REJECTED
            transaction.rejeitar();
        } finally {
            // Independente de sucesso ou erro de regra, atualizamos o status no banco
            repositorio.atualizarStatus(transaction);
        }
    }
}