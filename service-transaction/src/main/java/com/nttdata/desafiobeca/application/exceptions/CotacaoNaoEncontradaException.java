package com.nttdata.desafiobeca.application.exceptions;

public class CotacaoNaoEncontradaException extends RuntimeException{
    public CotacaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
