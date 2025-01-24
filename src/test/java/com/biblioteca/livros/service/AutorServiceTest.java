package com.biblioteca.livros.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.biblioteca.livros.model.Autor;
import com.biblioteca.livros.repository.AutorRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AutorServiceTest {

	@Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService autorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarTodos() {
        Autor autor1 = new Autor(1, "Autor 1");
        Autor autor2 = new Autor(2, "Autor 2");

        when(autorRepository.findAll()).thenReturn(Arrays.asList(autor1, autor2));

        var resultado = autorService.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Autor 1", resultado.get(0).getNome());
        assertEquals("Autor 2", resultado.get(1).getNome());

        verify(autorRepository, times(1)).findAll();
    }

    @Test
    void buscarPorIdExistente() {
        Autor autor = new Autor(1, "Autor 1");

        when(autorRepository.findById(1)).thenReturn(Optional.of(autor));

        var resultado = autorService.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Autor 1", resultado.get().getNome());
        verify(autorRepository, times(1)).findById(1);
    }

    @Test
    void buscarPorIdInexistente() {
        when(autorRepository.findById(1)).thenReturn(Optional.empty());

        var resultado = autorService.buscarPorId(1);

        assertFalse(resultado.isPresent());
        verify(autorRepository, times(1)).findById(1);
    }

    @Test
    void salvar() {
        Autor autor = new Autor(null, "Novo Autor");
        Autor autorSalvo = new Autor(1, "Novo Autor");

        when(autorRepository.save(autor)).thenReturn(autorSalvo);

        var resultado = autorService.salvar(autor);

        assertNotNull(resultado);
        assertEquals(1, resultado.getCodau());
        assertEquals("Novo Autor", resultado.getNome());
        verify(autorRepository, times(1)).save(autor);
    }

    @Test
    void deletarComIdExistente() {
        Autor autor = new Autor(1, "Autor 1");
        when(autorRepository.findById(1)).thenReturn(Optional.of(autor));
        doNothing().when(autorRepository).deleteById(1);
        assertDoesNotThrow(() -> autorService.deletar(1));
        verify(autorRepository, times(1)).deleteById(1);
    }

    @Test
    void deletarComIdInexistente() {
        when(autorRepository.findById(999)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> autorService.deletar(999));
        assertEquals("Autor não encontrado", exception.getMessage());
        verify(autorRepository, never()).deleteById(999);
    }
}
