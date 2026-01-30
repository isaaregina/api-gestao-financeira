package com.nttdata.desafiobeca.infra.excel;

import com.nttdata.desafiobeca.application.exceptions.ImportacaoClientesException;
import com.nttdata.desafiobeca.domain.Cliente;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClienteExcelParser {

    public List<Cliente> parse(MultipartFile file) {
        List<Cliente> clientes = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            // sheet.iterator() é a forma mais segura de não pular linhas físicas existentes
            var rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // Pula o cabeçalho
                if (row.getRowNum() == 0) continue;

                // Valida se a linha tem as células necessárias
                if (isRowEmpty(row)) continue;

                String nome = formatter.formatCellValue(row.getCell(0)).trim();
                String email = formatter.formatCellValue(row.getCell(1)).trim();
                String senha = formatter.formatCellValue(row.getCell(2)).trim();

                if (!nome.isEmpty() && !email.isEmpty()) {
                    clientes.add(new Cliente(nome, email, senha));
                }
            }
            return clientes;
        } catch (Exception e) {
            throw new ImportacaoClientesException("Erro ao processar o arquivo Excel: " + e.getMessage());
        }
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) return true;
        // Verifica se pelo menos a célula de Nome ou Email tem conteúdo
        Cell cellNome = row.getCell(0);
        Cell cellEmail = row.getCell(1);

        boolean nomeVazio = (cellNome == null || cellNome.getCellType() == CellType.BLANK);
        boolean emailVazio = (cellEmail == null || cellEmail.getCellType() == CellType.BLANK);

        return nomeVazio && emailVazio;
    }
}
