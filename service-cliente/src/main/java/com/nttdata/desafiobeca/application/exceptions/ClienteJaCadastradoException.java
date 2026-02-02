package com.nttdata.desafiobeca.application.exceptions;

public class ClienteJaCadastradoException extends RuntimeException{
    public ClienteJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}
