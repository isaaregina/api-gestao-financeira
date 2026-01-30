package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.exceptions.ImportacaoClientesException;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeCliente;
import com.nttdata.desafiobeca.domain.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ImportarClientesExcelTest {

    @Mock
    private RepositorioDeCliente repositorio;

    @InjectMocks
    private ImportarClientesExcel importarClientesExcel;

    @Test
    @DisplayName("Deve cadastrar todos os clientes da lista vinda do Excel")
    void deveCadastrarListaDeClientes() {
        List<Cliente> lista = List.of(
                new Cliente("User 1", "u1@email.com", "123"),
                new Cliente("User 2", "u2@email.com", "123")
        );

        importarClientesExcel.executar(lista);

        verify(repositorio, times(2)).cadastrarCliente(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando a lista de importação estiver vazia")
    void deveLancarExcecaoQuandoListaVazia() {
        List<Cliente> listaVazia = List.of();

        ImportacaoClientesException exception = assertThrows(ImportacaoClientesException.class, () -> {
            importarClientesExcel.executar(listaVazia);
        });

        assertEquals("A lista de clientes para importação não pode estar vazia!", exception.getMessage());
    }
}