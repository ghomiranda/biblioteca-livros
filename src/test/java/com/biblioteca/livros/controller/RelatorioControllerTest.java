package com.biblioteca.livros.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.biblioteca.livros.service.RelatorioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayOutputStream;

@WebMvcTest(RelatorioController.class)
public class RelatorioControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private RelatorioService relatorioService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void deveRetornarPdfDeRelatorio() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write("Relatório de Teste".getBytes());
        
        doAnswer(invocation -> {
            ((ByteArrayOutputStream) invocation.getArgument(0)).write(outputStream.toByteArray());
            return null;
        }).when(relatorioService).gerarRelatorioLivros(any());

        mockMvc.perform(get("/api/relatorios/livros"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=relatorio_livros.pdf"))
                .andExpect(content().contentType("application/pdf"))
                .andExpect(content().bytes(outputStream.toByteArray()));
    }
	
}
