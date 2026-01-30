package com.nttdata.desafiobeca.infra.controller;

import com.nttdata.desafiobeca.application.usecases.*;
import com.nttdata.desafiobeca.domain.Cliente;
import com.nttdata.desafiobeca.infra.excel.ClienteExcelParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean private CriarCliente criarCliente;
    @MockitoBean private AlterarCliente alterarCliente;
    @MockitoBean private BuscarClientePorId buscarClientePorId;
    @MockitoBean private ListarClientes listarClientes;
    @MockitoBean private ExcluirCliente excluirCliente;
    @MockitoBean private ClienteExcelParser excelParser;
    @MockitoBean private ImportarClientesExcel importarClientesExcel;

    @Test
    @DisplayName("POST /clientes - Deve cadastrar um cliente e retornar 201 Created")
    void deveCadastrarCliente() throws Exception {
        Cliente cliente = new Cliente(1L, "Geralt de Rivia", "geralt@kaermorhen.com", "witcher123");
        when(criarCliente.cadastrarCliente(any(Cliente.class))).thenReturn(cliente);

        String json = """
                {
                    "nome": "Geralt de Rivia",
                    "email": "geralt@kaermorhen.com",
                    "senha": "witcher123"
                }
                """;

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Geralt de Rivia"));
    }

    @Test
    @DisplayName("POST /clientes/upload - Deve importar Excel e retornar 201 Created")
    void deveImportarExcel() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "teste.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "content".getBytes());

        List<Cliente> clientes = List.of(new Cliente("Ciri", "ciri@email.com", "123"));
        when(excelParser.parse(any())).thenReturn(clientes);

        mockMvc.perform(multipart("/clientes/upload").file(file))
                .andExpect(status().isCreated());

        verify(importarClientesExcel, times(1)).executar(clientes);
    }

    @Test
    @DisplayName("GET /clientes - Deve listar todos os clientes e retornar 200 OK")
    void deveListarClientes() throws Exception {
        List<Cliente> lista = List.of(new Cliente(1L, "Yennefer", "yen@email.com", "123"));
        when(listarClientes.listaCliente()).thenReturn(lista);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Yennefer"))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("GET /clientes/{id} - Deve buscar por ID e retornar 200 OK")
    void deveBuscarPorId() throws Exception {
        Cliente cliente = new Cliente(1L, "Vesemir", "vesemir@email.com", "123");
        when(buscarClientePorId.buscarClientePorId(1L)).thenReturn(cliente);

        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Vesemir"));
    }

    @Test
    @DisplayName("PUT /clientes - Deve alterar dados e retornar 200 OK")
    void deveAlterarCliente() throws Exception {
        Cliente alterado = new Cliente(1L, "Triss Merigold", "triss@email.com", "123");
        when(alterarCliente.alteraCliente(eq(1L), anyString(), anyString())).thenReturn(alterado);

        String json = """
                {
                    "id": 1,
                    "nome": "Triss Merigold",
                    "email": "triss@email.com"
                }
                """;

        mockMvc.perform(put("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Triss Merigold"));
    }

    @Test
    @DisplayName("DELETE /clientes/{id} - Deve excluir e retornar 204 No Content")
    void deveExcluirCliente() throws Exception {
        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isNoContent());

        verify(excluirCliente, times(1)).excluirCliente(1L);
    }
}