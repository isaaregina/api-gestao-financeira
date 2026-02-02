package com.nttdata.desafiobeca.infra.messaging;

import com.nttdata.desafiobeca.application.exceptions.LimiteExcedidoException;
import com.nttdata.desafiobeca.application.exceptions.SaldoInsuficienteException;
import com.nttdata.desafiobeca.application.services.TransactionProcessor;
import com.nttdata.desafiobeca.application.usecases.AlterarStatusTransacao;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeProcessor;
import com.nttdata.desafiobeca.infra.messaging.kafka.dto.TransactionEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@RequiredArgsConstructor
public class TransactionConsumer {

    private final TransactionProcessor transactionProcessor;

    @KafkaListener(topics = "transaction.requested", groupId = "processor-group")
    public void consumir(TransactionEventDTO event) {
        transactionProcessor.processar(event.id());
    }
}
