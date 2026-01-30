package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.exceptions.ClienteNaoEncontradoException;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeCliente;
import com.nttdata.desafiobeca.domain.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarClientePorIdTest {

    @Mock
    private RepositorioDeCliente repositorio;

    @InjectMocks
    private BuscarClientePorId buscarClientePorId;

    @Test
    @DisplayName("Deve retornar um cliente quando o ID existir")
    void deveRetornarClienteQuandoIdExistir() {
        Cliente clienteMock = new Cliente(1L, "Pedro", "pedro@email.com", "123");
        when(repositorio.buscarPorId(1L)).thenReturn(Optional.of(clienteMock));

        Cliente resultado = buscarClientePorId.buscarClientePorId(1L);

        assertNotNull(resultado);
        assertEquals("Pedro", resultado.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o cliente não existir")
    void deveLancarExcecaoQuandoNaoExistir() {
        when(repositorio.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> {
            buscarClientePorId.buscarClientePorId(99L);
        });
    }
}