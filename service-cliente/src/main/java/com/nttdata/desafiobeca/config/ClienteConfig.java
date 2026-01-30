package com.nttdata.desafiobeca.config;

import com.nttdata.desafiobeca.application.gateways.RepositorioDeCliente;
import com.nttdata.desafiobeca.application.usecases.*;
import com.nttdata.desafiobeca.infra.excel.ClienteExcelParser;
import com.nttdata.desafiobeca.infra.gateways.ClienteEntityMapper;
import com.nttdata.desafiobeca.infra.gateways.RepositorioDeClienteJpa;
import com.nttdata.desafiobeca.infra.persistence.ClienteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ClienteConfig {

    @Bean
    RepositorioDeCliente criarRepositorioJpa(ClienteRepository repositorio, ClienteEntityMapper mapper) {
        return new RepositorioDeClienteJpa(repositorio, mapper);
    }

    @Bean
    ClienteEntityMapper retornaMapper() {
        return new ClienteEntityMapper();
    }

    @Bean
    CriarCliente criarCliente(RepositorioDeCliente repositorioDeCliente, PasswordEncoder passwordEncoder) {
        return new CriarCliente(repositorioDeCliente, passwordEncoder);
    }

    @Bean
    AlterarCliente alterarCliente(RepositorioDeCliente repositorioDeCliente) {
        return new AlterarCliente(repositorioDeCliente);
    }

    @Bean
    BuscarClientePorId buscarClientePorId(RepositorioDeCliente repositorioDeCliente) {
        return new BuscarClientePorId(repositorioDeCliente);
    }

    @Bean
    ListarClientes listarClientes(RepositorioDeCliente repositorioDeCliente) {
        return new ListarClientes(repositorioDeCliente);
    }

    @Bean
    ExcluirCliente excluirCliente(RepositorioDeCliente repositorioDeCliente) {
        return new ExcluirCliente(repositorioDeCliente);
    }

    @Bean
    ImportarClientesExcel importarClientesExcel(RepositorioDeCliente repositorioDeCliente, PasswordEncoder passwordEncoder) {
        return new ImportarClientesExcel(repositorioDeCliente, passwordEncoder);
    }

    // INFRA (Excel)

    @Bean
     ClienteExcelParser clienteExcelParser() {
        return new ClienteExcelParser();
    }


}
