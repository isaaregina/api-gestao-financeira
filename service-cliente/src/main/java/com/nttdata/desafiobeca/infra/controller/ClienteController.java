package com.nttdata.desafiobeca.infra.controller;

import com.nttdata.desafiobeca.application.usecases.*;
import com.nttdata.desafiobeca.domain.Cliente;
import com.nttdata.desafiobeca.infra.controller.dto.AlterarClienteDTO;
import com.nttdata.desafiobeca.infra.controller.dto.ClienteRequestDTO;
import com.nttdata.desafiobeca.infra.controller.dto.ClienteResponseDTO;
import com.nttdata.desafiobeca.infra.excel.ClienteExcelParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
@SecurityRequirement(name = "bearer-key")
public class ClienteController {

    private final CriarCliente criarCliente;
    private final AlterarCliente alterarCliente;
    private final BuscarClientePorId buscarClientePorId;
    private final ListarClientes listarClientes;
    private final ExcluirCliente excluirCliente;
    private final ClienteExcelParser excelParser;
    private final ImportarClientesExcel importarClientesExcel;

    public ClienteController(CriarCliente criarCliente, AlterarCliente alterarCliente, BuscarClientePorId buscarClientePorId, ListarClientes listarClientes, ExcluirCliente excluirCliente, ClienteExcelParser excelParser, ImportarClientesExcel importarClientesExcel) {
        this.criarCliente = criarCliente;
        this.alterarCliente = alterarCliente;
        this.buscarClientePorId = buscarClientePorId;
        this.listarClientes = listarClientes;
        this.excluirCliente = excluirCliente;
        this.excelParser = excelParser;
        this.importarClientesExcel = importarClientesExcel;
    }

    @PostMapping
    @Operation(summary = "Cadastro de cliente")
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(@RequestBody @Valid ClienteRequestDTO dto) {
        Cliente salvo = criarCliente.cadastrarCliente(new Cliente(dto.nome(), dto.email(), dto.senha()));

        return ResponseEntity.status(HttpStatus.CREATED).body(new ClienteResponseDTO(salvo));
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Importação de clientes via Excel", description = "Selecione um arquivo .xlsx para cadastro em lote")
    public ResponseEntity<Void> importarExcel(@RequestParam("file") MultipartFile file) {
        List<Cliente> clientes = excelParser.parse(file);
        importarClientesExcel.executar(clientes);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "Listar clientes")
    public ResponseEntity<List<ClienteResponseDTO>> listarCliente() {
        var cliente = listarClientes.listaCliente().stream()
                .map(u -> new ClienteResponseDTO(u.getId(), u.getNome(), u.getEmail()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(cliente);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        Cliente cliente = buscarClientePorId.buscarClientePorId(id);

        return ResponseEntity.ok().body(new ClienteResponseDTO(cliente));
    }

    @PutMapping
    @Operation(summary = "Alterar cliente")
    public ResponseEntity<ClienteResponseDTO> alteraCliente(@RequestBody AlterarClienteDTO dto) {
        var cliente = alterarCliente.alteraCliente(dto.id(), dto.nome(), dto.email());

        return ResponseEntity.ok().body(new ClienteResponseDTO(cliente));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir cliente")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        excluirCliente.excluirCliente(id);

        return ResponseEntity.noContent().build();
    }
}
