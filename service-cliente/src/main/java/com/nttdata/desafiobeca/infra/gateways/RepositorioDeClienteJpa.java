package com.nttdata.desafiobeca.infra.gateways;

import com.nttdata.desafiobeca.application.exceptions.ClienteNaoEncontradoException;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeCliente;
import com.nttdata.desafiobeca.domain.Cliente;
import com.nttdata.desafiobeca.infra.persistence.ClienteEntity;
import com.nttdata.desafiobeca.infra.persistence.ClienteRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RepositorioDeClienteJpa implements RepositorioDeCliente {

    private final ClienteRepository repository;
    private final ClienteEntityMapper mapper;

    public RepositorioDeClienteJpa(ClienteRepository repository, ClienteEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Cliente cadastrarCliente(Cliente cliente) {
       ClienteEntity entity = mapper.toEntity(cliente);
       repository.save(entity);

        return mapper.toDomain(entity);
    }

    @Override
    public Cliente alteraCliente(Cliente cliente) {
        ClienteEntity entity = repository.findById(cliente.getId())
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com o ID: " + cliente.getId()));

        if (cliente.getNome() != null && !cliente.getNome().isBlank()) {
            entity.setNome(cliente.getNome());
        }

        if (cliente.getEmail() != null && !cliente.getEmail().isBlank()) {
            entity.setEmail(cliente.getEmail());
        }

        repository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public List<Cliente> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        Optional<ClienteEntity> existeCliente = repository.findById(id);

        if(existeCliente.isPresent()) {
            ClienteEntity entity = existeCliente.get();
            Cliente cliente = mapper.toDomain(entity);
            return Optional.of(cliente);
        }
        return Optional.empty();
    }

    @Override
    public void excluiClientePorId(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Cliente> buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDomain); // Usa o seu mapper para voltar para o objeto Cliente
    }
}
