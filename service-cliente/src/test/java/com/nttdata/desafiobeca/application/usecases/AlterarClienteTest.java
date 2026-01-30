package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.gateways.RepositorioDeCliente;
import com.nttdata.desafiobeca.domain.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlterarClienteTest {

    @Mock
    private RepositorioDeCliente repositorio;

    @InjectMocks
    private AlterarCliente alterarCliente;

    @Test
    @DisplayName("Deve atualizar o nome e email do cliente com sucesso")
    void deveAlterarClienteComSucesso() {
        Cliente clienteExistente = new Cliente(1L, "Antigo", "antigo@email.com", "123");
        when(repositorio.buscarPorId(1L)).thenReturn(Optional.of(clienteExistente));

        when(repositorio.alteraCliente(any(Cliente.class))).thenAnswer(i -> i.getArgument(0));

        Cliente resultado = alterarCliente.alteraCliente(1L, "Novo Nome", "novo@email.com");

        assertEquals("Novo Nome", resultado.getNome());
        assertEquals("novo@email.com", resultado.getEmail());
        verify(repositorio).alteraCliente(any(Cliente.class));
    }
}
