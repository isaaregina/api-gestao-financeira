package com.nttdata.desafiobeca.domain;

public enum StatusTransacao {
    PENDING,    // Quando a transação é criada e enviada ao Kafka
    APPROVED,   // Após validar saldo e converter moeda
    REJECTED    // Caso não haja saldo ou ocorra erro externo
}
