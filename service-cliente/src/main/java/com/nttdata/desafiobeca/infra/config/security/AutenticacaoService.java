package com.nttdata.desafiobeca.infra.config.security;

import com.nttdata.desafiobeca.application.gateways.RepositorioDeCliente;
import com.nttdata.desafiobeca.infra.controller.exception.UsuarioNaoEncontradoException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final RepositorioDeCliente repository;

    public AutenticacaoService(RepositorioDeCliente repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscamos o cliente pelo e-mail (username)
        var cliente = repository.buscarPorEmail(email) // Garanta que este método existe no seu gateway/repositório
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o e-mail: " + email));

        // Retornamos um objeto que o Spring Security entende
        return new User(cliente.getEmail(), cliente.getSenha(), new ArrayList<>());
    }
}
