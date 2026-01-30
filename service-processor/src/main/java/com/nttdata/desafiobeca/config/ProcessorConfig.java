package com.nttdata.desafiobeca.config;

import com.nttdata.desafiobeca.application.gateways.RepositorioDeContaBancaria;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeProcessor;
import com.nttdata.desafiobeca.application.usecases.AlterarStatusTransacao;
import com.nttdata.desafiobeca.application.usecases.BuscarTransacaoPorId;
import com.nttdata.desafiobeca.infra.clients.OpenFinanceMockClient;
import com.nttdata.desafiobeca.infra.gateways.RepositorioDeContaBancariaJpa;
import com.nttdata.desafiobeca.infra.gateways.RepositorioDeProcessorJpa;
import com.nttdata.desafiobeca.infra.gateways.TransactionEntityMapper;
import com.nttdata.desafiobeca.infra.persistence.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessorConfig {

    @Bean
    RepositorioDeProcessor criarRepositorioJpa(TransactionRepository repositorio, TransactionEntityMapper mapper) {
        return new RepositorioDeProcessorJpa(repositorio, mapper);
    }

    @Bean
    RepositorioDeContaBancaria repositorioDeContaBancaria(OpenFinanceMockClient mockClient) {
        return new RepositorioDeContaBancariaJpa(mockClient);
    }

    @Bean
    TransactionEntityMapper retornaMapper() {
        return new TransactionEntityMapper();
    }

    @Bean
    public AlterarStatusTransacao alterarStatusTransacao(
            RepositorioDeProcessor repositorio,
            RepositorioDeContaBancaria contaBancaria) {
        return new AlterarStatusTransacao(repositorio, contaBancaria);
    }

    @Bean
    public BuscarTransacaoPorId buscarTransacaoPorId(RepositorioDeProcessor repositorioDeProcessor) {
        return new BuscarTransacaoPorId(repositorioDeProcessor);
    }
}