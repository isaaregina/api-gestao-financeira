package com.nttdata.desafiobeca.infra.clientes;

import com.nttdata.desafiobeca.infra.controller.dto.AccountResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "open-finance-mock", url = "${mockapi.url}")
public interface MockApiClient {

    @GetMapping("/accounts/{clienteId}")
    AccountResponseDTO getAccountDetails(@PathVariable("clienteId") String clienteId);
}