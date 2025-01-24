package com.biblioteca.livros.controller;

import com.biblioteca.livros.model.Autor;
import com.biblioteca.livros.service.AutorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@SpringBootTest
@AutoConfigureMockMvc
public class AutorControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private AutorService autorService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void listarTodos() throws Exception {
        Autor autor1 = new Autor(1, "Autor 1");
        Autor autor2 = new Autor(2, "Autor 2");

        when(autorService.listarTodos()).thenReturn(Arrays.asList(autor1, autor2));

        mockMvc.perform(get("/api/autores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Autor 1"))
                .andExpect(jsonPath("$[1].nome").value("Autor 2"));
    }

    @Test
    void buscarPorIdExistente() throws Exception {
        Autor autor = new Autor(1, "Autor 1");

        when(autorService.buscarPorId(1)).thenReturn(Optional.of(autor));

        mockMvc.perform(get("/api/autores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Autor 1"));
    }

    @Test
    void buscarPorIdInexistente() throws Exception {
        when(autorService.buscarPorId(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/autores/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void criar() throws Exception {
        Autor autorCriado = new Autor(1, "Novo Autor");

        when(autorService.salvar(any(Autor.class))).thenReturn(autorCriado);

        mockMvc.perform(post("/api/autores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Novo Autor\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Novo Autor"));
    }

    @Test
    void alterarComSucesso() throws Exception {
        Autor autorExistente = new Autor(1, "Autor Atual");
        Autor autorAtualizado = new Autor(1, "Autor Atualizado");

        when(autorService.buscarPorId(1)).thenReturn(Optional.of(autorExistente));
        when(autorService.salvar(any(Autor.class))).thenReturn(autorAtualizado);

        mockMvc.perform(put("/api/autores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Autor Atualizado\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Autor Atualizado"));
    }

    @Test
    void alterarComIdInexistente() throws Exception {
        when(autorService.buscarPorId(999)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/autores/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Autor Inexistente\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletarComSucesso() throws Exception {
        doNothing().when(autorService).deletar(1);

        mockMvc.perform(delete("/api/autores/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletarComIdInexistente() throws Exception {
        doThrow(new RuntimeException("Autor não encontrado")).when(autorService).deletar(999);

        mockMvc.perform(delete("/api/autores/999"))
                .andExpect(status().isNotFound());
    }
	
}
