package com.nttdata.desafiobeca.infra.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.nttdata.desafiobeca.infra.controller.exception.TokenGenerationException;
import com.nttdata.desafiobeca.infra.controller.exception.TokenInvalidoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Desafio Beca")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new TokenInvalidoException("Token JWT inválido ou expirado!");
        }
    }

    // Método crucial para o MS2 identificar o cliente sem consultar o MS1
    public Long getClienteId(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Desafio Beca")
                    .build()
                    .verify(tokenJWT)
                    .getClaim("id").asLong();
        } catch (JWTVerificationException exception) {
            throw new TokenGenerationException("Não foi possível extrair o ID do cliente");
        }
    }
}
