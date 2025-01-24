package com.biblioteca.livros.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.biblioteca.livros.model.Assunto;
import com.biblioteca.livros.repository.AssuntoRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AssuntoServiceTest {

	@Mock
    private AssuntoRepository assuntoRepository;

    @InjectMocks
    private AssuntoService assuntoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarTodos() {
        Assunto assunto1 = new Assunto(1, "Assunto 1");
        Assunto assunto2 = new Assunto(2, "Assunto 2");

        when(assuntoRepository.findAll()).thenReturn(Arrays.asList(assunto1, assunto2));

        var resultado = assuntoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Assunto 1", resultado.get(0).getDescricao());
        assertEquals("Assunto 2", resultado.get(1).getDescricao());

        verify(assuntoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorIdExistente() {
        Assunto assunto = new Assunto(1, "Assunto 1");

        when(assuntoRepository.findById(1)).thenReturn(Optional.of(assunto));

        var resultado = assuntoService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Assunto 1", resultado.get().getDescricao());
        verify(assuntoRepository, times(1)).findById(1);
    }

    @Test
    void buscarPorIdInexistente() {
        when(assuntoRepository.findById(1)).thenReturn(Optional.empty());

        var resultado = assuntoService.buscarPorId(1);

        assertFalse(resultado.isPresent());
        verify(assuntoRepository, times(1)).findById(1);
    }

    @Test
    void salvar() {
        Assunto assunto = new Assunto(null, "Novo Assunto");
        Assunto assuntoSalvo = new Assunto(1, "Novo Assunto");
        when(assuntoRepository.save(assunto)).thenReturn(assuntoSalvo);

        var resultado = assuntoService.salvar(assunto);

        assertNotNull(resultado);
        assertEquals(1, resultado.getCodas());
        assertEquals("Novo Assunto", resultado.getDescricao());
        verify(assuntoRepository, times(1)).save(assunto);
    }

    @Test
    void deletarComIdExistente() {
        Assunto assunto = new Assunto(1, "Assunto 1");
        when(assuntoRepository.findById(1)).thenReturn(Optional.of(assunto));
        doNothing().when(assuntoRepository).deleteById(1);
        assertDoesNotThrow(() -> assuntoService.deletar(1));
        verify(assuntoRepository, times(1)).deleteById(1);
    }

    @Test
    void deletarComIdInexistente() {
        when(assuntoRepository.findById(999)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> assuntoService.deletar(999));
        assertEquals("Assunto não encontrado", exception.getMessage());
        verify(assuntoRepository, never()).deleteById(999);
    }
	
}
