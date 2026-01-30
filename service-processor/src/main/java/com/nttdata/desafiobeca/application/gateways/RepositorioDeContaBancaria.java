package com.nttdata.desafiobeca.application.gateways;

import com.nttdata.desafiobeca.application.gateways.dto.ContaBancariaDTO;
import java.math.BigDecimal;

public interface RepositorioDeContaBancaria {
    ContaBancariaDTO buscarDadosBancarios(Long clienteId);

    void atualizarSaldo(Long clienteId, BigDecimal novoSaldo);
}