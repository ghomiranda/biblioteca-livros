package com.biblioteca.livros.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.biblioteca.livros.model.Livro;
import com.biblioteca.livros.repository.LivroRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LivroServiceTest {

	 private final LivroRepository livroRepository = Mockito.mock(LivroRepository.class);
	    private final LivroService livroService = new LivroService(livroRepository);

	    @Test
	    void listarTodos_DeveRetornarTodosOsLivros() {
	        Livro livro1 = new Livro();
	        livro1.setCodli(1);
	        livro1.setTitulo("Livro 1");

	        Livro livro2 = new Livro();
	        livro2.setCodli(2);
	        livro2.setTitulo("Livro 2");

	        when(livroRepository.findAll()).thenReturn(Arrays.asList(livro1, livro2));

	        var livros = livroService.listarTodos();

	        assertEquals(2, livros.size());
	        assertEquals("Livro 1", livros.get(0).getTitulo());
	        verify(livroRepository, times(1)).findAll();
	    }

	    @Test
	    void buscarPorId_DeveRetornarLivroQuandoIdExistir() {
	        Livro livro = new Livro();
	        livro.setCodli(1);
	        livro.setTitulo("Livro Teste");

	        when(livroRepository.findById(1)).thenReturn(Optional.of(livro));

	        var resultado = livroService.buscarPorId(1);

	        assertTrue(resultado.isPresent());
	        assertEquals("Livro Teste", resultado.get().getTitulo());
	        verify(livroRepository, times(1)).findById(1);
	    }

	    @Test
	    void buscarPorId_DeveRetornarVazioQuandoIdNaoExistir() {
	        when(livroRepository.findById(99)).thenReturn(Optional.empty());

	        var resultado = livroService.buscarPorId(99);

	        assertFalse(resultado.isPresent());
	        verify(livroRepository, times(1)).findById(99);
	    }

	    @Test
	    void salvar_DeveSalvarLivro() {
	        Livro livro = new Livro();
	        livro.setTitulo("Novo Livro");

	        when(livroRepository.save(livro)).thenReturn(livro);

	        var resultado = livroService.salvar(livro);

	        assertNotNull(resultado);
	        assertEquals("Novo Livro", resultado.getTitulo());
	        verify(livroRepository, times(1)).save(livro);
	    }

	    @Test
	    void deletar_DeveChamarMetodoDelete() {
	        livroService.deletar(1);

	        verify(livroRepository, times(1)).deleteById(1);
	    }
	
}
