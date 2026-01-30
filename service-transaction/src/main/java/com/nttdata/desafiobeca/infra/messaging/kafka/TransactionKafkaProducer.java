package com.nttdata.desafiobeca.infra.messaging.kafka;

import com.nttdata.desafiobeca.application.gateways.EnviarEventoTransaction;
import com.nttdata.desafiobeca.domain.Transaction;
import com.nttdata.desafiobeca.infra.messaging.kafka.dto.TransactionEventDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionKafkaProducer implements EnviarEventoTransaction {

    private final KafkaTemplate<String, TransactionEventDTO> kafkaTemplate;
    private static final String TOPIC = "transaction.requested";

    public TransactionKafkaProducer(KafkaTemplate<String, TransactionEventDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void enviar(Transaction transaction) {
        // Transformamos o objeto de Domínio em um DTO de Evento (pacote de dados)
        TransactionEventDTO event = new TransactionEventDTO(
                transaction.getId(),
                transaction.getClienteId(),
                transaction.getValorConvertido(),
                transaction.getMoeda(),
                transaction.getTipo().name(),
                transaction.getStatus().name()
        );

        // Enviamos para o Kafka usando o ID da transação como chave (para garantir ordem)
        kafkaTemplate.send(TOPIC, event.id().toString(), event);
    }
}