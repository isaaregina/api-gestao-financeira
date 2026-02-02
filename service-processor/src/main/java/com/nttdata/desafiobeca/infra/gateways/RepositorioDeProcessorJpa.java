package com.nttdata.desafiobeca.infra.gateways;

import com.nttdata.desafiobeca.application.gateways.RepositorioDeProcessor;
import com.nttdata.desafiobeca.domain.Transaction;
import com.nttdata.desafiobeca.infra.persistence.TransactionEntity;
import com.nttdata.desafiobeca.infra.persistence.TransactionRepository;

import java.util.Optional;
import java.util.UUID;

public class RepositorioDeProcessorJpa implements RepositorioDeProcessor {

    private final TransactionRepository jpaRepository;
    private final TransactionEntityMapper mapper;

    public RepositorioDeProcessorJpa(TransactionRepository jpaRepository, TransactionEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Transaction> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void atualizarStatus(Transaction transaction) {
        TransactionEntity entity = mapper.toEntity(transaction);
        jpaRepository.save(entity);
    }
}