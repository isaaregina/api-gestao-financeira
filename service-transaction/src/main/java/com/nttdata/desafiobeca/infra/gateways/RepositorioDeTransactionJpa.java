package com.nttdata.desafiobeca.infra.gateways;

import com.nttdata.desafiobeca.application.gateways.RepositorioDeTransaction;
import com.nttdata.desafiobeca.domain.Transaction;
import com.nttdata.desafiobeca.infra.persistence.TransactionEntity;
import com.nttdata.desafiobeca.infra.persistence.TransactionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class RepositorioDeTransactionJpa implements RepositorioDeTransaction {

    private final TransactionRepository repositorio;
    private final TransactionEntityMapper mapper;

    public RepositorioDeTransactionJpa(TransactionRepository repositorio, TransactionEntityMapper mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }

    @Override
    public Transaction cadastrar(Transaction transaction) {
        TransactionEntity entity = mapper.toEntity(transaction);
        TransactionEntity salva = repositorio.save(entity);
        return mapper.toDomain(salva);
    }

    @Override
    public Transaction atualizar(Transaction transaction) {
        // No Spring Data JPA, o .save() faz update se o ID já existir
        TransactionEntity entity = mapper.toEntity(transaction);
        TransactionEntity atualizada = repositorio.save(entity);
        return mapper.toDomain(atualizada);
    }

    @Override
    public Optional<Transaction> buscarPorId(UUID id) {
        return repositorio.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Transaction> listarPorCliente(Long clienteId) {
        // Aqui usamos o método que você criou no JpaRepository
        return repositorio.findByClienteId(clienteId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> listarTodas() {
        return repositorio.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void excluirTransacao(UUID id) {
        repositorio.deleteById(id);
    }
}
