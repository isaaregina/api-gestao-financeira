package com.nttdata.desafiobeca.infra.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.nttdata.desafiobeca.application.exceptions.ClienteNaoEncontradoException;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeCliente;
import com.nttdata.desafiobeca.domain.Cliente;
import com.nttdata.desafiobeca.infra.controller.exception.TokenGenerationException;
import com.nttdata.desafiobeca.infra.controller.exception.TokenInvalidoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private final RepositorioDeCliente repositorio;

    public TokenService(RepositorioDeCliente repositorio) {
        this.repositorio = repositorio;
    }

    public String gerarToken(String email) {
        Cliente cliente = repositorio.buscarPorEmail(email)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado para gerar token"));

        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Desafio Beca")
                    .withSubject(cliente.getEmail())
                    .withClaim("id", cliente.getId())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (Exception exception) {
            throw new TokenGenerationException("Erro técnico ao gerar o token de acesso");
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Desafio Beca")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new TokenInvalidoException("O token enviado é inválido ou está expirado");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}