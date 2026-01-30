package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.gateways.RepositorioDeCliente;
import com.nttdata.desafiobeca.domain.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita o Mockito
class CriarClienteTest {

    @Mock
    private RepositorioDeCliente repositorio; // Fingimos o repositório

    @InjectMocks
    private CriarCliente criarCliente; // Injeta o mock acima dentro do use case

    @Test
    @DisplayName("Deve cadastrar um cliente com sucesso")
    void deveCadastrarClienteComSucesso() {
        // ARRANGE
        Cliente clienteParaSalvar = new Cliente("João Silva", "joao@email.com", "123456");
        Cliente clienteSalvoNoBanco = new Cliente(1L, "João Silva", "joao@email.com", "123456");

        when(repositorio.cadastrarCliente(any(Cliente.class))).thenReturn(clienteSalvoNoBanco);

        // ACT
        Cliente resultado = criarCliente.cadastrarCliente(clienteParaSalvar);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());

        // Verifica se o método do repositório foi chamado exatamente 1 vez
        verify(repositorio, times(1)).cadastrarCliente(any(Cliente.class));
    }
}