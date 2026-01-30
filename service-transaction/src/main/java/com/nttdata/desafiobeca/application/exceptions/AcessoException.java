package com.nttdata.desafiobeca.application.exceptions;

public class AcessoException extends RuntimeException {
    public AcessoException(String mensagem) {
        super(mensagem);
    }
}
