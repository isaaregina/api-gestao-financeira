package com.nttdata.desafiobeca.infra.controller.exception;

import com.nttdata.desafiobeca.application.exceptions.AcessoException;
import com.nttdata.desafiobeca.application.exceptions.CotacaoNaoEncontradaException;
import com.nttdata.desafiobeca.application.exceptions.MockContaBancariaNaoExistenteException;
import com.nttdata.desafiobeca.application.exceptions.TransacaoNaoEncontradaException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransacaoNaoEncontradaException.class)
    public ResponseEntity<String> tratarTransacaoNaoEncontrada(TransacaoNaoEncontradaException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    // Captura o erro 404 da BrasilAPI (Moeda ou Data não encontrada)
    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<String> tratarMoedaNaoEncontrada(FeignException.NotFound ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Erro na BrasilAPI: Cotação não encontrada para a moeda ou data informada.");
    }

    // Captura erros genéricos de integração (API fora do ar, erro 500 deles, etc)
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> tratarErroIntegacao(FeignException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body("Erro de integração: O serviço de consulta de câmbio retornou um erro (" + ex.status() + ").");
    }

    @ExceptionHandler(CotacaoNaoEncontradaException.class)
    public ResponseEntity<String> tratarErroCotacao(CotacaoNaoEncontradaException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    // Adicionando o tratamento para Acesso Inválido (403 Forbidden)
    @ExceptionHandler(AcessoException.class)
    public ResponseEntity<String> tratarAcessoInvalido(AcessoException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ex.getMessage());
    }

    @ExceptionHandler(MockContaBancariaNaoExistenteException.class)
    public ResponseEntity<String> tratarMockContaBancariaNaoExistente(MockContaBancariaNaoExistenteException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}