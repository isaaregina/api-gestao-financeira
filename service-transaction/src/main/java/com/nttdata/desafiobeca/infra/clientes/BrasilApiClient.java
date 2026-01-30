package com.nttdata.desafiobeca.infra.clientes;

import com.nttdata.desafiobeca.infra.clientes.dto.BrasilApiCambioDTO;
import com.nttdata.desafiobeca.infra.config.FeignSSLConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "brasil-api-cambio", url = "https://brasilapi.com.br/api/cambio/v1", configuration = FeignSSLConfig.class)
public interface BrasilApiClient {

    @GetMapping("/cotacao/{moeda}/{data}")
    BrasilApiCambioDTO getCotacao(@PathVariable("moeda") String moeda, @PathVariable("data") String data);
}

