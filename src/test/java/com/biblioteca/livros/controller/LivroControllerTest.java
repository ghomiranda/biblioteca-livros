package com.biblioteca.livros.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.biblioteca.livros.model.Livro;
import com.biblioteca.livros.service.LivroService;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LivroControllerTest {
	
	private final LivroService livroService = Mockito.mock(LivroService.class);
    private final LivroController livroController = new LivroController(livroService);

    @Test
    void listarTodos_DeveRetornarListaDeLivros() {
        Livro livro1 = new Livro();
        livro1.setCodli(1);
        livro1.setTitulo("Livro 1");

        Livro livro2 = new Livro();
        livro2.setCodli(2);
        livro2.setTitulo("Livro 2");

        when(livroService.listarTodos()).thenReturn(Arrays.asList(livro1, livro2));

        var livros = livroController.listarTodos();

        assertEquals(2, livros.size());
        assertEquals("Livro 1", livros.get(0).getTitulo());
        verify(livroService, times(1)).listarTodos();
    }

    @Test
    void buscarPorId_DeveRetornarLivroQuandoIdExistir() {
        Livro livro = new Livro();
        livro.setCodli(1);
        livro.setTitulo("Livro Teste");

        when(livroService.buscarPorId(1)).thenReturn(Optional.of(livro));

        ResponseEntity<Livro> resposta = livroController.buscarPorId(1);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("Livro Teste", resposta.getBody().getTitulo());
        verify(livroService, times(1)).buscarPorId(1);
    }

    @Test
    void buscarPorId_DeveRetornar404QuandoIdNaoExistir() {
        when(livroService.buscarPorId(99)).thenReturn(Optional.empty());

        ResponseEntity<Livro> resposta = livroController.buscarPorId(99);

        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
        verify(livroService, times(1)).buscarPorId(99);
    }

    @Test
    void salvar_DeveSalvarLivro() {
        Livro livro = new Livro();
        livro.setTitulo("Novo Livro");

        when(livroService.salvar(livro)).thenReturn(livro);

        Livro resposta = livroController.salvar(livro);

        assertEquals("Novo Livro", resposta.getTitulo());
        verify(livroService, times(1)).salvar(livro);
    }

    @Test
    void deletar_DeveRetornar204QuandoDeletar() {
        ResponseEntity<Void> resposta = livroController.deletar(1);

        assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
        verify(livroService, times(1)).deletar(1);
    }

}
