package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.gateways.RepositorioDeCliente;
import com.nttdata.desafiobeca.domain.Cliente;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CriarCliente {
    private final RepositorioDeCliente repositorio;
    private final PasswordEncoder passwordEncoder; // Adicione este campo

    public CriarCliente(RepositorioDeCliente repositorio, PasswordEncoder passwordEncoder) {
        this.repositorio = repositorio;
        this.passwordEncoder = passwordEncoder;
    }

    public Cliente cadastrarCliente(Cliente cliente) {
        String senhaCriptografada = passwordEncoder.encode(cliente.getSenha());

        Cliente clienteComSenhaProtegida = new Cliente(
                cliente.getNome(),
                cliente.getEmail(),
                senhaCriptografada
        );

        return this.repositorio.cadastrarCliente(clienteComSenhaProtegida);
    }
}
