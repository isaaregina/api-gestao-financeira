package com.nttdata.desafiobeca.infra.controller.exception;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException(String mensagem) {
        super(mensagem);
    }
}
