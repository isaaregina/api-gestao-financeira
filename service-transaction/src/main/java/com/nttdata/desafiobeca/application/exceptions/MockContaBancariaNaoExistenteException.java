package com.nttdata.desafiobeca.application.exceptions;

public class MockContaBancariaNaoExistenteException extends RuntimeException{
    public MockContaBancariaNaoExistenteException(String mensagem) {
        super(mensagem);
    }
}
