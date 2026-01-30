package com.nttdata.desafiobeca.application.exceptions;

public class TransacaoNaoEncontradaException extends RuntimeException {
    public TransacaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
