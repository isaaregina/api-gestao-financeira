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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExcluirClienteTest {

    @Mock
    private RepositorioDeCliente repositorio;

    @InjectMocks
    private ExcluirCliente excluirCliente;

    @Test
    @DisplayName("Deve chamar o método de exclusão se o cliente existir")
    void deveExcluirCliente() {
        Cliente cliente = new Cliente(1L, "Ana", "ana@email.com", "123");
        when(repositorio.buscarPorId(1L)).thenReturn(Optional.of(cliente));

        excluirCliente.excluirCliente(1L);

        verify(repositorio, times(1)).excluiClientePorId(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar excluir um cliente inexistente")
    void deveLancarExcecaoAoExcluirClienteInexistente() {
        // ARRANGE
        Long idInexistente = 99L;
        when(repositorio.buscarPorId(idInexistente)).thenReturn(Optional.empty());

        // ACT & ASSERT
        ClienteNaoEncontradoException exception = assertThrows(ClienteNaoEncontradoException.class, () -> {
            excluirCliente.excluirCliente(idInexistente);
        });

        assertEquals("Cliente não encontrado!", exception.getMessage());
    }
}