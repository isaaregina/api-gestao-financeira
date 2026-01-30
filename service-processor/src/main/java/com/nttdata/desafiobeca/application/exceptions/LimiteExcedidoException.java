package com.nttdata.desafiobeca.application.exceptions;

public class LimiteExcedidoException extends RuntimeException {
    public LimiteExcedidoException(String mensagem) {
        super(mensagem);
    }
}
