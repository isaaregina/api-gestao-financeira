package com.nttdata.desafiobeca.infra.controller.exception;

public class TokenGenerationException extends RuntimeException {
    public TokenGenerationException(String mensagem) {
        super(mensagem);
    }
}
