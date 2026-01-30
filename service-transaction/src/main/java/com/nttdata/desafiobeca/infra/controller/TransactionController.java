package com.nttdata.desafiobeca.infra.controller;

import com.nttdata.desafiobeca.application.usecases.*;
import com.nttdata.desafiobeca.domain.Transaction;
import com.nttdata.desafiobeca.infra.controller.dto.TransactionRequestDTO;
import com.nttdata.desafiobeca.infra.controller.dto.TransactionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("transactions")
@SecurityRequirement(name = "bearer-key")
public class TransactionController {

    private final CadastrarTransacao cadastrarTransacao;
    private final AtualizarTransacao atualizarTransacao;
    private final BuscarTransacaoPorId buscarTransacaoPorId;
    private final ListaTransacoesPorCliente listaTransacoesPorCliente;
    private final ListaTransacoes listaTransacoes;
    private final ExcluirTransacao excluirTransacao;

    public TransactionController(CadastrarTransacao cadastrarTransacao,
                                 AtualizarTransacao atualizarTransacao,
                                 BuscarTransacaoPorId buscarTransacaoPorId,
                                 ListaTransacoesPorCliente listaTransacoesPorCliente,
                                 ListaTransacoes listaTransacoes,
                                 ExcluirTransacao excluirTransacao) {
        this.cadastrarTransacao = cadastrarTransacao;
        this.atualizarTransacao = atualizarTransacao;
        this.buscarTransacaoPorId = buscarTransacaoPorId;
        this.listaTransacoesPorCliente = listaTransacoesPorCliente;
        this.listaTransacoes = listaTransacoes;
        this.excluirTransacao = excluirTransacao;
    }

    @PostMapping
    @Operation(summary = "Cadastro de transações")
    public ResponseEntity<TransactionResponseDTO> cadastrar(@RequestBody @Valid TransactionRequestDTO dto) {
        // Pega o ID de quem está logado (o token que você colou no cabeçalho)
        Long idLogado = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        Transaction salva = cadastrarTransacao.executa(new Transaction(
                null, idLogado, dto.valorOriginal(), dto.moeda(),
                null, null, dto.tipo(), null, null
        ), idLogado);

        return ResponseEntity.status(HttpStatus.CREATED).body(new TransactionResponseDTO(salva));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar transação existente",
            description = "Permite a atualização dos dados de uma transação. **Regra de Segurança:** O sistema valida se a transação pertence ao usuário autenticado antes de processar a alteração. Caso pertença a outro usuário, a operação é negada."
    )
    public ResponseEntity<TransactionResponseDTO> atualizar(@PathVariable UUID id, @RequestBody @Valid TransactionRequestDTO dto) {
        Long idLogado = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        Transaction atualizada = atualizarTransacao.executa(new Transaction(
                id, null, dto.valorOriginal(), dto.moeda(), null, null, dto.tipo(), null, null
        ), idLogado); // Passamos o idLogado aqui

        return ResponseEntity.ok(new TransactionResponseDTO(atualizada));
    }
//    @PutMapping("/{id}")
//    @Operation(summary = "Atualização de transações", description = "O cliente só pode alterar suas informações, não de outros clientes")
//    public ResponseEntity<TransactionResponseDTO> atualizar(@PathVariable UUID id, @RequestBody @Valid TransactionRequestDTO dto) {
//        Long idLogado = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
//
//        Transaction atualizada = atualizarTransacao.executa(new Transaction(
//                id,
//                idLogado,
//                dto.valorOriginal(),
//                dto.moeda(),
//                null, null, dto.tipo(), null, null
//        ));
//
//        return ResponseEntity.ok(new TransactionResponseDTO(atualizada));
//    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca de transações por ID")
    public ResponseEntity<TransactionResponseDTO> buscarPorId(@PathVariable UUID id) {
        Transaction transaction = buscarTransacaoPorId.executa(id);
        return ResponseEntity.ok(new TransactionResponseDTO(transaction));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Busca transação por clienteID")
    public ResponseEntity<List<TransactionResponseDTO>> buscaPorClienteId(@PathVariable Long clienteId) {
        List<TransactionResponseDTO> lista = listaTransacoesPorCliente.executa(clienteId).stream()
                .map(TransactionResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @GetMapping
    @Operation(summary = "Lista todas as transações")
    public ResponseEntity<List<TransactionResponseDTO>> listarTodas() {
        List<TransactionResponseDTO> lista = listaTransacoes.executa().stream()
                .map(TransactionResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar transação")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        excluirTransacao.executa(id);
        return ResponseEntity.noContent().build();
    }
}