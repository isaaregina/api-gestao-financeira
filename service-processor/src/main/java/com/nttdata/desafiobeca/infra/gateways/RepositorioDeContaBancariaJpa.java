package com.nttdata.desafiobeca.infra.gateways;

import com.nttdata.desafiobeca.application.gateways.RepositorioDeContaBancaria;
import com.nttdata.desafiobeca.application.gateways.dto.AtualizarSaldoDTO;
import com.nttdata.desafiobeca.application.gateways.dto.ContaBancariaDTO;
import com.nttdata.desafiobeca.infra.clients.OpenFinanceMockClient;

import java.math.BigDecimal;


public class RepositorioDeContaBancariaJpa implements RepositorioDeContaBancaria {

    private final OpenFinanceMockClient mockClient;

    public RepositorioDeContaBancariaJpa(OpenFinanceMockClient mockClient) {
        this.mockClient = mockClient;
    }

    @Override
    public ContaBancariaDTO buscarDadosBancarios(Long clienteId) {
        return mockClient.getAccountDetails(clienteId);
    }

    @Override
    public void atualizarSaldo(Long clienteId, BigDecimal novoSaldo) {
        mockClient.updateBalance(clienteId, new AtualizarSaldoDTO(novoSaldo));
    }
}