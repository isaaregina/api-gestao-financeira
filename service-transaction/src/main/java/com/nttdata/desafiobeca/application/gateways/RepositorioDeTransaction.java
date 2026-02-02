package com.nttdata.desafiobeca.application.gateways;

import com.nttdata.desafiobeca.domain.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositorioDeTransaction {
    Transaction cadastrar(Transaction transaction);

    Transaction atualizar(Transaction transaction);

    Optional<Transaction> buscarPorId(UUID id);

    List<Transaction> listarPorCliente(Long clienteId);

    List<Transaction> listarTodas();

    void excluirTransacao(UUID id);
}
