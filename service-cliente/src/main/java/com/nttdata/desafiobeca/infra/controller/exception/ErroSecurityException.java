package com.nttdata.desafiobeca.infra.controller.exception;

public class ErroSecurityException extends RuntimeException {
    public ErroSecurityException(String mensagem, Exception exception) {
        super(mensagem, exception);
    }
}
