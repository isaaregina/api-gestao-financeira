package com.nttdata.desafiobeca.application.gateways;

import com.nttdata.desafiobeca.domain.Transaction;

import java.util.Optional;
import java.util.UUID;

public interface RepositorioDeProcessor {
    Optional<Transaction> buscarPorId(UUID id);
    void atualizarStatus(Transaction transaction);
}
