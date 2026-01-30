package com.nttdata.desafiobeca.infra.gateways;

import com.nttdata.desafiobeca.domain.Transaction;
import com.nttdata.desafiobeca.infra.persistence.TransactionEntity;

public class TransactionEntityMapper {

    public TransactionEntity toEntity(Transaction transaction) {
        if (transaction == null) return null;

        TransactionEntity entity = new TransactionEntity(transaction.getClienteId(), transaction.getValorOriginal(), transaction.getMoeda(), transaction.getTaxaCambio(), transaction.getValorConvertido(), transaction.getTipo(), transaction.getStatus(), transaction.getDataCriacao());

        if (transaction.getId() != null)  entity.setId(transaction.getId());

        return entity;
    }

    public Transaction toDomain(TransactionEntity entity) {
        if (entity == null) return null;

        return new Transaction(entity.getId(), entity.getClienteId(), entity.getValorOriginal(), entity.getMoeda(), entity.getTaxaCambio(), entity.getValorConvertido(), entity.getTipo(), entity.getStatus(), entity.getDataCriacao());
    }
}
