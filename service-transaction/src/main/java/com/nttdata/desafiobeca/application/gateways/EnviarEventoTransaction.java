package com.nttdata.desafiobeca.application.gateways;

import com.nttdata.desafiobeca.domain.Transaction;

public interface EnviarEventoTransaction {
    void enviar(Transaction transaction);
}
