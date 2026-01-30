package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.exceptions.ImportacaoClientesException;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeCliente;
import com.nttdata.desafiobeca.domain.Cliente;
import org.springframework.security.crypto.password.PasswordEncoder; // Adicione este import

import java.util.List;

public class ImportarClientesExcel {

    private final RepositorioDeCliente repositorioDeCliente;
    private final PasswordEncoder passwordEncoder; // Adicione o encoder

    public ImportarClientesExcel(RepositorioDeCliente repositorioDeCliente, PasswordEncoder passwordEncoder) {
        this.repositorioDeCliente = repositorioDeCliente;
        this.passwordEncoder = passwordEncoder;
    }

    public void executar(List<Cliente> clientes) {
        if (clientes == null || clientes.isEmpty()) {
            throw new ImportacaoClientesException("A lista de clientes está vazia no Use Case!");
        }

        clientes.forEach(cliente -> {
            cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));
            repositorioDeCliente.cadastrarCliente(cliente);
        });
    }
}