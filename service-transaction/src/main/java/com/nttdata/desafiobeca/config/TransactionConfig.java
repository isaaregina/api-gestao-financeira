package com.nttdata.desafiobeca.config;

import com.nttdata.desafiobeca.application.gateways.EnviarEventoTransaction;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeTransaction;
import com.nttdata.desafiobeca.application.usecases.*;
import com.nttdata.desafiobeca.infra.clientes.BrasilApiClient;
import com.nttdata.desafiobeca.infra.gateways.RepositorioDeTransactionJpa;
import com.nttdata.desafiobeca.infra.gateways.TransactionEntityMapper;
import com.nttdata.desafiobeca.infra.persistence.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionConfig {

    @Bean
    RepositorioDeTransaction criarRepositorioJpa(TransactionRepository repositorio, TransactionEntityMapper mapper) {
        return new RepositorioDeTransactionJpa(repositorio, mapper);
    }

    @Bean
    TransactionEntityMapper retornaMapper() {
        return new TransactionEntityMapper();
    }

    @Bean
    CadastrarTransacao cadastrarTransacao(RepositorioDeTransaction repositorioDeTransaction, BrasilApiClient apiClient, EnviarEventoTransaction enviarEventoTransaction) {
        return new CadastrarTransacao(repositorioDeTransaction, apiClient, enviarEventoTransaction);
    }

    @Bean
    AtualizarTransacao atualizarTransacao(RepositorioDeTransaction repositorioDeTransaction) {
        return new AtualizarTransacao(repositorioDeTransaction);
    }

    @Bean
    BuscarTransacaoPorId buscarTransacaoPorId(RepositorioDeTransaction repositorioDeTransaction) {
        return new BuscarTransacaoPorId(repositorioDeTransaction);
    }

    @Bean
    ListaTransacoesPorCliente listaTransacoesPorCliente(RepositorioDeTransaction repositorioDeTransaction) {
        return new ListaTransacoesPorCliente(repositorioDeTransaction);
    }

    @Bean
    ListaTransacoes listaTransacoes(RepositorioDeTransaction repositorioDeTransaction) {
        return new ListaTransacoes(repositorioDeTransaction);
    }

    @Bean
    ExcluirTransacao excluirTransacao(RepositorioDeTransaction repositorioDeTransaction) {
        return new ExcluirTransacao(repositorioDeTransaction);
    }


}
