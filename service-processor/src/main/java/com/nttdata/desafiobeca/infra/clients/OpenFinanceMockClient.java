package com.nttdata.desafiobeca.infra.clients;

import com.nttdata.desafiobeca.application.gateways.dto.ContaBancariaDTO;
import com.nttdata.desafiobeca.application.gateways.dto.AtualizarSaldoDTO;
import com.nttdata.desafiobeca.config.FeignSSLConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "open-finance-mock", url = "${api.mock.url}", configuration = FeignSSLConfig.class)
public interface OpenFinanceMockClient {

    // Passo 7 e 8: Consulta de saldo/limites
    @GetMapping("/accounts/{clienteId}")
    ContaBancariaDTO getAccountDetails(@PathVariable("clienteId") Long clienteId);

    // Efetivação: Atualiza o saldo após a lógica de soma/subtração no MS3
    @PutMapping("/accounts/{clienteId}")
    void updateBalance(@PathVariable("clienteId") Long clienteId, @RequestBody AtualizarSaldoDTO body);
}
