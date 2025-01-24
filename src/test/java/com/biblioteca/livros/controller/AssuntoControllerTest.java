package com.biblioteca.livros.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.biblioteca.livros.model.Assunto;
import com.biblioteca.livros.service.AssuntoService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@SpringBootTest
@AutoConfigureMockMvc
public class AssuntoControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssuntoService assuntoService;

    @BeforeEach
    void setUp() {
        // Configuração inicial do mock, se necessário
    }

    @Test
    void listarTodos() throws Exception {
        Assunto assunto1 = new Assunto(1, "Assunto 1");
        Assunto assunto2 = new Assunto(2, "Assunto 2");

        when(assuntoService.listarTodos()).thenReturn(Arrays.asList(assunto1, assunto2));

        mockMvc.perform(get("/api/assuntos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].descricao").value("Assunto 1"))
                .andExpect(jsonPath("$[1].descricao").value("Assunto 2"));
    }

    @Test
    void buscarPorIdExistente() throws Exception {
        Assunto assunto = new Assunto(1, "Assunto 1");

        when(assuntoService.buscarPorId(1)).thenReturn(Optional.of(assunto));

        mockMvc.perform(get("/api/assuntos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Assunto 1"));
    }

    @Test
    void buscarPorIdInexistente() throws Exception {
        when(assuntoService.buscarPorId(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/assuntos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void criar() throws Exception {
        Assunto assuntoCriado = new Assunto(1, "Novo Assunto");

        when(assuntoService.salvar(any(Assunto.class))).thenReturn(assuntoCriado);

        mockMvc.perform(post("/api/assuntos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\": \"Novo Assunto\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Novo Assunto"));
    }

    @Test
    void alterarComSucesso() throws Exception {
        Assunto assuntoExistente = new Assunto(1, "Assunto Atual");
        Assunto assuntoAtualizado = new Assunto(1, "Assunto Atualizado");

        when(assuntoService.buscarPorId(1)).thenReturn(Optional.of(assuntoExistente));
        when(assuntoService.salvar(any(Assunto.class))).thenReturn(assuntoAtualizado);

        mockMvc.perform(put("/api/assuntos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\": \"Assunto Atualizado\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Assunto Atualizado"));
    }

    @Test
    void alterarComIdInexistente() throws Exception {
        when(assuntoService.buscarPorId(999)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/assuntos/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\": \"Assunto Inexistente\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletarComSucesso() throws Exception {
        doNothing().when(assuntoService).deletar(1);

        mockMvc.perform(delete("/api/assuntos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletarComIdInexistente() throws Exception {
        doThrow(new RuntimeException("Assunto não encontrado")).when(assuntoService).deletar(999);

        mockMvc.perform(delete("/api/assuntos/999"))
                .andExpect(status().isNotFound());
    }
}
