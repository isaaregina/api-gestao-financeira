package com.nttdata.desafiobeca.application.gateways;

import com.nttdata.desafiobeca.domain.Cliente;

import java.util.List;
import java.util.Optional;

public interface RepositorioDeCliente {

    Cliente cadastrarCliente(Cliente cliente);

    Cliente alteraCliente(Cliente cliente);

    List<Cliente> listarTodos();

    Optional<Cliente> buscarPorId(Long id); // Caso não retornar nada ou o cliente nao existir

    void excluiClientePorId(Long id);

    Optional<Cliente> buscarPorEmail(String email);

    boolean existePorEmail(String email);
}
