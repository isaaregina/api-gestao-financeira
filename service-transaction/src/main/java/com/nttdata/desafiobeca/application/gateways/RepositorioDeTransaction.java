package com.nttdata.desafiobeca.application.gateways;

import com.nttdata.desafiobeca.domain.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositorioDeTransaction {
    // Para criar uma transação nova (POST)
    Transaction cadastrar(Transaction transaction);

    // Para mudar status ou atualizar valores (PUT/Kafka)
    Transaction atualizar(Transaction transaction);

    Optional<Transaction> buscarPorId(UUID id);

    List<Transaction> listarPorCliente(Long clienteId);

    List<Transaction> listarTodas();

    void excluirTransacao(UUID id);
}
