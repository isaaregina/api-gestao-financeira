package com.nttdata.desafiobeca.infra.messaging;

import com.nttdata.desafiobeca.application.exceptions.LimiteExcedidoException;
import com.nttdata.desafiobeca.application.exceptions.SaldoInsuficienteException;
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

    private final AlterarStatusTransacao alterarStatusTransacao;
    private final RepositorioDeProcessor repositorio; // Para salvar o erro se o use case falhar

    @KafkaListener(topics = "transaction.requested", groupId = "processor-group")
    public void consumir(TransactionEventDTO event) {
        try {
            alterarStatusTransacao.executar(event.id());
        } catch (SaldoInsuficienteException | LimiteExcedidoException e) {
            // Aqui você trata sem "explodir" o sistema
            System.err.println("Regra de negócio violada: " + e.getMessage());
            marcarComoRejeitada(event.id(), e.getMessage());
        } catch (Exception e) {
            // Erros técnicos (ex: MockAPI fora do ar)
            System.err.println("Erro técnico ao processar: " + e.getMessage());
            // Aqui você decide: ou rejeita ou deixa o Kafka tentar de novo (Retry)
        }
    }

    private void marcarComoRejeitada(UUID id, String motivo) {
        repositorio.buscarPorId(id).ifPresentOrElse(transaction -> {
            transaction.rejeitar();
            repositorio.atualizarStatus(transaction);
            System.out.println("Transação " + id + " marcada como REJECTED no banco. Motivo: " + motivo);
        }, () -> System.err.println("Erro: Transação " + id + " não encontrada para rejeição."));
    }
}
