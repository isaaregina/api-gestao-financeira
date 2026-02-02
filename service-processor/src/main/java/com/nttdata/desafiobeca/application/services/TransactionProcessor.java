package com.nttdata.desafiobeca.application.services;

import com.nttdata.desafiobeca.application.usecases.AlterarStatusTransacao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionProcessor {

    private final AlterarStatusTransacao alterarStatusTransacao;

    public void processar(UUID transactionId) {
        log.info("Iniciando o 'molho' para a transação: {}", transactionId);

        try {
            // Aqui ele chama o Use Case que você já tem pronto
            // Aquele que faz o GET no MockAPI, valida saldo e faz o PUT
            alterarStatusTransacao.executar(transactionId);

            log.info("Processamento finalizado para a transação: {}", transactionId);
        } catch (Exception e) {
            log.error("Erro crítico no processamento da transação {}: {}", transactionId, e.getMessage());
            // Lançamos a exceção novamente para o Consumer decidir se faz Retry ou não
            throw e;
        }
    }
}
