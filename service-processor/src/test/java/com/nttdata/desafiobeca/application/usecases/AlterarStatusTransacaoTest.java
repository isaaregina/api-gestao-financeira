package com.nttdata.desafiobeca.application.usecases;

import com.nttdata.desafiobeca.application.gateways.RepositorioDeContaBancaria;
import com.nttdata.desafiobeca.application.gateways.RepositorioDeProcessor;
import com.nttdata.desafiobeca.application.gateways.dto.ContaBancariaDTO;
import com.nttdata.desafiobeca.application.exceptions.SaldoInsuficienteException;
import com.nttdata.desafiobeca.domain.StatusTransacao;
import com.nttdata.desafiobeca.domain.TipoTransacao;
import com.nttdata.desafiobeca.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlterarStatusTransacaoTest {

    private RepositorioDeProcessor repositorio;
    private RepositorioDeContaBancaria contaBancaria;
    private AlterarStatusTransacao useCase;

    @BeforeEach
    void setup() {
        repositorio = mock(RepositorioDeProcessor.class);
        contaBancaria = mock(RepositorioDeContaBancaria.class);
        useCase = new AlterarStatusTransacao(repositorio, contaBancaria);
    }

    @Test
    @DisplayName("Deve aprovar transação e atualizar saldo quando houver saldo suficiente")
    void deveAprovarTransacaoComSucesso() {
        // GIVEN
        UUID id = UUID.randomUUID();
        Transaction transaction = new Transaction(
                id, 1L, new BigDecimal("100.00"), "BRL",
                BigDecimal.ONE, new BigDecimal("100.00"),
                TipoTransacao.PURCHASE, StatusTransacao.PENDING, LocalDateTime.now()
        );

// Adicionando o "1L" no início para fechar os 4 argumentos
        ContaBancariaDTO contaMock = new ContaBancariaDTO(1L, new BigDecimal("500.00"), new BigDecimal("1000.00"), true);

        when(repositorio.buscarPorId(id)).thenReturn(Optional.of(transaction));
        when(contaBancaria.buscarDadosBancarios(1L)).thenReturn(contaMock);

        // WHEN
        useCase.executar(id);

        // THEN
        assertEquals(StatusTransacao.APPROVED, transaction.getStatus());
        verify(contaBancaria, times(1)).atualizarSaldo(eq(1L), any());
        verify(repositorio, times(1)).atualizarStatus(transaction);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o saldo for insuficiente")
    void deveFalharPorSaldoInsuficiente() {
        // GIVEN
        UUID id = UUID.randomUUID();
        Transaction transaction = new Transaction(
                id, 1L, new BigDecimal("1000.00"), "BRL",
                BigDecimal.ONE, new BigDecimal("1000.00"),
                TipoTransacao.PURCHASE, StatusTransacao.PENDING, LocalDateTime.now()
        );

        ContaBancariaDTO contaMock = new ContaBancariaDTO(1L, new BigDecimal("50.00"), new BigDecimal("100.00"), true);

        when(repositorio.buscarPorId(id)).thenReturn(Optional.of(transaction));
        when(contaBancaria.buscarDadosBancarios(1L)).thenReturn(contaMock);

        // WHEN & THEN
        assertThrows(SaldoInsuficienteException.class, () -> useCase.executar(id));
        verify(contaBancaria, never()).atualizarSaldo(any(), any());
        verify(repositorio, never()).atualizarStatus(any());
    }
}