package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.exceptions.CotacaoNaoEncontradaException;
import com.nttdata.desafiobeca.application.exceptions.MockContaBancariaNaoExistenteException;
import com.nttdata.desafiobeca.application.gateways.EnviarEventoTransaction;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeTransaction;
import com.nttdata.desafiobeca.domain.Transaction;
import com.nttdata.desafiobeca.infra.clientes.BrasilApiClient;
import com.nttdata.desafiobeca.infra.clientes.MockApiClient;
import com.nttdata.desafiobeca.infra.clientes.dto.BrasilApiCambioDTO;
import feign.FeignException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CadastrarTransacao {
    private final RepositorioDeTransaction repositorio;
    private final BrasilApiClient brasilApiClient;
    private final MockApiClient mockApiClient;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    private final EnviarEventoTransaction enviarEventoTransaction;

    public CadastrarTransacao(RepositorioDeTransaction repositorio, BrasilApiClient brasilApiClient, EnviarEventoTransaction enviarEventoTransaction, MockApiClient mockApiClient) {
        this.repositorio = repositorio;
        this.brasilApiClient = brasilApiClient;
        this.enviarEventoTransaction = enviarEventoTransaction;
        this.mockApiClient = mockApiClient;
    }

    public Transaction executa(Transaction dados, Long clienteIdAutenticado) {

        try {
            mockApiClient.getAccountDetails(String.valueOf(clienteIdAutenticado));
        } catch (feign.FeignException.NotFound e) {
            throw new MockContaBancariaNaoExistenteException("Cliente não possui conta no banco parceiro.");
        } catch (Exception e) {
            System.err.println("Falha na rede ao conectar com MockAPI: " + e.getMessage());
            throw new RuntimeException("Erro de comunicação externa: " + e.getMessage());
        }
//        try {
//            mockApiClient.getAccountDetails(clienteIdAutenticado);
//        } catch (Exception e) {
//            throw new MockContaBancariaNaoExistenteException("Operação cancelada: Este usuário não possui conta bancária ativa no parceiro.");
//        }

        dados.setClienteId(clienteIdAutenticado);

        dados.prepararNovaTransacao();
        BigDecimal taxa = buscarTaxaCambio(dados.getMoeda());
        dados.aplicarConversao(taxa);

        Transaction salva = repositorio.cadastrar(dados);
        enviarEventoTransaction.enviar(salva);

        return salva;
    }

    private BigDecimal buscarTaxaCambio(String moeda) {
        if ("BRL".equalsIgnoreCase(moeda)) return BigDecimal.ONE;

        LocalDate dataConsulta = LocalDate.now();
        int tentativas = 0;

        while (tentativas < 5) {
            try {
                String dataFormatada = dataConsulta.format(DateTimeFormatter.ISO_LOCAL_DATE);

                System.out.println("Tentando data: " + dataFormatada + " para moeda: " + moeda);

                BrasilApiCambioDTO resposta = brasilApiClient.getCotacao(moeda, dataFormatada);

                if (resposta != null && resposta.cotacoes() != null && !resposta.cotacoes().isEmpty()) {
                    return resposta.cotacoes().get(0).cotacaoCompra();
                }
            } catch (Exception e) {
                System.err.println("Erro na data " + dataConsulta + ": " + e.getMessage());
            }

            dataConsulta = dataConsulta.minusDays(1);
            tentativas++;
        }

        throw new CotacaoNaoEncontradaException("Nenhuma cotação encontrada nos últimos 5 dias para: " + moeda);
    }
}