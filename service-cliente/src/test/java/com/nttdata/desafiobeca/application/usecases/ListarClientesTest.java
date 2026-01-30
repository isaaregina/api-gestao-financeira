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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarClientesTest {

    @Mock
    private RepositorioDeCliente repositorio;

    @InjectMocks
    private ListarClientes listarClientes;

    @Test
    @DisplayName("Deve retornar a lista de clientes quando houver registros")
    void deveRetornarListaQuandoHouverClientes() {
        List<Cliente> mockLista = List.of(
                new Cliente(1L, "Ana", "ana@email.com", "123"),
                new Cliente(2L, "Beto", "beto@email.com", "456")
        );
        when(repositorio.listarTodos()).thenReturn(mockLista);

        List<Cliente> resultado = listarClientes.listaCliente();

        assertFalse(resultado.isEmpty());
        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("Deve lançar ClienteNaoEncontradoException quando a lista vier vazia")
    void deveLancarExcecaoQuandoListaVazia() {
        when(repositorio.listarTodos()).thenReturn(List.of());

        ClienteNaoEncontradoException exception = assertThrows(ClienteNaoEncontradoException.class, () -> {
            listarClientes.listaCliente();
        });

        assertEquals("Nenhum cliente cadastrado no sistema!", exception.getMessage());
    }
}