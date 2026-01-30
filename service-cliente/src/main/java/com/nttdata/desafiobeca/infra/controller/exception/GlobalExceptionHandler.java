package com.nttdata.desafiobeca.infra.controller.exception;

import com.nttdata.desafiobeca.application.exceptions.ClienteNaoEncontradoException;
import com.nttdata.desafiobeca.application.exceptions.ImportacaoClientesException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<String> tratarClienteNaoEncontrado(ClienteNaoEncontradoException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ImportacaoClientesException.class)
    public ResponseEntity<String> tratarErroDeImportacaoExcel(ImportacaoClientesException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    // Adicionando o tratamento para Token Inválido (403 Forbidden)
    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<String> tratarErroTokenInvalido(TokenInvalidoException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ex.getMessage());
    }

    // Adicionando o tratamento para Erros Técnicos de Geração (500 Internal Server Error)
    @ExceptionHandler(TokenGenerationException.class)
    public ResponseEntity<String> tratarErroGeracaoToken(TokenGenerationException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno ao processar segurança: " + ex.getMessage());
    }
}