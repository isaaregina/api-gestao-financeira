package com.nttdata.desafiobeca.infra.controller;

import com.nttdata.desafiobeca.infra.config.security.TokenService;
import com.nttdata.desafiobeca.infra.controller.dto.DadosAutenticacaoDTO;
import com.nttdata.desafiobeca.infra.controller.dto.DadosTokenJWTDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/login")
public class AutenticacaoController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacaoDTO dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);

        var userSpring = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        var tokenJWT = tokenService.gerarToken(userSpring.getUsername());

        return ResponseEntity.ok(new DadosTokenJWTDTO(tokenJWT));
    }
}